package com.namarie.gui;

import com.namarie.dao.FileManager;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.namarie.gui.MainWindow.*;

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
    private JButton renameMusicFilesButton;
    private JButton saveButton;
    private JButton defaultButton;
    private JPanel containerTimePanel;
    private JCheckBox promotionalVideoCheckBox;
    private JFormattedTextField promotionalTextField;
    private JButton promotionalSearchButton;
    private JTextPane textTimePanel;
    private JComboBox timeRandomSongComboBox;
    private JComboBox timeLimitComboBox;
    private JTextPane textCreditsPanel;
    private JComboBox creditsComboBox;
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
    private JComboBox fontComboBox;
    private JButton foregroundSearchButton;
    private JComboBox fontSizeComboBox;
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
    private JButton searchFileButton;
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
    private JComboBox fontStyleComboBox;

    private JSONObject loadedSettings;

    private FileManager fileManager = new FileManager();

    public SettingsWindow() {

        this.setTitle("Settings");
        this.setMinimumSize(new Dimension(640, 360));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout(0, 0));

        getContentPane().add(containerPanel);
        this.setVisible(true);

        File file = new File(new File("") + "config.json");
        if (!file.exists())
            fileManager.saveDefaultSettings();

        loadedSettings = fileManager.openFile(new File("") + "config.json");
        loadSettings(loadedSettings);

        searchFileButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open file");

                String path;

                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    // This returns the path from the selected file
                    path = fileChooser.getSelectedFile().getAbsolutePath();


                    loadedSettings = fileManager.openFile(path);
                    loadSettings(loadedSettings);

                }
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

                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                    //This returns the nameFile
                    String nameFile = fileChooser.getSelectedFile().getName();

                    fileManager.saveFile(nameFile, settingsValues());
                    loadedSettings = fileManager.openFile(new File("") + nameFile);
                    loadSettings(loadedSettings);
                    robotMove();
                    dispose();
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
                int res = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if (res == 0) {
                    fileManager.saveDefaultSettings();
                    loadedSettings = fileManager.openFile("config.json");
                    loadSettings(loadedSettings);
                    robotMove();
                    dispose();
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

            //View
//            color1ViewLabel.setBackground((Color)values.get(KEY_COLOR1));

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
        dialog.setMinimumSize(new Dimension((int) Logic.RESOLUTION.getWidth() / 8, (int) Logic.RESOLUTION.getHeight() / 8));
        dialog.setVisible(true);

        return dialog;
    }

}
