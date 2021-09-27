package com.namarie.gui;

import com.namarie.filemanager.FileManager;
import com.namarie.models.Song;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWindow extends javax.swing.JFrame {

    // Folders TabPanel
    public final static String KEY_PATH_VLC = "pathVLC";
    public final static String KEY_PATH_VIDEOS = "pathVideos";
    public final static String KEY_PATH_SONGS = "pathSongs";
    public final static String KEY_PROMOTIONAL_VIDEO = "promotionalVideo";
    public final static String KEY_PATH_PROMOTIONAL_VIDEO = "pathPromotionalVideo";

    // Time TabPanel
    public final static String KEY_RANDOM_SONG = "randomSong";
    public final static String KEY_REPEAT_SONGS = "repeatSongs";

    // Credits TabPanel
    public final static String KEY_AMOUNT_CREDITS = "amountCredits";
    public final static String KEY_LOCK_SCREEN = "lockScreen";
    public final static String KEY_SAVE_SONGS = "saveSongs";

    // Keys TabPanel
    public final static String KEY_UP_SONG = "upSong";
    public final static String KEY_DOWN_SONG = "downSong";
    public final static String KEY_UP_SONGS = "upSongs";
    public final static String KEY_DOWN_SONGS = "downSongs";
    public final static String KEY_UP_GENDER = "upGender";
    public final static String KEY_DOWN_GENDER = "downGender";
    public final static String KEY_ADD_COIN = "addCoin";
    public final static String KEY_REMOVE_COIN = "removeCoin";
    public final static String KEY_POWER_OFF = "powerOff";
    public final static String KEY_NEXT_SONG = "nextSong";
    public final static String KEY_SETTINGS = "settings";

    //View TabPanel
    public final static String KEY_COLOR1 = "color1";
    public final static String KEY_COLOR2 = "color2";
    public final static String KEY_FONT = "font";
    public final static String KEY_FOREGROUND = "foreground";
    public final static String KEY_FONT_SIZE = "fontSize";
    public final static String KEY_BOLD = "bold";

    // MainFrame
    // Folders
    private String vlcPath;
    private String videosPath;
    private String songsPath;
    private boolean promotionalVideo;
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
    private int widthScreen;
    private int heightScreen;
    private JPanel containerPanel;
    private JPanel videoPanel;
    private JPanel musicListPanel;
    private JPanel songsListPanel;
    private JList songsListJList;
    private JList musicListJList;
    private JScrollPane musicListScrollPanel;
    private JScrollPane songsListScrollPanel;
    private JPanel centerPanel;
    private JLabel songsGenderLabel;
    private JLabel numberSong;
    private JLabel currentCreditsLabel;
    private JPanel searchSongsListPanel;
    private JTextField searchSongsListTextField;
    private JButton searchSongsListButton;
    private SettingsWindow settingsWindow;

    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    private ArrayList<ArrayList<Song>> musicListByGenders;
    private String[] genders;
    private int selectedGender;
    private int selectedSong;

    private int currentCredits;

    // Data
    private JSONObject loadedSettings;
    private final FileManager fileManager = new FileManager();

    public MainWindow() {

        resolution = Toolkit.getDefaultToolkit().getScreenSize();
//        System.out.println(resolution.toString());

        this.setTitle("MainWindow");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
//        this.setResizable(false);
//        this.setUndecorated(true);
        this.setMaximumSize(resolution);

        getContentPane().add(containerPanel);
        this.setVisible(true);

        initComponents();

        getContentPane().addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been pressed.
             *
             * @param e
             */
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 81) {
                    settingsWindow = new SettingsWindow();
                    settingsWindow.setVisible(true);
                }
            }
        });
        getContentPane().addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been pressed.
             *
             * @param e
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 67) {
                    musicListPanel.setVisible(!musicListPanel.isVisible());
                    songsListPanel.setVisible(!songsListPanel.isVisible());
                }
                if (e.getKeyCode() == upGender) {
                    if (selectedGender < genders.length - 1) {
                        selectedGender++;
                    } else {
                        selectedGender = 0;
                    }
                    setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                    songsGenderLabel.setText(genders[selectedGender]);

                    selectedSong = 0;
                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == downGender) {
                    if (selectedGender > 0) {
                        selectedGender--;
                    } else {
                        selectedGender = genders.length - 1;
                    }
                    setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                    songsGenderLabel.setText(genders[selectedGender]);

                    selectedSong = 0;
                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == upSong) {
                    if (selectedSong > 0) {
                        selectedSong--;
                    } else {
                        selectedSong = songsListJList.getModel().getSize() - 1;
                    }

                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == downSong) {
                    if (selectedSong < songsListJList.getModel().getSize() - 1) {
                        selectedSong++;
                    } else {
                        selectedSong = 0;
                    }

                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == downSongs) {
                    if (selectedSong > 0) {
                        selectedSong -= 20;
                        if(selectedSong < 0){
                            selectedSong = 0;
                        }
                    } else {
                        selectedSong = songsListJList.getModel().getSize() - 1;
                    }

                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == upSongs) {
                    if (selectedSong < songsListJList.getModel().getSize() - 1) {
                        selectedSong += 20;
                        if(selectedSong > songsListJList.getModel().getSize() - 1){
                            selectedSong = songsListJList.getModel().getSize() - 1;
                        }
                    } else {
                        selectedSong = 0;
                    }

                    songsListJList.setSelectedIndex(selectedSong);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
                if (e.getKeyCode() == powerOff) {

//                    embeddedMediaPlayer.stop();

                }
                if (e.getKeyCode() == 10) {
                    Song selectedSong = (Song) songsListJList.getSelectedValue();

                    if (selectedSong != null) {
//                        embeddedMediaPlayer.playMedia(String.format("%s\\%s\\%s\\%s", songsPath, selectedSong.getGender(), selectedSong.getSinger(), selectedSong.getName()));
                    }
                }
                if (e.getKeyCode() == 82) {

                    selectedGender = 0;

                    // Load values from JSON file
                    loadedSettings = fileManager.openFile(new java.io.File("") + "config.json");
                    loadSettings(loadedSettings);

                    genders = gendersList();

                    musicListByGenders = musicListByGenders(musicList(), genders);

                    setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

                    selectedSong = 0;
                    songsListJList.setSelectedIndex(selectedSong);

                    songsGenderLabel.setText(genders[selectedGender]);
                    songsListJList.ensureIndexIsVisible(selectedSong);

                }
            }
        });
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
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }

    public void initComponents() {

        selectedGender = 0;

        // Load data from JSON file
        File file = new File(new java.io.File("") + "config.json");
        if (!file.exists())
            fileManager.saveDefaultSettings();

        loadedSettings = fileManager.openFile(new java.io.File("") + "config.json");
        loadSettings(loadedSettings);

        // Validate credits
        currentCredits = 0;
        currentCreditsLabel.setText(String.format("Creditos disponibles: %d", currentCredits));

        // Reshape components to screen resolution
        centerPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight()));
        videoPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight() / 2));
        musicListPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 4, (int) resolution.getHeight() / 2));
        songsListPanel.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight()));

        // Create a VLC instance and add to the video panel
        try {
            mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

            // A Canvas is created
            Canvas canvas = new Canvas();
//            canvas.setBackground(Color.WHITE);
//            canvas.setPreferredSize(new Dimension((int) resolution.getWidth() / 2, (int) resolution.getHeight() / 2));

            // Add to the player container our canvas
            videoPanel.add(mediaPlayerComponent);

            // Instantiating a MediaPlayerFactory. Create an embedded player and add our canvas

            // Play video
            mediaPlayerComponent.setFocusable(false);
            mediaPlayerComponent.mediaPlayer().media().play("C:" + File.separator + "Users" + File.separator + "leron" + File.separator + "Music" + File.separator + "Electronica" + File.separator + "League of Legends" + File.separator + "Phoenix ft Cailin Russo and Chrissy Costanza  Worlds 2019  League of Legends.mp4");


        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }

        genders = gendersList();

        musicListByGenders = musicListByGenders(musicList(), genders);


        if (musicListByGenders != null) {

            setMusicList(musicListByGenders.get(selectedGender), genders[selectedGender]);

            selectedSong = 0;

            songsListJList.setSelectedIndex(selectedSong);

            songsGenderLabel.setText(genders[selectedGender]);
        }

        getContentPane().requestFocus();
        System.out.println("aca");

    }

    private String[] gendersList() {

        String[] genders = null;

        File directory = new File(songsPath);

        if (directory.isDirectory())
            genders = directory.list();

        if (genders == null)
            return null;

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s/%s", songsPath, gender));

            if (!genderDirectory.isDirectory()) {
                List<String> gendersList = new ArrayList<String>(Arrays.asList(genders));
                gendersList.remove(gender);
                genders = gendersList.toArray(new String[0]);
            }

        }

        return genders;
    }

    private ArrayList<Song> musicList() {

        String[] genders = null;
        String[] singers;
        String[] songs;
        int songCounter = 0;
        ArrayList<Song> musicList = new ArrayList<>();

        File directory = new File(songsPath);

        if (directory.isDirectory())
            genders = directory.list();

        if (genders == null)
            return null;

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s/%s", songsPath, gender));

            if (genderDirectory.isDirectory()) {

                singers = genderDirectory.list();

                for (String singer : singers) {

                    File singerDirectory = new File(String.format("%s/%s/%s", songsPath, gender, singer));

                    if (singerDirectory.isDirectory()) {

                        songs = singerDirectory.list();

                        for (String song : songs) {

                            File songFile = new File(String.format("%s/%s/%s/%s", songsPath, gender, singer, song));

                            if (songFile.isFile()) {
                                String extension = "";

                                int pointIndex = songFile.getName().lastIndexOf('.');

                                if (pointIndex > 0) {
                                    extension = songFile.getName().substring(pointIndex + 1);

                                    // TODO validate extensions
                                    if (!extension.equals("pdf")) {
                                        musicList.add(new Song(songCounter, song, singer, gender));
                                        songCounter++;
                                    }
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

        if (gendersList == null)
            return null;

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

    private void setMusicList(ArrayList<Song> musicList, String selectedGender) {


        if (musicList != null) {

            DefaultListModel model = new DefaultListModel();

            for (Song song : musicList) {
                if (selectedGender.equals(song.getGender()))
                    model.addElement(song);
            }

            songsListJList.setModel(model);
        }

    }

    private void loadSettings(JSONObject values) {

        try {
            //Folders
            vlcPath = (String) values.get(KEY_PATH_VLC);
            videosPath = (String) values.get(KEY_PATH_VIDEOS);
            songsPath = (String) values.get(KEY_PATH_SONGS);
            promotionalVideo = (boolean) values.get(KEY_PROMOTIONAL_VIDEO);
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
