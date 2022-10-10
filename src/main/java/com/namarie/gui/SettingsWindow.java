package com.namarie.gui;

import com.namarie.logic.SettingsLogic;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.namarie.logic.SettingsLogic.*;

public class SettingsWindow extends JFrame {

    private JPanel containerPanel;
    private JPanel centerPanel;
    private JTabbedPane containerTabbedPane;
    private JPanel welcomePanel;
    private JPanel foldersPanel;
    private JPanel timePanel;
    private JPanel creditsPanel;
    private JPanel keysPanel;
    private JPanel viewPanel;
    private JLabel tittleWelcomePanel;
    private JLabel tittleFoldersPanel;
    private JLabel tittleTimePanel;
    private JLabel tittleCreditsPanel;
    private JLabel tittleKeysPanel;
    private JLabel tittleViewPanel;
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
    private JPanel containerViewPanel;
    private JTextPane textViewPanel;
    private JButton color1SearchButton;
    private JButton color2SearchButton;
    private JComboBox<String> fontComboBox;
    private JButton foregroundSearchButton;
    private JComboBox<Integer> fontSizeComboBox;
    private JCheckBox boldCheckBox;
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
    private JLabel color1Label;
    private JLabel color1ViewLabel;
    private JLabel fontLabel;
    private JLabel color2Label;
    private JLabel color2ViewLabel;
    private JLabel foregroundLabel;
    private JLabel fontSizeLabel;
    private JLabel clickLabel;
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

    public SettingsWindow() {

        initComponents();

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

        saveButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogTitle("Save Settings");
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setSelectedFile(new File(SettingsLogic.PATH));
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    SettingsLogic.save(settingsValues());
                    loadSettings(SettingsLogic.loadSettings());
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
                    SettingsLogic.saveDefault();
                    loadSettings(SettingsLogic.loadSettings());
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

        this.setTitle("Settings");
        this.setMinimumSize(new Dimension(RESOLUTION_WIDTH / 2, RESOLUTION_HEIGHT / 2));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.getContentPane().add(containerPanel);
        this.setVisible(false);

        loadSettings(SettingsLogic.loadSettings());

    }

    private void loadSettings(JSONObject values) {

        try {
            //Folders
            videoTextField.setText((String) values.get(KEY_PATH_VIDEOS));
            musicTextField.setText((String) values.get(KEY_PATH_SONGS));
            promotionalVideoCheckBox.setSelected((boolean) values.get(KEY_PROMOTIONAL_VIDEO));
            promotionalTextField.setText((String) values.get(KEY_PATH_PROMOTIONAL_VIDEO));
            promotionalTextField.setEnabled(promotionalVideoCheckBox.isSelected());
            promotionalSearchButton.setEnabled(promotionalVideoCheckBox.isSelected());

            //Time
            timeRandomSongComboBox.setSelectedIndex((int) values.get(KEY_RANDOM_SONG));
            timeLimitComboBox.setSelectedIndex((int) values.get(KEY_REPEAT_SONGS));

            //Credits
            creditsComboBox.setSelectedIndex((int) values.get(KEY_AMOUNT_CREDITS));
            blockScreenWhenHavenCheckBox.setSelected((boolean) values.get(KEY_LOCK_SCREEN));
            saveSongsWhenPowerCheckBox.setSelected((boolean) values.get(KEY_SAVE_SONGS));

            //Keys
            upSongSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_UP_SONG)));
            upSongButton.setText(Integer.toString((int) values.get(KEY_UP_SONG)));
            downSongSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_DOWN_SONG)));
            downSongButton.setText(Integer.toString((int) values.get(KEY_DOWN_SONG)));
            upSongsSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_UP_SONGS)));
            upSongsButton.setText(Integer.toString((int) values.get(KEY_UP_SONGS)));
            downSongsSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_DOWN_SONGS)));
            downSongsButton.setText(Integer.toString((int) values.get(KEY_DOWN_SONGS)));
            upGenderSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_UP_GENDER)));
            upGenderButton.setText(Integer.toString((int) values.get(KEY_UP_GENDER)));
            downGenderSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_DOWN_GENDER)));
            downGenderButton.setText(Integer.toString((int) values.get(KEY_DOWN_GENDER)));
            addCoinSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_ADD_COIN)));
            addCoinButton.setText(Integer.toString((int) values.get(KEY_ADD_COIN)));
            removeCoinSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_REMOVE_COIN)));
            removeCoinButton.setText(Integer.toString((int) values.get(KEY_REMOVE_COIN)));
            powerOffSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_POWER_OFF)));
            powerOffButton.setText(Integer.toString((int) values.get(KEY_POWER_OFF)));
            nextSongSymbolLabel.setText(KeyEvent.getKeyText((int) values.get(KEY_NEXT_SONG)));
            nextSongButton.setText(Integer.toString((int) values.get(KEY_NEXT_SONG)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Map<String, Object> settingsValues() {

        Map<String, Object> values = new HashMap<>();

        //Folders
        values.put(KEY_PATH_VIDEOS, videoTextField.getText());
        values.put(KEY_PATH_SONGS, musicTextField.getText());
        values.put(KEY_PROMOTIONAL_VIDEO, promotionalVideoCheckBox.isSelected());
        values.put(KEY_PATH_PROMOTIONAL_VIDEO, promotionalTextField.getText());

        //Time
        values.put(KEY_RANDOM_SONG, timeRandomSongComboBox.getSelectedIndex());
        values.put(KEY_REPEAT_SONGS, timeLimitComboBox.getSelectedIndex());

        //Credits
        values.put(KEY_AMOUNT_CREDITS, creditsComboBox.getSelectedIndex());
        values.put(KEY_LOCK_SCREEN, blockScreenWhenHavenCheckBox.isSelected());
        values.put(KEY_SAVE_SONGS, saveSongsWhenPowerCheckBox.isSelected());

        //Keys
        values.put(KEY_UP_SONG, Integer.parseInt(upSongButton.getText()));
        values.put(KEY_DOWN_SONG, Integer.parseInt(downSongButton.getText()));
        values.put(KEY_UP_SONGS, Integer.parseInt(upSongsButton.getText()));
        values.put(KEY_DOWN_SONGS, Integer.parseInt(downSongsButton.getText()));
        values.put(KEY_UP_GENDER, Integer.parseInt(upGenderButton.getText()));
        values.put(KEY_DOWN_GENDER, Integer.parseInt(downGenderButton.getText()));
        values.put(KEY_ADD_COIN, Integer.parseInt(addCoinButton.getText()));
        values.put(KEY_REMOVE_COIN, Integer.parseInt(removeCoinButton.getText()));
        values.put(KEY_POWER_OFF, Integer.parseInt(powerOffButton.getText()));
        values.put(KEY_NEXT_SONG, Integer.parseInt(nextSongButton.getText()));

        return values;

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
