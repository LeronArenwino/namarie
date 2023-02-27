package com.namarie.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;

import static com.namarie.dao.PropertiesManager.*;
import static com.namarie.logic.SettingsSingleton.*;

public class SettingsWindow extends JFrame {

    private JPanel containerPanel;
    private JPanel centerPanel;
    private JTabbedPane containerTabbedPane;
    private JPanel welcomePanel;
    private JPanel foldersPanel;
    private JPanel timePanel;
    private JPanel creditsPanel;
    private JPanel keysPanel;
    private JPanel mainViewPanel;
    private JLabel titleWelcomePanel;
    private JLabel titleFoldersPanel;
    private JLabel titleTimePanel;
    private JLabel tittleCreditsPanel;
    private JLabel titleKeysPanel;
    private JLabel titleMainViewPanel;
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
    private JTextPane textKeysPanel;
    private JButton upSongButton;
    private JButton downSongButton;
    private JButton upGenderButton;
    private JButton downGenderButton;
    private JButton addCoinButton;
    private JButton removeCoinButton;
    private JButton powerOffButton;
    private JButton nextSongButton;
    private JPanel containerKeysPanel;
    private JPanel containerCreditsPanel;
    private JPanel containerMainViewPanel;
    private JTextPane textViewPanel;
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
    private JLabel upSongLabel;
    private JLabel upGenderLabel;
    private JLabel addCoinLabel;
    private JLabel powerOffLabel;
    private JLabel downSongLabel;
    private JLabel downGenderLabel;
    private JLabel removeCoinLabel;
    private JLabel nextSongLabel;
    private JLabel backgroundColorMainTextLabel;
    private JLabel backgroundColorMainViewLabel;
    private JLabel fontLabel;
    private JLabel foregroundColorMainTextLabel;
    private JLabel fontSizeLabel;
    private JLabel upSongSymbolLabel;
    private JLabel downSongSymbolLabel;
    private JLabel upGenderSymbolLabel;
    private JLabel downGenderSymbolLabel;
    private JLabel removeCoinSymbolLabel;
    private JLabel nextSongSymbolLabel;
    private JLabel powerOffSymbolLabel;
    private JLabel addCoinSymbolLabel;
    private JButton downSongsButton;
    private JButton upSongsButton;
    private JLabel upSongsLabel;
    private JLabel upSongsSymbolLabel;
    private JLabel downSongsSymbolLabel;
    private JLabel downSongsLabel;
    private JLabel fontStyleLabel;
    private JComboBox<String> fontStyleComboBox;
    private JButton exitButton;
    private JLabel foregroundColorViewLabel;
    private JPanel listViewPanel;
    private JPanel containerListViewPanel;
    private JLabel titleListViewPanel;

    public SettingsWindow() {

        initComponents();

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
        upSongButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        upSongSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        upSongButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        downSongButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        downSongSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        downSongButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        upGenderButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        upGenderSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        upGenderButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        downGenderButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        downSongSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        downGenderButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        addCoinButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        addCoinSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        addCoinButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        removeCoinButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        removeCoinSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        removeCoinButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        powerOffButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        powerOffSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        powerOffButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
            }
        });
        nextSongButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = keySelector();
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        nextSongSymbolLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        nextSongButton.setText(Integer.toString(e.getKeyCode()));
                        dialog.dispose();
                    }
                });
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
    }

    private void initComponents() {

        this.addWindowListener(new WindowListener() {


            /**
             * Invoked the first time a window is made visible.
             *
             * @param e
             */
            @Override
            public void windowOpened(WindowEvent e) {
            }

            /**
             * Invoked when the user attempts to close the window
             * from the window's system menu.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
            }

            /**
             * Invoked when a window has been closed as the result
             * of calling dispose on the window.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                MainWindow.timerFocusMainPanel.start();
            }

            /**
             * Invoked when a window is changed from a normal to a
             * minimized state. For many platforms, a minimized window
             * is displayed as the icon specified in the window's
             * iconImage property.
             *
             * @param e
             * @see Frame#setIconImage
             */
            @Override
            public void windowIconified(WindowEvent e) {

            }

            /**
             * Invoked when a window is changed from a minimized
             * to a normal state.
             *
             * @param e
             */
            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            /**
             * Invoked when the Window is set to be the active Window. Only a Frame or
             * a Dialog can be the active Window. The native windowing system may
             * denote the active Window or its children with special decorations, such
             * as a highlighted title bar. The active Window is always either the
             * focused Window, or the first Frame or Dialog that is an owner of the
             * focused Window.
             *
             * @param e
             */
            @Override
            public void windowActivated(WindowEvent e) {

            }

            /**
             * Invoked when a Window is no longer the active Window. Only a Frame or a
             * Dialog can be the active Window. The native windowing system may denote
             * the active Window or its children with special decorations, such as a
             * highlighted title bar. The active Window is always either the focused
             * Window, or the first Frame or Dialog that is an owner of the focused
             * Window.
             *
             * @param e
             */
            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        this.setTitle("Settings");
        this.setMinimumSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT / 2));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.getContentPane().add(containerPanel);
        this.setVisible(false);

        paintComponents();

        loadSettings();

    }

    private void paintComponents() {

        ComboBoxModel<String> modelFonts = new DefaultComboBoxModel<>(fontFamilyNames);
        fontComboBox.setModel(modelFonts);

    }

    private void loadSettings() {

        setSettingsFromProperties(loadProperties());

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

        // Keys control section
        upSongSymbolLabel.setText(KeyEvent.getKeyText(getValueToUpIndex()));
        upSongButton.setText(Integer.toString(getValueToUpIndex()));

        downSongSymbolLabel.setText(KeyEvent.getKeyText(getValueToDownIndex()));
        downSongButton.setText(Integer.toString(getValueToDownIndex()));

        upSongsSymbolLabel.setText(KeyEvent.getKeyText(getValueToUpIndexes()));
        upSongsButton.setText(Integer.toString(getValueToUpIndexes()));

        downSongsSymbolLabel.setText(KeyEvent.getKeyText(getValueToDownIndexes()));
        downSongsButton.setText(Integer.toString(getValueToDownIndexes()));

        upGenderSymbolLabel.setText(KeyEvent.getKeyText(getValueToChangeGenderToUp()));
        upGenderButton.setText(Integer.toString(getValueToChangeGenderToUp()));

        downGenderSymbolLabel.setText(KeyEvent.getKeyText(getValueToChangeGenderToDown()));
        downGenderButton.setText(Integer.toString(getValueToChangeGenderToDown()));

        addCoinSymbolLabel.setText(KeyEvent.getKeyText(getValueToAddCoin()));
        addCoinButton.setText(Integer.toString(getValueToAddCoin()));

        removeCoinSymbolLabel.setText(KeyEvent.getKeyText(getValueToRemoveCoin()));
        removeCoinButton.setText(Integer.toString(getValueToRemoveCoin()));

        powerOffSymbolLabel.setText(KeyEvent.getKeyText(getValueToPowerOff()));
        powerOffButton.setText(Integer.toString(getValueToPowerOff()));

        nextSongSymbolLabel.setText(KeyEvent.getKeyText(getValueToPlayNextSong()));
        nextSongButton.setText(Integer.toString(getValueToPlayNextSong()));

        // Main view control section
        backgroundColorMainViewLabel.setBackground(Color.decode(getValueBackgroundColor()));
        foregroundColorViewLabel.setBackground(Color.decode(getValueForeground()));
        fontComboBox.setSelectedItem(getValueFont());
        fontStyleComboBox.setSelectedItem(getValueFontStyle());
        fontSizeComboBox.setSelectedItem(String.valueOf(getValueFontSize()));

    }

    private Properties settingsToProperties() {

        Properties properties = new Properties();

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

        //Keys
        properties.put(KEY_UP_SONG, upSongButton.getText());
        properties.put(KEY_DOWN_SONG, downSongButton.getText());
        properties.put(KEY_UP_SONGS, upSongsButton.getText());
        properties.put(KEY_DOWN_SONGS, downSongsButton.getText());
        properties.put(KEY_UP_GENDER, upGenderButton.getText());
        properties.put(KEY_DOWN_GENDER, downGenderButton.getText());
        properties.put(KEY_ADD_COIN, addCoinButton.getText());
        properties.put(KEY_REMOVE_COIN, removeCoinButton.getText());
        properties.put(KEY_POWER_OFF, powerOffButton.getText());
        properties.put(KEY_NEXT_SONG, nextSongButton.getText());

        // Main view settings
        properties.put(KEY_BACKGROUND_COLOR, "#" + Integer.toHexString(backgroundColorMainViewLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_FOREGROUND, "#" + Integer.toHexString(foregroundColorViewLabel.getBackground().getRGB()).substring(2).toUpperCase());
        properties.put(KEY_FONT, fontComboBox.getSelectedItem().toString());
        properties.put(KEY_FONT_STYLE, fontStyleComboBox.getSelectedItem().toString());
        properties.put(KEY_FONT_SIZE, fontSizeComboBox.getSelectedItem().toString());

        return properties;

    }

    private JDialog keySelector() {
        JDialog dialog = new JDialog();

        dialog.setTitle("Select Key");
        dialog.setLocationRelativeTo(this);
        dialog.setMinimumSize(new Dimension(RESOLUTION_WIDTH / 8, RESOLUTION_HEIGHT / 8));
        dialog.setVisible(true);

        return dialog;
    }

}
