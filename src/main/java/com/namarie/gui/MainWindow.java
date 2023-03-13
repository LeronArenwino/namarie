package com.namarie.gui;

import com.namarie.entity.Multimedia;
import com.namarie.entity.Song;
import com.namarie.logic.MultimediaLogic;
import org.apache.commons.lang3.StringUtils;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import static com.namarie.dao.PropertiesManager.loadProperties;
import static com.namarie.logic.SettingsSingleton.*;

public class MainWindow extends javax.swing.JFrame implements Serializable {

    SettingsWindow settingsWindow;

    // Create a Logger
    private final transient Logger logger = Logger.getLogger(MainWindow.class.getName());

    // Random secure generator
    private final Random rand = SecureRandom.getInstanceStrong();

    // General components
    private JPanel containerPanel;
    private JPanel westPanel;
    private JPanel northPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JPanel westCenterPanel;
    private JPanel northCenterPanel;
    private JPanel eastCenterPanel;
    private JPanel southCenterPanel;
    private JPanel centerCenterPanel;
    private JMenuBar containerJMenuBar;
    private JMenu fileJMenu;
    private JMenu editJMenu;
    private JMenuItem settingsJMenuItem;
    private JPanel videoPlayerPanel;
    private JPanel musicQueuePanel;
    private JPanel songsListPanel;
    private JScrollPane songsListScrollPanel;
    private JList<Song> songsListJList;
    private JList<Song> musicQueueJList;
    private JLabel songNumberToPlayLabel;
    private JLabel currentCreditsLabel;
    private JScrollPane musicQueueScrollPanel;
    private JTextField searchSongsListTextField;
    private JLabel nameSongLabel;
    private JButton searchSongsListButton;
    private JPanel searchSongPanel;
    private JLabel songsListGenderLabel;

    // Video and audio components
    private EmbeddedMediaPlayerComponent videoMediaPlayer;
    private transient AudioPlayerComponent audioMediaPlayer;

    //
    private transient ArrayList<Song> musicQueueToPlay;
    private transient List<Multimedia> availableVideos;
    private transient List<Multimedia> promotionalAvailableVideos;
    private transient List<List<Song>> musicListByGenders;
    private String[] genders;
    private int selectedGender;
    private int selectedSong;
    private boolean promotionalVideoStatus;
    private String[] tmpSongNumberToPlay;
    private int currentCredits;

    private JPanel songsTitlePanel;

    // Timers
    private javax.swing.Timer timerFocusMainPanel;
    private javax.swing.Timer timerReturnFocus;
    private javax.swing.Timer timerRandomSong;
    private javax.swing.Timer timerRandomPromotionalVideo;

    private final transient ListSelectionListener songsListListSelection = new ListSelectionListener() {
        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            selectedSong = songsListJList.getSelectedIndex();
        }
    };

    public static void main(String[] args) {

        // Look and feel to mainWindow
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                 IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainWindow().setVisible(true);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException(e);
            }
        });

    }

    public MainWindow() throws NoSuchAlgorithmException {

        setSettingsFromProperties(loadProperties());

        // Set default configuration to JFrame
        this.setTitle("Namarie");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(RESOLUTION_WIDTH - (RESOLUTION_WIDTH / 3), RESOLUTION_HEIGHT - (RESOLUTION_HEIGHT / 3)));
        this.setResizable(true);
        this.setContentPane(containerPanel);
        this.requestFocus();

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                MainWindow.super.requestFocus();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });

        // KeyListener to KeyPressed event
        this.addKeyListener(new KeyListener() {
            /**
             * Invoked when a key has been typed.
             * See the class description for {@link KeyEvent} for a definition of
             * a key typed event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();
            }

            /**
             * Invoked when a key has been pressed.
             * See the class description for {@link KeyEvent} for a definition of
             * a key pressed event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 122) {
                    videoMediaPlayer.mediaPlayer().fullScreen().toggle();
                }
                // Event to open a settings window (Key 'Q')
                if (e.getKeyCode() == 81) {
                    if (settingsWindow == null)
                        settingsWindow = new SettingsWindow();
                    settingsWindow.setVisible(true);
                    //timerFocusMainPanel.stop();
                }
                // Event to open add coin
                else if (e.getKeyCode() == getValueToAddCoin() && currentCredits < 25) {
                    currentCredits += 1;
                    creditsValidate(currentCredits > 0);
                }
                // Event to open remove coin
                else if (e.getKeyCode() == getValueToRemoveCoin() && currentCredits > 0) {
                    currentCredits -= 1;
                    creditsValidate(currentCredits > 0);
                }
                // Event to up gender in gender list
                else if (e.getKeyCode() == getValueToChangeGenderToUp()) {
                    if (selectedGender < genders.length - 1) {
                        selectedGender++;
                    } else {
                        selectedGender = 0;
                    }
                    loadSongsListJList();
                }
                // Event to down gender in gender list
                else if (e.getKeyCode() == getValueToChangeGenderToDown()) {
                    if (selectedGender > 0) {
                        selectedGender--;
                    } else {
                        selectedGender = genders.length - 1;
                    }
                    loadSongsListJList();
                }
                // Event to up a song in music list
                else if (e.getKeyCode() == getValueToUpIndex()) {
                    if (selectedSong > 0) {
                        selectedSong--;
                    } else {
                        selectedSong = songsListJList.getModel().getSize() - 1;
                    }
                    updateSelectedSongInSongsList();
                }
                // Event to down a song in music list
                else if (e.getKeyCode() == getValueToDownIndex()) {
                    if (selectedSong < songsListJList.getModel().getSize() - 1) {
                        selectedSong++;
                    } else {
                        selectedSong = 0;
                    }
                    updateSelectedSongInSongsList();
                }
                // Event to up 20 songs in music list
                else if (e.getKeyCode() == getValueToUpIndexes()) {
                    if (selectedSong < songsListJList.getModel().getSize() - 1) {
                        selectedSong += 20;
                        if (selectedSong > songsListJList.getModel().getSize() - 1) {
                            selectedSong = songsListJList.getModel().getSize() - 1;
                        }
                    } else {
                        selectedSong = 0;
                    }
                    updateSelectedSongInSongsList();
                }
                // Event to down 20 songs in music list
                else if (e.getKeyCode() == getValueToDownIndexes()) {
                    if (selectedSong > 0) {
                        selectedSong -= 20;
                        if (selectedSong < 0) {
                            selectedSong = 0;
                        }
                    } else {
                        selectedSong = songsListJList.getModel().getSize() - 1;
                    }
                    updateSelectedSongInSongsList();
                }
                // Event to play the next song in music queue
                else if (e.getKeyCode() == getValueToPlayNextSong()) {
                    timerRandomSong.start();
                    videoMediaPlayer.mediaPlayer().controls().stop();
                    audioMediaPlayer.mediaPlayer().controls().stop();
                    if (!musicQueueToPlay.isEmpty()) {
                        timerRandomSong.stop();
                        Song song = musicQueueToPlay.get(0);
                        playSong(song);
                        musicQueueToPlay.remove(0);
                        setMusicQueueList(musicQueueToPlay);
                    } else {
                        timerRandomPromotionalVideo.start();
                        nameSongLabel.setText(NAMARIE_TITLE);
                    }
                }
                // Event to play or add a song to music queue with ENTER
                else if (e.getKeyCode() == 10 && currentCredits > 0) {
                    Song selectedValue = songsListJList.getSelectedValue();
                    if (selectedValue != null) {
                        if (promotionalVideoStatus) {
                            videoMediaPlayer.mediaPlayer().controls().stop();
                            promotionalVideoStatus = false;
                        }
                        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying()) {
                            playSong(selectedValue);
                        } else {
                            musicQueueToPlay.add(selectedValue);
                            setMusicQueueList(musicQueueToPlay);
                        }
                        currentCredits -= 1;
                        creditsValidate(currentCredits > 0);
                    }
                }
                // Event to reload settings
                if (e.getKeyCode() == 82) {
                    // Load values from Properties file

                    selectedGender = 0;
                    genders = MultimediaLogic.getGenders();
                    musicListByGenders = MultimediaLogic.musicListByGenders(MultimediaLogic.getMusicList(), genders);
                    loadSongsListJList();
                }
                // Event to set a values in String to select a song
                else if ((e.getKeyCode() == 48 || e.getKeyCode() == 96 ||
                        // Event to set '1' value in String to select a song
                        e.getKeyCode() == 49 || e.getKeyCode() == 97 ||
                        // Event to set '2' value in String to select a song
                        e.getKeyCode() == 50 || e.getKeyCode() == 98 ||
                        // Event to set '3' value in String to select a song
                        e.getKeyCode() == 51 || e.getKeyCode() == 99 ||
                        // Event to set '4' value in String to select a song
                        e.getKeyCode() == 52 || e.getKeyCode() == 100 ||
                        // Event to set '5' value in String to select a song
                        e.getKeyCode() == 53 || e.getKeyCode() == 101 ||
                        // Event to set '6' value in String to select a song
                        e.getKeyCode() == 54 || e.getKeyCode() == 102 ||
                        // Event to set '7' value in String to select a
                        e.getKeyCode() == 55 || e.getKeyCode() == 103 ||
                        // Event to set '8' value in String to select a
                        e.getKeyCode() == 56 || e.getKeyCode() == 104 ||
                        // Event to set '9' value in String to select a
                        e.getKeyCode() == 57 || e.getKeyCode() == 105) && currentCredits > 0) {
                    for (int i = 0; i < tmpSongNumberToPlay.length; i++) {
                        if ("-".equals(tmpSongNumberToPlay[i])) {
                            tmpSongNumberToPlay[i] = String.valueOf(e.getKeyChar());
                            break;
                        }
                    }
                    if (Arrays.stream(tmpSongNumberToPlay).noneMatch("-"::equals)) {
                        selectedSong = Integer.parseInt(String.format(ACTION_LIST, tmpSongNumberToPlay[0], tmpSongNumberToPlay[1], tmpSongNumberToPlay[2], tmpSongNumberToPlay[3], tmpSongNumberToPlay[4]));
                        if (selectedSong <= Objects.requireNonNull(MultimediaLogic.getMusicList()).size() - 1) {
                            Song song = Objects.requireNonNull(MultimediaLogic.getMusicList()).get(selectedSong);
                            if (videoMediaPlayer.mediaPlayer().status().isPlaying() && !promotionalVideoStatus) {
                                musicQueueToPlay.add(song);
                                setMusicQueueList(musicQueueToPlay);
                            }
                            if (musicQueueToPlay.isEmpty()) {
                                playSong(song);
                                promotionalVideoStatus = false;
                            }
                            currentCredits -= 1;
                            creditsValidate(currentCredits > 0);
                        }
                        setDefaultString();
                    }
                    songNumberToPlayLabel.setText(String.format(" %s %s %s %s %s ", tmpSongNumberToPlay[0], tmpSongNumberToPlay[1], tmpSongNumberToPlay[2], tmpSongNumberToPlay[3], tmpSongNumberToPlay[4]));
                }
                // Event to set default value in String to select a song
                else if (e.getKeyCode() == 110) {
                    setDefaultString();
                }
                // Event to power off computer
                else if (e.getKeyCode() == getValueToPowerOff()) {
                    String s = JOptionPane.showInputDialog(null, "Password:", "Power off", JOptionPane.PLAIN_MESSAGE);
                    if ("031217".equals(s)) {
                        videoMediaPlayer.release();
                        audioMediaPlayer.release();
                        try {
                            MultimediaLogic.shutdown();
                        } catch (IOException ex) {
                            logger.log(Level.WARNING, () -> "Runtime exec error! " + ex);
                        }
                    }
                }
            }

            /**
             * Invoked when a key has been released.
             * See the class description for {@link KeyEvent} for a definition of
             * a key released event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyReleased(KeyEvent e) {
                e.consume();
            }
        });

        initComponents();

        addComponents();

        paintComponents();

        loadComponentsData();

        startTimers();

        searchSongsListTextField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent focusEvent) {
                songsListGenderLabel.setText("All music");
                setMusicList(MultimediaLogic.getMusicList());
                timerFocusMainPanel.stop();
                timerReturnFocus.start();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                loadSongsListJList();
                timerFocusMainPanel.start();
                timerReturnFocus.stop();
            }
        });
        searchSongsListTextField.addCaretListener(caretEvent -> {
            timerReturnFocus.stop();
            filterModel(searchSongsListTextField.getText());
            timerReturnFocus.start();
        });
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {

        // Main panels
        northPanel = new JPanel(new BorderLayout());
        eastPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());
        westPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new BorderLayout());

        // Secondary panels
        northCenterPanel = new JPanel(new BorderLayout());
        eastCenterPanel = new JPanel(new BorderLayout());
        southCenterPanel = new JPanel(new BorderLayout());
        westCenterPanel = new JPanel(new BorderLayout());
        centerCenterPanel = new JPanel(new BorderLayout());

        // Create EmbeddedMediaPlayerComponent instances
        videoMediaPlayer = new EmbeddedMediaPlayerComponent(
                null,
                null,
                new AdaptiveFullScreenStrategy(this),
                null,
                null
        ) {

            @Override
            public void mouseClicked(MouseEvent e) {
                MainWindow.super.requestFocus();
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {

                timerRandomSong.start();

                if (!audioMediaPlayer.mediaPlayer().status().isPlaying() && !mediaPlayer.status().isPlaying() && !musicQueueToPlay.isEmpty()) {

                    timerRandomSong.stop();

                    Song song = musicQueueToPlay.get(0);

                    Matcher matcher = MultimediaLogic.getPatternVideo().matcher(song.getName());

                    if (matcher.find()) {

                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                    }

                    matcher = MultimediaLogic.getPatternAudio().matcher(song.getName());

                    if (matcher.find()) {

                        int randVideo = rand.nextInt(availableVideos.size());

                        Multimedia video = availableVideos.get(randVideo);

                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(ACTION_MULTIMEDIA, getPathToVideos(), File.separator, video.getName())));
                        audioMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                    }

                    musicQueueToPlay.remove(0);

                    setMusicQueueList(musicQueueToPlay);

                } else if (audioMediaPlayer.mediaPlayer().status().isPlaying()) {

                    timerRandomSong.stop();

                    int randVideo = rand.nextInt(availableVideos.size());

                    Multimedia video = availableVideos.get(randVideo);

                    mediaPlayer.submit(() -> mediaPlayer().media().play(String.format(ACTION_MULTIMEDIA, getPathToVideos(), File.separator, video.getName())));

                } else {

                    timerRandomPromotionalVideo.start();
                    nameSongLabel.setText(NAMARIE_TITLE);

                }

            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                JOptionPane.showMessageDialog(null, ADVERTISEMENT_MESSAGE, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        };

        // Create AudioPlayerComponent instances
        audioMediaPlayer = new AudioPlayerComponent() {

            @Override
            public void finished(MediaPlayer mediaPlayer) {

                videoMediaPlayer.mediaPlayer().controls().stop();

                timerRandomSong.start();

                if (!mediaPlayer.status().isPlaying() && !musicQueueToPlay.isEmpty()) {

                    timerRandomSong.stop();

                    Song song = musicQueueToPlay.get(0);

                    Matcher matcher = MultimediaLogic.getPatternVideo().matcher(song.getName());

                    if (matcher.find()) {

                        videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                    }

                    matcher = MultimediaLogic.getPatternAudio().matcher(song.getName());

                    if (matcher.find()) {

                        int randVideo = rand.nextInt(availableVideos.size());

                        Multimedia video = availableVideos.get(randVideo);

                        videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MULTIMEDIA, getPathToVideos(), File.separator, video.getName()));
                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                    }

                    musicQueueToPlay.remove(0);

                    setMusicQueueList(musicQueueToPlay);

                } else {
                    timerRandomPromotionalVideo.start();
                    nameSongLabel.setText(NAMARIE_TITLE);
                }
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                JOptionPane.showMessageDialog(null, ADVERTISEMENT_MESSAGE, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        };

        // Music and songs list panels
        songsListPanel = new JPanel(new BorderLayout());
        musicQueuePanel = new JPanel(new BorderLayout());
        videoPlayerPanel = new JPanel(new BorderLayout());

        // Songs list components
        songsTitlePanel = new JPanel(new BorderLayout());
        songsListJList = new JList<>();
        songsListScrollPanel = new JScrollPane(songsListJList);

        // Music list queue components
        musicQueueJList = new JList<>();
        musicQueueScrollPanel = new JScrollPane(musicQueueJList);

        // Menu components
        containerJMenuBar = new JMenuBar();
        fileJMenu = new JMenu("File");
        editJMenu = new JMenu("Edit");
        settingsJMenuItem = new JMenuItem("Settings");

        // Utility labels
        songNumberToPlayLabel = new JLabel();
        currentCreditsLabel = new JLabel();
        songsListGenderLabel = new JLabel();
        nameSongLabel = new JLabel();

        // Temporal string to calculate number song to play
        tmpSongNumberToPlay = new String[5];

        // Values to calculate credits
        selectedGender = 0;
        currentCredits = 0;

        // Search songs components
        searchSongPanel = new JPanel(new BorderLayout());
        searchSongsListTextField = new JTextField();
        searchSongsListButton = new JButton();

        // Lists
        musicQueueToPlay = new ArrayList<>();
        availableVideos = new ArrayList<>();
        promotionalAvailableVideos = new ArrayList<>();

        // Timers
        ActionListener focusMainPanel = e -> getContentPane().requestFocus();
        timerFocusMainPanel = new Timer(10000, focusMainPanel);

        ActionListener returnFocusMainPanel = e -> timerFocusMainPanel.start();
        timerReturnFocus = new Timer(10000, returnFocusMainPanel);

        ActionListener playRandomSong = e -> playRandomSong();
        timerRandomSong = new Timer(getTimeToPlayRandomSongs() * 60000, playRandomSong);

        ActionListener playRandomPromotionalVideo = e -> playRandomPromotionalVideo();
        timerRandomPromotionalVideo = new Timer(0, playRandomPromotionalVideo);

    }

    private void startTimers() {

        timerFocusMainPanel.setRepeats(true);
        timerFocusMainPanel.stop();

        timerReturnFocus.setRepeats(true);
        timerReturnFocus.stop();

        timerRandomSong.setRepeats(false);
        timerRandomSong.start();

        timerRandomPromotionalVideo.setRepeats(false);
        timerRandomPromotionalVideo.start();

    }

    private void addComponents() {

        // Adding main panels to containerPanel
        containerPanel.add(northPanel, BorderLayout.NORTH);
        containerPanel.add(eastPanel, BorderLayout.EAST);
        containerPanel.add(southPanel, BorderLayout.SOUTH);
        containerPanel.add(westPanel, BorderLayout.WEST);
        containerPanel.add(centerPanel, BorderLayout.CENTER);

        // Adding secondary panels to centerPanel
        centerPanel.add(northCenterPanel, BorderLayout.NORTH);
        centerPanel.add(eastCenterPanel, BorderLayout.EAST);
        centerPanel.add(southCenterPanel, BorderLayout.SOUTH);
        centerPanel.add(westCenterPanel, BorderLayout.WEST);
        centerPanel.add(centerCenterPanel, BorderLayout.CENTER);

        // Adding to main panels
        southPanel.add(songsListPanel, BorderLayout.CENTER);
        eastPanel.add(musicQueuePanel, BorderLayout.CENTER);
        centerCenterPanel.add(videoPlayerPanel, BorderLayout.CENTER);

        // Adding listeners
        songsListJList.addListSelectionListener(songsListListSelection);

        // Add to the player container our canvas
        videoPlayerPanel.add(videoMediaPlayer, BorderLayout.CENTER);

        // Menu container
        fileJMenu.add(settingsJMenuItem);
        containerJMenuBar.add(fileJMenu);
        containerJMenuBar.add(editJMenu);
        northPanel.add(containerJMenuBar, BorderLayout.CENTER);

        // Songs list panel
        songsListPanel.add(songsListScrollPanel, BorderLayout.CENTER);
        songsListPanel.add(songsTitlePanel, BorderLayout.NORTH);
        songsTitlePanel.add(songsListGenderLabel, BorderLayout.WEST);
        songsTitlePanel.add(songNumberToPlayLabel, BorderLayout.CENTER);
        songsTitlePanel.add(currentCreditsLabel, BorderLayout.EAST);

        // Music list panel
        musicQueuePanel.add(musicQueueScrollPanel, BorderLayout.CENTER);

    }

    private void paintComponents() {

        String dark = "#000000";
        String darkLight = "#262626";
        String light = "#D9D9D9";

        Font defaultFont = new Font("Aria Narrow", Font.PLAIN, 28);

        Border blackLine = BorderFactory.createLineBorder(Color.decode(dark), 8);

        TitledBorder musicQueueTitledBorder = BorderFactory.createTitledBorder(
                blackLine, "");
        musicQueueTitledBorder.setTitleJustification(TitledBorder.CENTER);
        musicQueueTitledBorder.setTitleColor(Color.decode(light));
        musicQueueTitledBorder.setTitleFont(defaultFont);

        centerPanel.setBackground(Color.decode(dark));
        searchSongPanel.setBackground(Color.decode(dark));

        musicQueuePanel.setBorder(musicQueueTitledBorder);

        nameSongLabel.setBackground(Color.decode(dark));
        nameSongLabel.setForeground(Color.decode(light));
        nameSongLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameSongLabel.setText(NAMARIE_TITLE);

        songsTitlePanel.setBackground(Color.decode(dark));

        songNumberToPlayLabel.setBackground(Color.decode(dark));
        songNumberToPlayLabel.setForeground(Color.decode(light));
        songNumberToPlayLabel.setFont(defaultFont);
        songNumberToPlayLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentCreditsLabel.setBackground(Color.decode(dark));
        currentCreditsLabel.setForeground(Color.decode(light));
        currentCreditsLabel.setFont(defaultFont);
        currentCreditsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        musicQueueJList.setBackground(Color.decode(darkLight));
        musicQueueJList.setForeground(Color.decode(light));
        musicQueueJList.setFont(defaultFont);

        musicQueuePanel.setBackground(Color.decode(dark));
        musicQueueScrollPanel.setBackground(Color.decode(dark));

        songsListPanel.setBackground(Color.decode(dark));
        songsListJList.setSelectionBackground(Color.decode("#FFFFFF"));
        songsListJList.setSelectionForeground(Color.decode("#000000"));
        songsListScrollPanel.setBackground(Color.decode(dark));
        songsListScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        songsListScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        songsListGenderLabel.setBackground(Color.decode(dark));
        songsListGenderLabel.setForeground(Color.decode(light));
        songsListGenderLabel.setFont(defaultFont);
        songsListGenderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        songsListJList.setBackground(Color.decode(darkLight));
        songsListJList.setForeground(Color.decode(light));
        songsListJList.setFont(defaultFont);

        searchSongPanel.setBackground(Color.decode(dark));

        searchSongsListTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(light)));
        searchSongsListTextField.setBackground(Color.decode(dark));
        searchSongsListTextField.setFont(defaultFont);

        searchSongsListButton.setBackground(Color.decode(light));
        searchSongsListButton.setForeground(Color.decode(darkLight));
        searchSongsListButton.setText("Search song");

        Font fontFromSettings = new Font(getValueFont(), Font.PLAIN, getValueFontSize());

        songsListJList.setFont(fontFromSettings);
        musicQueueJList.setFont(fontFromSettings);


    }

    private void loadComponentsData() {

        availableVideos = MultimediaLogic.getVideos(getPathToVideos());
        promotionalAvailableVideos = MultimediaLogic.getVideos(getPathToPromotionalVideos());

        genders = MultimediaLogic.getGenders();

        musicListByGenders = MultimediaLogic.musicListByGenders(MultimediaLogic.getMusicList(), genders);

        if (!musicListByGenders.isEmpty()) {
            loadSongsListJList();
        }

        creditsValidate(false);

        setDefaultString();

    }

    private void creditsValidate(boolean state) {

        musicQueuePanel.setVisible(state);
        songsListPanel.setVisible(state);
        nameSongLabel.setVisible(!state);

        currentCreditsLabel.setText(String.format("Credits: %s", currentCredits));

    }

    private void filterModel(String filter) {

        DefaultListModel<Song> model = new DefaultListModel<>();

        for (Song song : MultimediaLogic.getMusicList()) {
            if (StringUtils.containsIgnoreCase(song.toString(), filter)) {
                model.addElement(song);
            }
        }

        songsListJList.setModel(model);

    }

    private void loadSongsListJList() {

        searchSongsListTextField.setText("");
        songsListGenderLabel.setText(genders[selectedGender]);
        setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);
        selectedSong = 0;
        updateSelectedSongInSongsList();

    }

    private void playPromotionalMedia(Multimedia video) {
        if (isPromotionalVideos()) {
            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MULTIMEDIA, getPathToPromotionalVideos(), File.separator, video.getName()));
            promotionalVideoStatus = true;
        }
    }

    private void playRandomPromotionalVideo() {

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueueToPlay.isEmpty() && !promotionalAvailableVideos.isEmpty()) {

            int randSong = rand.nextInt(promotionalAvailableVideos.size());

            Multimedia promotionalVideo = promotionalAvailableVideos.get(randSong);

            playPromotionalMedia(promotionalVideo);

            promotionalVideoStatus = true;

        }

    }

    private void playRandomSong() {

        if (promotionalVideoStatus) {

            videoMediaPlayer.mediaPlayer().controls().stop();
            promotionalVideoStatus = false;

        }

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueueToPlay.isEmpty() && !MultimediaLogic.getMusicList().isEmpty()) {

            int randSong = rand.nextInt(Objects.requireNonNull(MultimediaLogic.getMusicList()).size());

            Song song = Objects.requireNonNull(MultimediaLogic.getMusicList()).get(randSong);

            playSong(song);

        }

    }

    private void playSong(Song song) {

        Matcher matcher = MultimediaLogic.getPatternVideo().matcher(song.getName());

        if (matcher.find()) {

            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            nameSongLabel.setText(song.toString());
        }

        matcher = MultimediaLogic.getPatternAudio().matcher(song.getName());

        if (matcher.find()) {
            if (!availableVideos.isEmpty()) {
                int randVideo = rand.nextInt(availableVideos.size());

                Multimedia video = availableVideos.get(randVideo);

                videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MULTIMEDIA, getPathToVideos(), File.separator, video.getName()));
            }
            audioMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, getPathToSongs(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            nameSongLabel.setText(song.toString());

        }

        if (timerRandomSong.isRunning()) {

            timerRandomSong.stop();

        }

    }

    private void setDefaultString() {

        Arrays.fill(tmpSongNumberToPlay, "-");
        songNumberToPlayLabel.setText(String.format(" %s %s %s %s %s ", tmpSongNumberToPlay[0], tmpSongNumberToPlay[1], tmpSongNumberToPlay[2], tmpSongNumberToPlay[3], tmpSongNumberToPlay[4]));

    }

    private void setMusicList(List<Song> musicList) {

        if (musicList != null) {

            DefaultListModel<Song> model = new DefaultListModel<>();

            for (Song song : musicList) {
                model.addElement(song);
            }

            songsListJList.setModel(model);
        }

    }

    private void setMusicList(List<Song> musicList, String selectedGender) {

        if (musicList != null) {

            DefaultListModel<Song> model = new DefaultListModel<>();

            for (Song song : musicList) {
                if (selectedGender.equals(song.getGender())) model.addElement(song);
            }

            songsListJList.setModel(model);
        }

    }

    private void setMusicQueueList(ArrayList<Song> musicQueueList) {

        if (musicQueueList != null) {

            DefaultListModel<Song> model = new DefaultListModel<>();

            for (Song song : musicQueueList) {

                model.addElement(song);

            }

            musicQueueJList.setModel(model);

        }

    }

    private void updateSelectedSongInSongsList() {

        songsListJList.setSelectedIndex(selectedSong);
        songsListJList.ensureIndexIsVisible(selectedSong);

    }

}
