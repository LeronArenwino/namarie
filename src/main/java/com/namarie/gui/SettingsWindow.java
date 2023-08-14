package com.namarie.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.Properties;

import static com.namarie.dao.PropertiesManager.*;
import static com.namarie.controller.JukeboxController.loadData;
import static com.namarie.logic.SettingsSingleton.*;

public class SettingsWindow extends JFrame {

    // Fonts
    protected static final String[] fontFamilyNames = graphicsEnvironment.getAvailableFontFamilyNames();

    private JPanel containerPanel;
    private JPanel centerPanel;
    private JTabbedPane containerTabbedPane;
    private JPanel welcomePanel;
    private JPanel foldersPanel;
    private JPanel timePanel;
    private JPanel creditsPanel;
    private JPanel musicListQueueViewPanel;
    private JLabel titleWelcomePanel;
    private JLabel titleFoldersPanel;
    private JLabel titleTimePanel;
    private JLabel tittleCreditsPanel;
    private JLabel titleMusicListQueueViewNorthPanel;
    private JPanel containerFoldersPanel;
    private JTextPane textFoldersPanel;
    private JPanel southPanel;
    private JFormattedTextField videoTextField;
    private JButton videoSearchButton;
    private JFormattedTextField musicTextField;
    private JButton musicSearchButton;
    private JButton saveButton;
    private JButton defaultButton;
    private JPanel containerTimePanel;
    private JCheckBox promotionalVideoCheckBox;
    private JFormattedTextField promotionalTextField;
    private JButton promotionalSearchButton;
    private JTextPane textTimePanel;
    private JComboBox<Integer> timeRandomSongComboBox;
    private JComboBox<Integer> timeLimitComboBox;
    private JTextPane textCreditsPanel;
    private JComboBox<Integer> creditsComboBox;
    private JCheckBox blockScreenWhenHavenCheckBox;
    private JCheckBox saveSongsWhenPowerCheckBox;
    private JPanel containerCreditsPanel;
    private JPanel containerMusicListQueueViewPanel;
    private JTextPane textMusicListQueueViewPanel;
    private JButton backgroundColorSearchButton;
    private JComboBox<String> fontComboBox;
    private JButton foregroundSearchButton;
    private JComboBox<Integer> fontSizeComboBox;
    private JTextPane textWelcomePanel;
    private JLabel videoLabel;
    private JLabel musicLabel;
    private JPanel containerWelcomePanel;
    private JLabel timeSongLabel;
    private JLabel timeLimitLabel;
    private JLabel creditsLabel;
    private JLabel backgroundColorMainTextLabel;
    private JLabel backgroundColorMainViewLabel;
    private JLabel fontLabel;
    private JLabel foregroundColorMainTextLabel;
    private JLabel fontSizeLabel;
    private JComboBox<String> fontStyleComboBox;
    private JButton exitButton;
    private JLabel foregroundColorViewLabel;
    private JPanel songListViewPanel;
    private JPanel containerListViewPanel;
    private JLabel titleSongListViewNorthPanel;

    // Song list components
    private JLabel songListBackgroundLabel;
    private JLabel songListFontLabel;
    private JComboBox<String> songListFontJComboBox;
    private JLabel songListForegroundLabel;
    private JLabel songListFontSizeLabel;
    private JComboBox<Integer> songListFontSizeJComboBox;
    private JTextPane textSongListViewPanel;
    private JLabel songListColorBackgroundLabel;
    private JLabel songListColorForegroundLabel;
    private JButton songListColorForegroundJButton;
    private JButton songListColorBackgroundJButton;
    private JCheckBox usingMetadaFilesCheckBox;
    private transient ComboBoxModel<String> songListFonts;

    private transient ComboBoxModel<String> modelFonts;

    public SettingsWindow() {

        this.setTitle("Settings");
        this.setMinimumSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT / 2));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.getContentPane().add(containerPanel);
        this.setVisible(false);

        initComponents();

        paintComponents();

        loadSettings();

        saveButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                int selection = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if (selection == JOptionPane.YES_NO_OPTION) {
                    saveProperties(settingsToProperties());
                    loadSettings();

                    JOptionPane.showMessageDialog(null, "Properties saved!");
                }

            }
        });
        defaultButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int selection = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if (selection == JOptionPane.YES_NO_OPTION) {
                    saveProperties(defaultSettingsProperties());
                    loadSettings();

                    JOptionPane.showMessageDialog(null, "Default properties saved!");
                }
            }
        });
        videoSearchButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogTitle("Video Directory");
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    // This returns the path from the selected file
                    videoTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        musicSearchButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogTitle("Music Directory");
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    // This returns the path from the selected file
                    musicTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        promotionalSearchButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogTitle("Promotional Videos Directory");
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    // This returns the path from the selected file
                    promotionalTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        promotionalVideoCheckBox.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                promotionalTextField.setEnabled(promotionalVideoCheckBox.isSelected());
                promotionalSearchButton.setEnabled(promotionalVideoCheckBox.isSelected());
            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        songListColorBackgroundJButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Select a color", Color.decode(getSongListValueBackground()));
                songListColorBackgroundLabel.setBackground(color);
            }
        });
        songListColorForegroundJButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Select a color", Color.decode(getSongListValueForeground()));
                songListColorForegroundLabel.setBackground(color);
            }
        });
    }

    private void initComponents() {

        modelFonts = new DefaultComboBoxModel<>(fontFamilyNames);
        songListFonts = new DefaultComboBoxModel<>(fontFamilyNames);

    }

    private void paintComponents() {

        fontComboBox.setModel(modelFonts);
        songListFontJComboBox.setModel(songListFonts);

    }

    private void loadSettings() {

        setSettingsFromProperties(loadProperties());

        // Metadata files
        usingMetadaFilesCheckBox.setSelected(getHasMetadata());

        // Paths control section
        videoTextField.setText(getPathToVideos());
        musicTextField.setText(getPathToSongs());
        promotionalVideoCheckBox.setSelected(isPromotionalVideos());
        if (isPromotionalVideos())
            promotionalTextField.setText(getPathToPromotionalVideos());
        else
            promotionalTextField.setText("");
        promotionalTextField.setEnabled(isPromotionalVideos());
        promotionalSearchButton.setEnabled(isPromotionalVideos());

        // Time control section
        timeRandomSongComboBox.setSelectedIndex(getTimeToPlayRandomSongs());
        timeLimitComboBox.setSelectedIndex(getTimeToRepeatSongs());

        // Credits control section
        creditsComboBox.setSelectedIndex(getAmountCredits());
        blockScreenWhenHavenCheckBox.setSelected(isLockScreen());
        saveSongsWhenPowerCheckBox.setSelected(isSaveSongs());

        // Main view control section
        backgroundColorMainViewLabel.setBackground(Color.decode(getValueBackground()));
        foregroundColorViewLabel.setBackground(Color.decode(getValueForeground()));
        fontComboBox.setSelectedItem(getValueFont());
        fontSizeComboBox.setSelectedItem(String.valueOf(getValueFontSize()));

        // Song list view control
        songListColorBackgroundLabel.setBackground(Color.decode(getSongListValueBackground()));
        songListColorForegroundLabel.setBackground(Color.decode(getSongListValueForeground()));
        songListFontJComboBox.setSelectedItem(getSongListValueFont());
        songListFontSizeJComboBox.setSelectedItem(String.valueOf(getSongListValueFontSize()));

        loadData();

    }

    private Properties settingsToProperties() {

        Properties properties = new Properties();

        // Metadata files
        properties.put(KEY_IS_METADATA, String.valueOf(usingMetadaFilesCheckBox.isSelected()));

        // Path settings
        properties.put(KEY_PATH_TO_VIDEOS, videoTextField.getText());
        properties.put(KEY_PATH_TO_SONGS, musicTextField.getText());
        properties.put(KEY_IS_PROMOTIONAL_VIDEOS, String.valueOf(promotionalVideoCheckBox.isSelected()));
        properties.put(KEY_PATH_TO_PROMOTIONAL_VIDEOS, promotionalTextField.getText());

        //Time
        properties.put(KEY_TIME_TO_PLAY_RANDOM_SONGS, String.valueOf(timeRandomSongComboBox.getSelectedIndex()));
        properties.put(KEY_TIME_TO_REPEAT_SONGS, String.valueOf(timeLimitComboBox.getSelectedIndex()));

        //Credits
        properties.put(KEY_AMOUNT_CREDITS, String.valueOf(creditsComboBox.getSelectedIndex()));
        properties.put(KEY_LOCK_SCREEN, String.valueOf(blockScreenWhenHavenCheckBox.isSelected()));
        properties.put(KEY_SAVE_SONGS, String.valueOf(saveSongsWhenPowerCheckBox.isSelected()));

        // Main view settings
        properties.put(KEY_BACKGROUND, "#" + Integer.toHexString(backgroundColorMainViewLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_FOREGROUND, "#" + Integer.toHexString(foregroundColorViewLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_FONT, Objects.requireNonNull(fontComboBox.getSelectedItem()).toString());
        properties.put(KEY_FONT_SIZE, Objects.requireNonNull(fontSizeComboBox.getSelectedItem()).toString());

        // Main view settings
        properties.put(KEY_SONG_LIST_BACKGROUND, "#" + Integer.toHexString(songListColorBackgroundLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_SONG_LIST_FOREGROUND, "#" + Integer.toHexString(songListColorForegroundLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_SONG_LIST_FONT, Objects.requireNonNull(songListFontJComboBox.getSelectedItem()).toString());
        properties.put(KEY_SONG_LIST_FONT_SIZE, Objects.requireNonNull(songListFontSizeJComboBox.getSelectedItem()).toString());

        return properties;

    }

}
