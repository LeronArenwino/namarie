package com.namarie.gui;

import com.namarie.entity.Media;
import com.namarie.entity.Song;
import com.namarie.logic.MediaLogic;
import com.namarie.logic.SettingsLogic;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

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

import static com.namarie.logic.SettingsLogic.*;

public class MainWindow extends javax.swing.JFrame implements Serializable {

    // Constants
    private final JPanel advertisementPanel = new JPanel();
    private static final String ADVERTISEMENT_MESSAGE = "Error media-player!";
    private static final String NAMARIE_TITLE = "Namarie jukebox";
    SettingsWindow settingsWindow = new SettingsWindow();

    // Create a Logger
    private final transient Logger logger = Logger.getLogger(MainWindow.class.getName());

    // Random secure generator
    private final Random rand = SecureRandom.getInstanceStrong();

    // General components
    private JPanel containerPanel;
    private JPanel centerPanel;
    private JPanel videoPanel;
    private JPanel musicListPanel;
    private JPanel songsListPanel;
    private JScrollPane songsListScrollPanel;
    private JList<Song> songsListJList;
    private JList<Song> musicQueueJList;
    private JLabel numberSong;
    private JLabel currentCreditsLabel;
    private JScrollPane musicListScrollPanel;
    private JTextField searchSongsListTextField;
    private JLabel videoLabel;
    private JButton searchSongsListButton;
    private JPanel searchSongPanel;
    private JLabel songsListLabel;

    // Video and audio components
    private EmbeddedMediaPlayerComponent videoMediaPlayer;
    private transient AudioPlayerComponent audioMediaPlayer;

    //
    private transient ArrayList<Song> musicQueue;
    private transient List<Media> videosQueue;
    private transient List<Media> promotionalVideos;
    private transient List<List<Song>> musicListByGenders;
    private String[] genders;
    private int selectedGender;
    private int selectedSong;
    private boolean promotionalVideoStatus;
    private String[] stringLabel;
    private int currentCredits;

    // Timers
    private javax.swing.Timer timerFocusMainPanel;
    private javax.swing.Timer timerCheckFocus;
    private javax.swing.Timer timerRandomSong;
    private javax.swing.Timer timerRandomPromotionalVideo;

    // KeyListener to KeyPressed event
    private final transient KeyListener mainWindowKeyListener = new KeyListener() {
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
            // Event to open a settings window (Key 'Q')
            if (e.getKeyCode() == 81) {
                settingsWindow.setVisible(true);
                timerCheckFocus.start();
            }
            // Event to open add coin
            else if (e.getKeyCode() == MediaLogic.getAddCoin() && currentCredits < 25) {
                currentCredits += 1;
                creditsValidate(currentCredits > 0);
            }
            // Event to open remove coin
            else if (e.getKeyCode() == MediaLogic.getRemoveCoin() && currentCredits > 0) {
                currentCredits -= 1;
                creditsValidate(currentCredits > 0);
            }
            // Event to up gender in gender list
            else if (e.getKeyCode() == MediaLogic.getUpGender()) {
                if (selectedGender < genders.length - 1) {
                    selectedGender++;
                } else {
                    selectedGender = 0;
                }
                loadSongsListJList();
            }
            // Event to down gender in gender list
            else if (e.getKeyCode() == MediaLogic.getDownGender()) {
                if (selectedGender > 0) {
                    selectedGender--;
                } else {
                    selectedGender = genders.length - 1;
                }
                loadSongsListJList();
            }
            // Event to up a song in music list
            else if (e.getKeyCode() == MediaLogic.getUpSong()) {
                if (selectedSong > 0) {
                    selectedSong--;
                } else {
                    selectedSong = songsListJList.getModel().getSize() - 1;
                }
                updateSelectedSongInSongsList();
            }
            // Event to down a song in music list
            else if (e.getKeyCode() == MediaLogic.getDownSong()) {
                if (selectedSong < songsListJList.getModel().getSize() - 1) {
                    selectedSong++;
                } else {
                    selectedSong = 0;
                }
                updateSelectedSongInSongsList();
            }
            // Event to up 20 songs in music list
            else if (e.getKeyCode() == MediaLogic.getUpSongs()) {
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
            else if (e.getKeyCode() == MediaLogic.getDownSongs()) {
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
            else if (e.getKeyCode() == MediaLogic.getNextSong()) {
                timerRandomSong.start();
                videoMediaPlayer.mediaPlayer().controls().stop();
                audioMediaPlayer.mediaPlayer().controls().stop();
                if (!musicQueue.isEmpty()) {
                    timerRandomSong.stop();
                    Song song = musicQueue.get(0);
                    playSong(song);
                    musicQueue.remove(0);
                    setMusicQueue(musicQueue);
                } else {
                    timerRandomPromotionalVideo.start();
                    videoLabel.setText(NAMARIE_TITLE);
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
                        musicQueue.add(selectedValue);
                        setMusicQueue(musicQueue);
                    }
                    currentCredits -= 1;
                    creditsValidate(currentCredits > 0);
                }
            }
            // Event to reload settings
            if (e.getKeyCode() == 82) {
                // Load values from JSON file
                MediaLogic.loadSettingsValues(SettingsLogic.loadSettings());

                selectedGender = 0;
                genders = MediaLogic.gendersList();
                musicListByGenders = MediaLogic.musicListByGenders(MediaLogic.musicList(), genders);
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
                for (int i = 0; i < stringLabel.length; i++) {
                    if ("-".equals(stringLabel[i])) {
                        stringLabel[i] = String.valueOf(e.getKeyChar());
                        break;
                    }
                }
                if (Arrays.stream(stringLabel).noneMatch("-"::equals)) {
                    selectedSong = Integer.parseInt(String.format(MediaLogic.ACTION_LIST, stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));
                    if (selectedSong <= Objects.requireNonNull(MediaLogic.musicList()).size() - 1) {
                        Song song = Objects.requireNonNull(MediaLogic.musicList()).get(selectedSong);
                        if (videoMediaPlayer.mediaPlayer().status().isPlaying() && !promotionalVideoStatus) {
                            musicQueue.add(song);
                            setMusicQueue(musicQueue);
                        }
                        if (musicQueue.isEmpty()) {
                            playSong(song);
                            promotionalVideoStatus = false;
                        }
                        currentCredits -= 1;
                        creditsValidate(currentCredits > 0);
                    }
                    setDefaultString();
                }
                numberSong.setText(String.format(" %s %s %s %s %s ", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));
            }
            // Event to set default value in String to select a song
            else if (e.getKeyCode() == 110) {
                setDefaultString();
            }
            // Event to power off computer
            else if (e.getKeyCode() == MediaLogic.getPowerOff()) {
                String s = JOptionPane.showInputDialog(null, "Password:", "Power off", JOptionPane.PLAIN_MESSAGE);
                if ("031217".equals(s)) {
                    videoMediaPlayer.release();
                    audioMediaPlayer.release();
                    try {
                        MediaLogic.shutdown();
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
    };
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

        initComponents();

    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {

        // Set default configuration to JFrame
        this.setTitle("Namarie");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setContentPane(containerPanel);

        // Reshape components to screen resolution
        videoPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT / 2));
        musicListPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 4, RESOLUTION_HEIGHT / 2));
        songsListPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT));

        paintComponents();

        // Adding listeners
        containerPanel.addKeyListener(mainWindowKeyListener);
        songsListJList.addListSelectionListener(songsListListSelection);

        setVideoMediaPlayer();

        // Add to the player container our canvas
        videoPanel.add(videoMediaPlayer, BorderLayout.CENTER);
        videoMediaPlayer.addKeyListener(mainWindowKeyListener);

        setAudioMediaPlayer();

        // Validate credits
        selectedGender = 0;
        currentCredits = 0;
        creditsValidate(false);

        videosQueue = MediaLogic.getVideos(MediaLogic.getVideosPath());
        promotionalVideos = MediaLogic.getVideos(MediaLogic.getPromotionalVideoPath());

        genders = MediaLogic.gendersList();

        musicListByGenders = MediaLogic.musicListByGenders(MediaLogic.musicList(), genders);

        musicQueue = new ArrayList<>();

        if (!musicListByGenders.isEmpty()) {
            loadSongsListJList();
        }

        stringLabel = new String[5];

        setDefaultString();

        ActionListener focusMainPanel = e -> getContentPane().requestFocus();

        ActionListener checkFocus = e -> checkSettingsFrame();

        ActionListener playRandomSong = e -> playRandomSong();

        ActionListener playRandomPromotionalVideo = e -> playRandomPromotionalVideo();

        timerFocusMainPanel = new Timer(250, focusMainPanel);
        timerFocusMainPanel.setRepeats(true);
        timerFocusMainPanel.start();

        timerCheckFocus = new Timer(200, checkFocus);
        timerCheckFocus.setRepeats(true);
        timerCheckFocus.stop();

        timerRandomSong = new Timer(MediaLogic.getRandomSong() * 60000, playRandomSong);
        timerRandomSong.setRepeats(false);
        timerRandomSong.start();

        timerRandomPromotionalVideo = new Timer(0, playRandomPromotionalVideo);
        timerRandomPromotionalVideo.setRepeats(false);
        timerRandomPromotionalVideo.start();

    }

    private void paintComponents() {

        String dark = "#0D0D0D";
        String darkLight = "#262626";
        String light = "#D9D9D9";

        Font defaultFont = new Font("Aria Narrow", Font.PLAIN, 36);

        Border blackLine = BorderFactory.createLineBorder(Color.decode(dark), 8);

        TitledBorder musicQueueTitledBorder = BorderFactory.createTitledBorder(
                blackLine, "");
        musicQueueTitledBorder.setTitleJustification(TitledBorder.CENTER);
        musicQueueTitledBorder.setTitleColor(Color.decode(light));
        musicQueueTitledBorder.setTitleFont(defaultFont);

        centerPanel.setBackground(Color.decode(dark));
        searchSongPanel.setBackground(Color.decode(dark));

        musicListPanel.setBorder(musicQueueTitledBorder);

        videoLabel.setBackground(Color.decode(dark));
        videoLabel.setForeground(Color.decode(light));
        videoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        videoLabel.setText(NAMARIE_TITLE);

        numberSong.setBackground(Color.decode(dark));
        numberSong.setForeground(Color.decode(light));
        numberSong.setFont(defaultFont);
        numberSong.setHorizontalAlignment(SwingConstants.CENTER);

        currentCreditsLabel.setBackground(Color.decode(dark));
        currentCreditsLabel.setForeground(Color.decode(light));
        currentCreditsLabel.setFont(defaultFont);
        currentCreditsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        musicQueueJList.setBackground(Color.decode(darkLight));
        musicQueueJList.setForeground(Color.decode(light));
        musicQueueJList.setFont(defaultFont);

        musicListPanel.setBackground(Color.decode(dark));
        musicListScrollPanel.setBackground(Color.decode(dark));

        songsListPanel.setBackground(Color.decode(dark));
        songsListScrollPanel.setBackground(Color.decode(dark));

        songsListLabel.setBackground(Color.decode(dark));
        songsListLabel.setForeground(Color.decode(light));
        songsListLabel.setFont(defaultFont);
        songsListLabel.setHorizontalAlignment(SwingConstants.CENTER);

        songsListJList.setBackground(Color.decode(darkLight));
        songsListJList.setForeground(Color.decode(light));
        songsListJList.setFont(defaultFont);

        searchSongsListTextField.setVisible(false);

        searchSongsListButton.setBackground(Color.decode(light));
        searchSongsListButton.setForeground(Color.decode(darkLight));
        searchSongsListButton.setText("Search song");
        searchSongsListButton.setVisible(false);

    }

    private void checkSettingsFrame() {

        if (!settingsWindow.isVisible()) {
            timerFocusMainPanel.start();
            timerCheckFocus.stop();
        } else if (timerFocusMainPanel.isRunning()) {
            timerFocusMainPanel.stop();
            settingsWindow.requestFocus();
        }

    }

    private void creditsValidate(boolean state) {

        musicListPanel.setVisible(state);
        songsListPanel.setVisible(state);
        videoLabel.setVisible(!state);

        currentCreditsLabel.setText(String.format("Credits: %s", currentCredits));

    }

    private void loadSongsListJList() {

        songsListLabel.setText(genders[selectedGender]);
        setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);
        selectedSong = 0;
        updateSelectedSongInSongsList();

    }

    private void playPromotionalMedia(Media video) {
        if (MediaLogic.isPromotionalVideoValidate()) {
            videoMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_MEDIA, MediaLogic.getPromotionalVideoPath(), File.separator, video.getName()));
            promotionalVideoStatus = true;
        }
    }

    private void playRandomPromotionalVideo() {

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty() && !promotionalVideos.isEmpty()) {

            int randSong = rand.nextInt(promotionalVideos.size());

            Media promotionalVideo = promotionalVideos.get(randSong);

            playPromotionalMedia(promotionalVideo);

            promotionalVideoStatus = true;

        }

    }

    private void playRandomSong() {

        if (promotionalVideoStatus) {

            videoMediaPlayer.mediaPlayer().controls().stop();
            promotionalVideoStatus = false;

        }

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty() && !MediaLogic.musicList().isEmpty()) {

            int randSong = rand.nextInt(Objects.requireNonNull(MediaLogic.musicList()).size());

            Song song = Objects.requireNonNull(MediaLogic.musicList()).get(randSong);

            playSong(song);

        }

    }

    private void playSong(Song song) {

        Matcher matcher = MediaLogic.getPatternVideo().matcher(song.getName());

        if (matcher.find()) {

            videoMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            videoLabel.setText(song.toString());
        }

        matcher = MediaLogic.getPatternAudio().matcher(song.getName());

        if (matcher.find()) {

            int randVideo = rand.nextInt(videosQueue.size());

            Media video = videosQueue.get(randVideo);

            videoMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_MEDIA, MediaLogic.getVideosPath(), File.separator, video.getName()));
            audioMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            videoLabel.setText(song.toString());

        }

        if (timerRandomSong.isRunning()) {

            timerRandomSong.stop();

        }

    }

    private void setDefaultString() {

        Arrays.fill(stringLabel, "-");
        numberSong.setText(String.format(" %s %s %s %s %s ", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

    }

    private void setAudioMediaPlayer() {

        // Create AudioPlayerComponent instances
        audioMediaPlayer = new AudioPlayerComponent() {

            @Override
            public void finished(MediaPlayer mediaPlayer) {

                videoMediaPlayer.mediaPlayer().controls().stop();

                timerRandomSong.start();

                if (!mediaPlayer.status().isPlaying() && !musicQueue.isEmpty()) {

                    timerRandomSong.stop();

                    Song song = musicQueue.get(0);

                    Matcher matcher = MediaLogic.getPatternVideo().matcher(song.getName());

                    if (matcher.find()) {

                        videoMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                    }

                    matcher = MediaLogic.getPatternAudio().matcher(song.getName());

                    if (matcher.find()) {

                        int randVideo = rand.nextInt(videosQueue.size());

                        Media video = videosQueue.get(randVideo);

                        videoMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_MEDIA, MediaLogic.getVideosPath(), File.separator, video.getName()));
                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                    }

                    musicQueue.remove(0);

                    setMusicQueue(musicQueue);

                } else {
                    timerRandomPromotionalVideo.start();
                    videoLabel.setText(NAMARIE_TITLE);
                }
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                JOptionPane.showMessageDialog(advertisementPanel, ADVERTISEMENT_MESSAGE, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        };

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

    private void setMusicQueue(ArrayList<Song> musicQueue) {

        if (musicQueue != null) {

            DefaultListModel<Song> model = new DefaultListModel<>();

            for (Song song : musicQueue) {

                model.addElement(song);

            }

            musicQueueJList.setModel(model);

        }

    }

    private void setVideoMediaPlayer() {

        // Create EmbeddedMediaPlayerComponent instances and add to the video panel
        videoMediaPlayer = new EmbeddedMediaPlayerComponent() {

            @Override
            public void finished(MediaPlayer mediaPlayer) {

                timerRandomSong.start();

                if (!audioMediaPlayer.mediaPlayer().status().isPlaying() && !mediaPlayer.status().isPlaying() && !musicQueue.isEmpty()) {

                    timerRandomSong.stop();

                    Song song = musicQueue.get(0);

                    Matcher matcher = MediaLogic.getPatternVideo().matcher(song.getName());

                    if (matcher.find()) {

                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                    }

                    matcher = MediaLogic.getPatternAudio().matcher(song.getName());

                    if (matcher.find()) {

                        int randVideo = rand.nextInt(videosQueue.size());

                        Media video = videosQueue.get(randVideo);

                        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(MediaLogic.ACTION_MEDIA, MediaLogic.getVideosPath(), File.separator, video.getName())));
                        audioMediaPlayer.mediaPlayer().media().play(String.format(MediaLogic.ACTION_SONG, MediaLogic.getSongsPath(), File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                    }

                    musicQueue.remove(0);

                    setMusicQueue(musicQueue);

                } else if (audioMediaPlayer.mediaPlayer().status().isPlaying()) {

                    timerRandomSong.stop();

                    int randVideo = rand.nextInt(videosQueue.size());

                    Media video = videosQueue.get(randVideo);

                    mediaPlayer.submit(() -> mediaPlayer().media().play(String.format(MediaLogic.ACTION_MEDIA, MediaLogic.getVideosPath(), File.separator, video.getName())));

                } else {

                    timerRandomPromotionalVideo.start();
                    videoLabel.setText(NAMARIE_TITLE);

                }

            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                JOptionPane.showMessageDialog(advertisementPanel, ADVERTISEMENT_MESSAGE, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        };

    }

    private void updateSelectedSongInSongsList() {

        songsListJList.setSelectedIndex(selectedSong);
        songsListJList.ensureIndexIsVisible(selectedSong);

    }

}
