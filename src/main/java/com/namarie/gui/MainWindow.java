package com.namarie.gui;

import com.namarie.entity.Media;
import com.namarie.entity.Song;
import com.namarie.logic.Logic;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindow extends javax.swing.JFrame {

    // Regex
    private static final String PATTERN = "([^\\s]+(\\.(?i)(mp3|mp4|wav|wma|mov|wmv|avi|flv|mkv|mpg|mpeg))$)";
    private static final String PATTERN_VIDEO = "([^\\s]+(\\.(?i)(mp4|mov|wmv|avi|flv|mkv|mpg))$)";
    private static final String PATTERN_AUDIO = "([^\\s]+(\\.(?i)(mp3|wav|wma|mpeg))$)";

    // Folders TabPanel
    public static final String KEY_PATH_VIDEOS = "pathVideos";
    public static final String KEY_PATH_SONGS = "pathSongs";
    public static final String KEY_PROMOTIONAL_VIDEO = "promotionalVideo";
    public static final String KEY_PATH_PROMOTIONAL_VIDEO = "pathPromotionalVideo";

    // Time TabPanel
    public static final String KEY_RANDOM_SONG = "randomSong";
    public static final String KEY_REPEAT_SONGS = "repeatSongs";

    // Credits TabPanel
    public static final String KEY_AMOUNT_CREDITS = "amountCredits";
    public static final String KEY_LOCK_SCREEN = "lockScreen";
    public static final String KEY_SAVE_SONGS = "saveSongs";

    // Keys TabPanel
    public static final String KEY_UP_SONG = "upSong";
    public static final String KEY_DOWN_SONG = "downSong";
    public static final String KEY_UP_SONGS = "upSongs";
    public static final String KEY_DOWN_SONGS = "downSongs";
    public static final String KEY_UP_GENDER = "upGender";
    public static final String KEY_DOWN_GENDER = "downGender";
    public static final String KEY_ADD_COIN = "addCoin";
    public static final String KEY_REMOVE_COIN = "removeCoin";
    public static final String KEY_POWER_OFF = "powerOff";
    public static final String KEY_NEXT_SONG = "nextSong";
    public static final String KEY_SETTINGS = "settings";

    //View TabPanel
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";
    public static final String KEY_TEXT_COLOR = "textColor";
    public static final String KEY_FONT = "font";
    public static final String KEY_FONT_STYLE = "fontStyle";
    public static final String KEY_FOREGROUND = "foreground";
    public static final String KEY_FONT_SIZE = "fontSize";
    public static final String KEY_BOLD = "fontBold";

    // MainFrame
    // Folders
    private String videosPath;
    private String songsPath;
    private boolean promotionalVideoValidate;
    private String promotionalVideoPath;

    // Time
    private int randomSong;
    private int repeatSong;

    // Credits
    private int creditsAmount;
    private boolean lockScreen;
    private boolean saveSongs;

    // Keys
    private int upSong;
    private int downSong;
    private int upSongs;
    private int downSongs;
    private int upGender;
    private int downGender;
    private int addCoin;
    private int removeCoin;
    private int powerOff;
    private int nextSong;

    private Dimension resolution;
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
    private JButton searchSongsListButton;
    private SettingsWindow settingsWindow;

    private EmbeddedMediaPlayerComponent videoMediaPlayer;
    private AudioPlayerComponent audioMediaPlayer;

    private ArrayList<Song> musicQueue;
    private ArrayList<Media> videosQueue;
    private ArrayList<Media> promotionalVideos;
    private ArrayList<ArrayList<Song>> musicListByGenders;
    private String[] genders;
    private int selectedGender;
    private int selectedSong;

    private javax.swing.Timer timerRandomSong;
    private javax.swing.Timer timerRandomPromotionalVideo;
    private boolean promotionalVideoStatus;
    private Pattern pattern;
    private Pattern patternVideo;
    private Pattern patternAudio;

    private String[] stringLabel;
    private int currentCredits;

    private Random rand = SecureRandom.getInstanceStrong();

    private final KeyListener mainWindowKeyListener = new KeyListener() {
        /**
         * Invoked when a key has been typed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e
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
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {

            // Event to open a settings window (Key 'Q')
            if (e.getKeyCode() == 81) {
                settingsWindow = new SettingsWindow();
                settingsWindow.setVisible(true);
            }

            // Event to open add coin
            if (e.getKeyCode() == addCoin) {
                if (currentCredits < 25) {
                    currentCredits += 1;
                    creditsValidate(currentCredits > 0);
                }
            }

            // Event to open remove coin
            if (e.getKeyCode() == removeCoin) {
                if (currentCredits > 0) {
                    currentCredits -= 1;
                    creditsValidate(currentCredits > 0);
                }
            }

            // Event to up gender in gender list
            if (e.getKeyCode() == upGender) {
                if (selectedGender < genders.length - 1) {
                    selectedGender++;
                } else {
                    selectedGender = 0;
                }
                setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                songsGenderLabel.setText("Gender: " + genders[selectedGender]);

                selectedSong = 0;
                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to down gender in gender list
            if (e.getKeyCode() == downGender) {
                if (selectedGender > 0) {
                    selectedGender--;
                } else {
                    selectedGender = genders.length - 1;
                }
                setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                songsGenderLabel.setText("Gender: " + genders[selectedGender]);

                selectedSong = 0;
                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to up a song in music list
            if (e.getKeyCode() == upSong) {
                if (selectedSong > 0) {
                    selectedSong--;
                } else {
                    selectedSong = songsListJList.getModel().getSize() - 1;
                }

                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to down a song in music list
            if (e.getKeyCode() == downSong) {
                if (selectedSong < songsListJList.getModel().getSize() - 1) {
                    selectedSong++;
                } else {
                    selectedSong = 0;
                }

                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to up 20 songs in music list
            if (e.getKeyCode() == upSongs) {
                if (selectedSong < songsListJList.getModel().getSize() - 1) {
                    selectedSong += 20;
                    if (selectedSong > songsListJList.getModel().getSize() - 1) {
                        selectedSong = songsListJList.getModel().getSize() - 1;
                    }
                } else {
                    selectedSong = 0;
                }

                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to down 20 songs in music list
            if (e.getKeyCode() == downSongs) {
                if (selectedSong > 0) {
                    selectedSong -= 20;
                    if (selectedSong < 0) {
                        selectedSong = 0;
                    }
                } else {
                    selectedSong = songsListJList.getModel().getSize() - 1;
                }

                songsListJList.setSelectedIndex(selectedSong);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to play the next song in music queue
            if (e.getKeyCode() == nextSong) {

                timerRandomSong.start();

                nextSong();

            }

            // Event to play or add a song to music queue with ENTER
            if (e.getKeyCode() == 10) {

                if (currentCredits > 0) {

                    Song selectedSong = (Song) songsListJList.getSelectedValue();

                    if (selectedSong != null) {

                        if (promotionalVideoStatus) {
                            videoMediaPlayer.mediaPlayer().controls().stop();
                            promotionalVideoStatus = false;
                        }

                        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying()) {

                            playSong(selectedSong);

                        } else {

                            musicQueue.add(selectedSong);

                            setMusicQueue(musicQueue);

                        }

                        currentCredits -= 1;
                        creditsValidate(currentCredits > 0);

                    }
                }

            }

            // Event to reload settings
            if (e.getKeyCode() == 82) {

                selectedGender = 0;

                // Load values from JSON file
                loadSettings(Logic.loadSettings());

                genders = gendersList();

                musicListByGenders = musicListByGenders(musicList(), genders);

                setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                selectedSong = 0;
                songsListJList.setSelectedIndex(selectedSong);

                songsGenderLabel.setText("Gender: " + genders[selectedGender]);
                songsListJList.ensureIndexIsVisible(selectedSong);

            }

            // Event to set '0' value in String to select a song
            if (e.getKeyCode() == 48 || e.getKeyCode() == 96) {
                setString("0");
            }

            // Event to set '1' value in String to select a song
            if (e.getKeyCode() == 49 || e.getKeyCode() == 97) {
                setString("1");
            }

            // Event to set '2' value in String to select a song
            if (e.getKeyCode() == 50 || e.getKeyCode() == 98) {
                setString("2");
            }

            // Event to set '3' value in String to select a song
            if (e.getKeyCode() == 51 || e.getKeyCode() == 99) {
                setString("3");
            }

            // Event to set '4' value in String to select a song
            if (e.getKeyCode() == 52 || e.getKeyCode() == 100) {
                setString("4");
            }

            // Event to set '5' value in String to select a song
            if (e.getKeyCode() == 53 || e.getKeyCode() == 101) {
                setString("5");
            }

            // Event to set '6' value in String to select a song
            if (e.getKeyCode() == 54 || e.getKeyCode() == 102) {
                setString("6");
            }

            // Event to set '7' value in String to select a song
            if (e.getKeyCode() == 55 || e.getKeyCode() == 103) {
                setString("7");
            }

            // Event to set '8' value in String to select a song
            if (e.getKeyCode() == 56 || e.getKeyCode() == 104) {
                setString("8");
            }

            // Event to set '9' value in String to select a song
            if (e.getKeyCode() == 57 || e.getKeyCode() == 105) {
                setString("9");
            }

            // Event to set default value in String to select a song
            if (e.getKeyCode() == 110) {
                setDefaultString();
            }

            // Event to power off computer
            if (e.getKeyCode() == powerOff) {

                String s = JOptionPane.showInputDialog(null, "Password:", "Power off", JOptionPane.PLAIN_MESSAGE);

                if ("031217".equals(s)) {

                    videoMediaPlayer.release();
                    audioMediaPlayer.release();

                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec("shutdown -s -t 0");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);

                }

            }

        }

        /**
         * Invoked when a key has been released.
         * See the class description for {@link KeyEvent} for a definition of
         * a key released event.
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
            e.consume();
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setTitle("Namarie");
        setContentPane(containerPanel);

        containerPanel.addKeyListener(mainWindowKeyListener);
        songsListJList.addListSelectionListener(new ListSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedSong = songsListJList.getSelectedIndex();
            }
        });

        // Regex to extensions
        pattern = Pattern.compile(PATTERN);
        patternVideo = Pattern.compile(PATTERN_VIDEO);
        patternAudio = Pattern.compile(PATTERN_AUDIO);

        // Load data from JSON file
        loadSettings(Logic.loadSettings());

        selectedGender = 0;

        // Validate credits
        currentCredits = 0;
        creditsValidate(currentCredits > 0);

        resolution = Toolkit.getDefaultToolkit().getScreenSize();

        // Reshape components to screen resolution
//        centerPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight()));
//        videoPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight() / 2));
//        musicListPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 4, (int) resolution.getHeight() / 2));
//        songsListPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight()));

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

                        Matcher matcher = patternVideo.matcher(song.getName());

                        if (matcher.find()) {

                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName())));

                        }

                        matcher = patternAudio.matcher(song.getName());

                        if (matcher.find()) {

                            int randVideo = rand.nextInt(videosQueue.size());

                            Media video = videosQueue.get(randVideo);

                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format("%s" + File.separator + "%s", videosPath, video.getName())));
                            audioMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName()));

                        }

                        musicQueue.remove(0);

                        setMusicQueue(musicQueue);

                    } else if (audioMediaPlayer.mediaPlayer().status().isPlaying()) {

                        timerRandomSong.stop();

                        int randVideo = rand.nextInt(videosQueue.size());

                        Media video = videosQueue.get(randVideo);

                        mediaPlayer.submit(() -> mediaPlayer().media().play(String.format("%s" + File.separator + "%s", videosPath, video.getName())));

                    } else {
                        timerRandomPromotionalVideo.start();
                    }

                }

                @Override
                public void error(MediaPlayer mediaPlayer) {
                }
            };

            // Add to the player container our canvas
            videoPanel.add(videoMediaPlayer);
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

                        Matcher matcher = patternVideo.matcher(song.getName());

                        if (matcher.find()) {

                            videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName()));

                        }

                        matcher = patternAudio.matcher(song.getName());

                        if (matcher.find()) {

                            int randVideo = rand.nextInt(videosQueue.size());

                            Media video = videosQueue.get(randVideo);

                            videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s", videosPath, video.getName()));
                            mediaPlayer.submit(() -> mediaPlayer.media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName())));

                        }

                        musicQueue.remove(0);

                        setMusicQueue(musicQueue);

                    } else {

                        timerRandomPromotionalVideo.start();
                    }
                }

                @Override
                public void error(MediaPlayer mediaPlayer) {
                }
            };

        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }

        videosQueue = getVideos(videosPath);
        promotionalVideos = getVideos(promotionalVideoPath);

        genders = gendersList();

        musicListByGenders = musicListByGenders(musicList(), genders);

        musicQueue = new ArrayList<>();

        if (musicListByGenders != null) {

            setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

            selectedSong = 0;

            songsListJList.setSelectedIndex(selectedSong);

            songsGenderLabel.setText(genders[selectedGender]);
        }

        stringLabel = new String[5];

        setDefaultString();

        ActionListener playRandomSong = e -> {

            playRandomSong();

        };

        ActionListener playRandomPromotionalVideo = e -> {

            playRandomPromotionalVideo();

        };

        timerRandomSong = new Timer(randomSong * 60000, playRandomSong);
        timerRandomSong.setRepeats(false);
        timerRandomSong.start();

        timerRandomPromotionalVideo = new Timer(0, playRandomPromotionalVideo);
        timerRandomPromotionalVideo.setRepeats(false);
        timerRandomPromotionalVideo.start();

    }

    private void creditsValidate(boolean state) {

        musicListPanel.setVisible(state);
        songsListPanel.setVisible(state);

        currentCreditsLabel.setText(String.format("Credits: %s", currentCredits));

    }

    private String[] gendersList() {

        String[] genders = null;

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return null;

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s" + File.separator + "%s", songsPath, gender));

            if (!genderDirectory.isDirectory()) {
                List<String> gendersList = new ArrayList<>(Arrays.asList(genders));
                gendersList.remove(gender);
                genders = gendersList.toArray(new String[0]);
            }

        }

        return genders;
    }

    private void playRandomSong() {

        // getContentPane().requestFocus();

        if (promotionalVideoStatus) {

            videoMediaPlayer.mediaPlayer().controls().stop();
            promotionalVideoStatus = false;

        }

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty()) {

            int randSong = rand.nextInt(musicList().size());

            Song song = musicList().get(randSong);

            playSong(song);

        }

    }

    private void playRandomPromotionalVideo() {

        // getContentPane().requestFocus();

        if (!videoMediaPlayer.mediaPlayer().status().isPlaying() && !audioMediaPlayer.mediaPlayer().status().isPlaying() && musicQueue.isEmpty()) {

            int randSong = rand.nextInt(promotionalVideos.size());

            Media promotionalVideo = promotionalVideos.get(randSong);

            playPromotionalMedia(promotionalVideo);

        }

    }

    private void playSong(Song song) {

        Matcher matcher = patternVideo.matcher(song.getName());

        if (matcher.find()) {

            videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName()));

        }

        matcher = patternAudio.matcher(song.getName());

        if (matcher.find()) {

            int randVideo = rand.nextInt(videosQueue.size());

            Media video = videosQueue.get(randVideo);

            videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s", videosPath, video.getName()));
            audioMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName()));

        }

        if (timerRandomSong.isRunning()) {

            timerRandomSong.stop();

        }

    }

    private void playPromotionalMedia(Media video) {

        videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s", promotionalVideoPath, video.getName()));

        promotionalVideoStatus = true;

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

    private void nextSong(MediaPlayer mediaPlayer) {

        Song song = musicQueue.get(0);

        mediaPlayer.submit(() -> mediaPlayer.media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName())));

        musicQueue.remove(0);

        setMusicQueue(musicQueue);

    }

    private void setDefaultString() {

        for (int i = 0; i < stringLabel.length; i++) {

            stringLabel[i] = "-";

        }

        numberSong.setText(String.format(" %s %s %s %s %s ", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

    }

    private void setString(String value) {

        if (currentCredits > 0) {

            for (int i = 0; i < stringLabel.length; i++) {
                if ("-".equals(stringLabel[i])) {
                    stringLabel[i] = value;
                    break;
                }
            }

            if (!Arrays.stream(stringLabel).anyMatch("-"::equals)) {

                selectedSong = Integer.parseInt(String.format("%s%s%s%s%s", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

                if (selectedSong <= musicList().size() - 1) {

                    Song song = musicList().get(selectedSong);

                    if (videoMediaPlayer.mediaPlayer().status().isPlaying()) {

                        musicQueue.add(song);
                        setMusicQueue(musicQueue);

                    }

                    if (musicQueue.isEmpty()) {

                        videoMediaPlayer.mediaPlayer().media().play(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, song.getGender(), song.getSinger(), song.getName()));

                    }

                    currentCredits -= 1;
                    creditsValidate(currentCredits > 0);

                }

                setDefaultString();
            }

            numberSong.setText(String.format(" %s %s %s %s %s ", stringLabel[0], stringLabel[1], stringLabel[2], stringLabel[3], stringLabel[4]));

        }

    }

    private ArrayList<Media> getVideos(String videosPath) {

        String[] videos = null;
        int videoCounter = 0;
        ArrayList<Media> videoList = new ArrayList<>();

        File directory = new File(videosPath);

        if (directory.isDirectory()) videos = directory.list();

        if (videos == null) return null;

        for (String video : videos) {

            File videoDirectory = new File(String.format("%s" + File.separator + "%s", videosPath, video));

            if (videoDirectory.isFile()) {

                Matcher matcher = pattern.matcher(videoDirectory.getName());

                if (matcher.find()) {

                    videoList.add(new Media(videoCounter, video));
                    videoCounter++;

                }

            }

        }

        return videoList;

    }

    private ArrayList<Song> musicList() {

        String[] genders = null;
        String[] singers;
        String[] songs;
        int songCounter = 0;
        ArrayList<Song> musicList = new ArrayList<>();

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return null;

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s" + File.separator + "%s", songsPath, gender));

            if (genderDirectory.isDirectory()) {

                singers = genderDirectory.list();

                for (String singer : singers) {

                    File singerDirectory = new File(String.format("%s" + File.separator + "%s" + File.separator + "%s", songsPath, gender, singer));

                    if (singerDirectory.isDirectory()) {

                        songs = singerDirectory.list();

                        for (String song : songs) {

                            File songFile = new File(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, gender, singer, song));

                            if (songFile.isFile()) {

                                Matcher matcher = pattern.matcher(songFile.getName());

                                if (matcher.find()) {

                                    musicList.add(new Song(songCounter, song, singer, gender));
                                    songCounter++;

                                }

                            }
                        }
                    }
                }
            }
        }

        return musicList;

    }

    private ArrayList<ArrayList<Song>> musicListByGenders(ArrayList<Song> musicList, String[] gendersList) {

        ArrayList<ArrayList<Song>> musicListByGenders = new ArrayList<>();

        if (gendersList == null) return null;

        for (String gender : gendersList) {
            ArrayList<Song> musicListByGender = new ArrayList<>();
            for (Song song : musicList) {
                if (gender.equals(song.getGender())) {
                    musicListByGender.add(song);
                }

            }
            musicListByGenders.add(musicListByGender);
        }

        return musicListByGenders;
    }

    private void setMusicQueue(ArrayList<Song> musicQueue) {

        if (musicQueue != null) {

            DefaultListModel model = new DefaultListModel();

            for (Song song : musicQueue) {

                model.addElement(song);

            }

            musicQueueJList.setModel(model);

        }

    }

    private void setMusicList(ArrayList<Song> musicList, String selectedGender) {


        if (musicList != null) {

            DefaultListModel model = new DefaultListModel();

            for (Song song : musicList) {
                if (selectedGender.equals(song.getGender())) model.addElement(song);
            }

            songsListJList.setModel(model);
        }

    }

    private void loadSettings(JSONObject values) {

        try {
            //Folders
            videosPath = (String) values.get(KEY_PATH_VIDEOS);
            songsPath = (String) values.get(KEY_PATH_SONGS);
            promotionalVideoValidate = (boolean) values.get(KEY_PROMOTIONAL_VIDEO);
            promotionalVideoPath = (String) values.get(KEY_PATH_PROMOTIONAL_VIDEO);

            //Time
            randomSong = (int) values.get(KEY_RANDOM_SONG);
            repeatSong = (int) values.get(KEY_REPEAT_SONGS);

            //Credits
            creditsAmount = (int) values.get(KEY_AMOUNT_CREDITS);
            lockScreen = (boolean) values.get(KEY_LOCK_SCREEN);
            saveSongs = (boolean) values.get(KEY_SAVE_SONGS);

            //Keys
            upSong = (int) values.get(KEY_UP_SONG);
            downSong = (int) values.get(KEY_DOWN_SONG);
            upSongs = (int) values.get(KEY_UP_SONGS);
            downSongs = (int) values.get(KEY_DOWN_SONGS);
            upGender = (int) values.get(KEY_UP_GENDER);
            downGender = (int) values.get(KEY_DOWN_GENDER);
            addCoin = (int) values.get(KEY_ADD_COIN);
            removeCoin = (int) values.get(KEY_REMOVE_COIN);
            powerOff = (int) values.get(KEY_POWER_OFF);
            nextSong = (int) values.get(KEY_NEXT_SONG);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
