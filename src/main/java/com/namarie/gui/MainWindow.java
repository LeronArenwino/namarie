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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import static com.namarie.logic.SettingsLogic.*;

public class MainWindow extends javax.swing.JFrame implements Serializable {

    // Constants
    private final JPanel advertisementPanel = new JPanel();
    private static final String advertisementMessage = "Error media-player!";

    private static final String ACTION_SONG = "%s%s%s%s%s%s%s";
    private static final String ACTION_MEDIA = "%s%s%s";

    // Create a Logger
    private final transient Logger logger
            = Logger.getLogger(
            MainWindow.class.getName());

    // Random secure generator
    private final Random rand = SecureRandom.getInstanceStrong();

    // General components
    private JPanel containerPanel;
    private JPanel videoPanel;
    private JPanel musicListPanel;
    private JPanel songsListPanel;
    private JScrollPane songsListScrollPanel;
    private JList<Song> songsListJList;
    private JList<Song> musicQueueJList;
    private JPanel centerPanel;
    private JLabel songsGenderLabel;
    private JLabel numberSong;
    private JLabel currentCreditsLabel;
    private JScrollPane musicListScrollPanel;
    private JPanel searchSongsListPanel;
    private JTextField searchSongsListTextField;
    private JLabel videoJLabel;
    private JButton searchSongsListButton;

    // Video and audio components
    private EmbeddedMediaPlayerComponent videoMediaPlayer;
    private transient AudioPlayerComponent audioMediaPlayer;

    //
    private transient ArrayList<Song> musicQueue;
    private transient ArrayList<Media> videosQueue;
    private transient ArrayList<Media> promotionalVideos;
    private transient ArrayList<ArrayList<Song>> musicListByGenders;
    private String[] genders;
    private int selectedGender;
    private int selectedSong;
    private boolean promotionalVideoStatus;
    private String[] stringLabel;
    private int currentCredits;

    // Timers
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
                SettingsWindow settingsWindow = new SettingsWindow();
                settingsWindow.setVisible(true);
            }
            // Event to open add coin
            else if (e.getKeyCode() == MediaLogic.addCoin && currentCredits < 25) {
                currentCredits += 1;
                creditsValidate(currentCredits > 0);
            }
            // Event to open remove coin
            else if (e.getKeyCode() == MediaLogic.removeCoin && currentCredits > 0) {
                currentCredits -= 1;
                creditsValidate(currentCredits > 0);
            }
            // Event to up gender in gender list
            else if (e.getKeyCode() == MediaLogic.upGender) {
                if (selectedGender < genders.length - 1) {
                    selectedGender++;
                } else {
                    selectedGender = 0;
                }
                loadSongsListJList();
            }
            // Event to down gender in gender list
            else if (e.getKeyCode() == MediaLogic.downGender) {
                if (selectedGender > 0) {
                    selectedGender--;
                } else {
                    selectedGender = genders.length - 1;
                }
                loadSongsListJList();
            }
            // Event to up a song in music list
            else if (e.getKeyCode() == MediaLogic.upSong) {
                if (selectedSong > 0) {
                    selectedSong--;
                } else {
                    selectedSong = songsListJList.getModel().getSize() - 1;
                }
                updateSelectedSongInSongsList();
            }
            // Event to down a song in music list
            else if (e.getKeyCode() == MediaLogic.downSong) {
                if (selectedSong < songsListJList.getModel().getSize() - 1) {
                    selectedSong++;
                } else {
                    selectedSong = 0;
                }
                updateSelectedSongInSongsList();
            }
            // Event to up 20 songs in music list
            else if (e.getKeyCode() == MediaLogic.upSongs) {
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
            else if (e.getKeyCode() == MediaLogic.downSongs) {
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
            else if (e.getKeyCode() == MediaLogic.nextSong) {
                timerRandomSong.start();
                // TODO Promotional video
                nextSong();
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
            // Event to set '0' value in String to select a song
            else if (e.getKeyCode() == 48 || e.getKeyCode() == 96 ||
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
                    e.getKeyCode() == 57 || e.getKeyCode() == 105) {
                setString(e.getKeyChar());
            }
            // Event to set default value in String to select a song
            else if (e.getKeyCode() == 110) {
                setDefaultString();
            }
            // Event to power off computer
            else if (e.getKeyCode() == MediaLogic.powerOff) {
                String s = JOptionPane.showInputDialog(null, "Password:", "Power off", JOptionPane.PLAIN_MESSAGE);

                if ("031217".equals(s)) {

                    videoMediaPlayer.release();
                    audioMediaPlayer.release();

                    try {
                        MediaLogic.shutdown();
                    } catch (IOException ex) {
                        logger.log(Level.WARNING, () -> "Runtime exec error! " + ex);
                    }
                    System.exit(0);

                }
            } else {
                e.consume();
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
                throw new RuntimeException(e);
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

        containerPanel.addKeyListener(mainWindowKeyListener);
        songsListJList.addListSelectionListener(songsListListSelection);

        selectedGender = 0;

        // Validate credits
        currentCredits = 0;
        creditsValidate(false);

        // Reshape components to screen resolution
        centerPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT));
        videoPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT / 2));
        musicListPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 4, RESOLUTION_HEIGHT / 2));
        songsListPanel.setPreferredSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT));

        try {

            // Create EmbeddedMediaPlayerComponent instances and add to the video panel
            videoMediaPlayer = new EmbeddedMediaPlayerComponent() {

                @Override
                public void playing(MediaPlayer mediaPlayer) {

                    getContentPane().requestFocus();

                }

                @Override
                public void finished(MediaPlayer mediaPlayer) {

                    getContentPane().requestFocus();

                    timerRandomSong.start();

                    if (!audioMediaPlayer.mediaPlayer().status().isPlaying() && !mediaPlayer.status().isPlaying() && !musicQueue.isEmpty()) {

                        timerRandomSong.stop();

                        Song song = musicQueue.get(0);

                        Matcher matcher = MediaLogic.patternVideo.matcher(song.getName());

                        if (matcher.find()) {

                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format("%s%s%s%s%s%s%s", MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                        }

                        matcher = MediaLogic.patternAudio.matcher(song.getName());

                        if (matcher.find()) {

                            int randVideo = rand.nextInt(videosQueue.size());

                            Media video = videosQueue.get(randVideo);

                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(ACTION_MEDIA, MediaLogic.videosPath, File.separator, video.getName())));
                            audioMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                        }

                        musicQueue.remove(0);

                        setMusicQueue(musicQueue);

                    } else if (audioMediaPlayer.mediaPlayer().status().isPlaying()) {

                        timerRandomSong.stop();

                        int randVideo = rand.nextInt(videosQueue.size());

                        Media video = videosQueue.get(randVideo);

                        mediaPlayer.submit(() -> mediaPlayer().media().play(String.format(ACTION_MEDIA, MediaLogic.videosPath, File.separator, video.getName())));

                    } else {

                        timerRandomPromotionalVideo.start();

                    }

                }

                @Override
                public void error(MediaPlayer mediaPlayer) {
                    JOptionPane.showMessageDialog(advertisementPanel, advertisementMessage, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            };

            // Add to the player container our canvas
            videoPanel.add(videoMediaPlayer, BorderLayout.CENTER);
            videoMediaPlayer.addKeyListener(mainWindowKeyListener);

            // Create AudioPlayerComponent instances
            audioMediaPlayer = new AudioPlayerComponent() {

                @Override
                public void playing(MediaPlayer mediaPlayer) {

                    getContentPane().requestFocus();

                }

                @Override
                public void finished(MediaPlayer mediaPlayer) {

                    getContentPane().requestFocus();

                    videoMediaPlayer.mediaPlayer().controls().stop();

                    timerRandomSong.start();

                    if (!mediaPlayer.status().isPlaying() && !musicQueue.isEmpty()) {

                        timerRandomSong.stop();

                        Song song = musicQueue.get(0);

                        Matcher matcher = MediaLogic.patternVideo.matcher(song.getName());

                        if (matcher.find()) {

                            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));

                        }

                        matcher = MediaLogic.patternAudio.matcher(song.getName());

                        if (matcher.find()) {

                            int randVideo = rand.nextInt(videosQueue.size());

                            Media video = videosQueue.get(randVideo);

                            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MEDIA, MediaLogic.videosPath, File.separator, video.getName()));
                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format(ACTION_SONG, MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName())));

                        }

                        musicQueue.remove(0);

                        setMusicQueue(musicQueue);

                    } else {
                        timerRandomPromotionalVideo.start();
                    }
                }

                @Override
                public void error(MediaPlayer mediaPlayer) {
                    JOptionPane.showMessageDialog(advertisementPanel, advertisementMessage, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            };

        } catch (UnsatisfiedLinkError e) {
            logger.log(Level.WARNING, () -> "Create EmbeddedMediaPlayerComponent error! " + e);
        }

        videosQueue = MediaLogic.getVideos(MediaLogic.videosPath);
        promotionalVideos = MediaLogic.getVideos(MediaLogic.promotionalVideoPath);

        genders = MediaLogic.gendersList();

        musicListByGenders = MediaLogic.musicListByGenders(MediaLogic.musicList(), genders);

        musicQueue = new ArrayList<>();

        if (musicListByGenders != null) {
            loadSongsListJList();
        }

        stringLabel = new String[5];

        setDefaultString();

        ActionListener playRandomSong = e -> playRandomSong();

        ActionListener playRandomPromotionalVideo = e -> playRandomPromotionalVideo();

        ActionListener movingTextLabel = e -> movingTextLabel();

        timerRandomSong = new Timer(MediaLogic.randomSong * 60000, playRandomSong);
        timerRandomSong.setRepeats(false);
        timerRandomSong.start();

        timerRandomPromotionalVideo = new Timer(0, playRandomPromotionalVideo);
        timerRandomPromotionalVideo.setRepeats(false);
        timerRandomPromotionalVideo.start();

        Timer timerMovingTextLabel = new Timer(600, movingTextLabel);
        timerMovingTextLabel.setRepeats(true);
        timerMovingTextLabel.start();

    }

    private void creditsValidate(boolean state) {

        musicListPanel.setVisible(state);
        songsListPanel.setVisible(state);

        currentCreditsLabel.setText(String.format("Credits: %s", currentCredits));

    }

    private void playRandomSong() {

        // getContentPane().requestFocus();

        if (promotionalVideoStatus) {

            videoMediaPlayer.mediaPlayer().controls().stop();
            promotionalVideoStatus = false;

        }

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty()) {

            int randSong = rand.nextInt(Objects.requireNonNull(MediaLogic.musicList()).size());

            Song song = Objects.requireNonNull(MediaLogic.musicList()).get(randSong);

            playSong(song);

        }

    }

    private void playRandomPromotionalVideo() {

        // getContentPane().requestFocus();

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty()) {

            int randSong = rand.nextInt(promotionalVideos.size());

            Media promotionalVideo = promotionalVideos.get(randSong);

            playPromotionalMedia(promotionalVideo);

            promotionalVideoStatus = true;

        }

    }

    private void playSong(Song song) {

        Matcher matcher = MediaLogic.patternVideo.matcher(song.getName());

        if (matcher.find()) {

            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            videoJLabel.setText(song.toString());

        }

        matcher = MediaLogic.patternAudio.matcher(song.getName());

        if (matcher.find()) {

            int randVideo = rand.nextInt(videosQueue.size());

            Media video = videosQueue.get(randVideo);

            videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MEDIA, MediaLogic.videosPath, File.separator, video.getName()));
            audioMediaPlayer.mediaPlayer().media().play(String.format(ACTION_SONG, MediaLogic.songsPath, File.separator, song.getGender(), File.separator, song.getSinger(), File.separator, song.getName()));
            videoJLabel.setText(song.toString());

        }

        if (timerRandomSong.isRunning()) {

            timerRandomSong.stop();

        }

    }

    private void playPromotionalMedia(Media video) {

        videoMediaPlayer.mediaPlayer().media().play(String.format(ACTION_MEDIA, MediaLogic.promotionalVideoPath, File.separator, video.getName()));

        promotionalVideoStatus = true;

    }

    private void loadSongsListJList() {

        selectedSong = 0;
        songsGenderLabel.setText(genders[selectedGender]);
        setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);
        updateSelectedSongInSongsList();

    }

    private void movingTextLabel() {

        String oldText = videoJLabel.getText();
        String newText = oldText.substring(1) + oldText.charAt(0);
        videoJLabel.setText(newText);

    }

    private void nextSong() {

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

        }

    }

    private void setDefaultString() {

        Arrays.fill(stringLabel, "-");
        numberSong.setText(String.format(" %s %s %s %s %s ", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

    }

    private void setString(char value) {

        if (currentCredits > 0) {
            for (int i = 0; i < stringLabel.length; i++) {
                if ("-".equals(stringLabel[i])) {
                    stringLabel[i] = String.valueOf(value);
                    break;
                }
            }
            if (Arrays.stream(stringLabel).noneMatch("-"::equals)) {

                selectedSong = Integer.parseInt(String.format("%s%s%s%s%s", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

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

    private void setMusicList(ArrayList<Song> musicList, String selectedGender) {

        if (musicList != null) {

            DefaultListModel<Song> model = new DefaultListModel<>();

            for (Song song : musicList) {
                if (selectedGender.equals(song.getGender())) model.addElement(song);
            }

            songsListJList.setModel(model);
        }

    }

    private void updateSelectedSongInSongsList() {

        songsListJList.setSelectedIndex(selectedSong);
        songsListJList.ensureIndexIsVisible(selectedSong);

    }

}
