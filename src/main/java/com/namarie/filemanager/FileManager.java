package com.namarie.filemanager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.namarie.gui.MainWindow.*;

public class FileManager {

    private JSONObject settings;

    public JSONObject openFile(String path) {

        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {

            FileReader file = new FileReader(path);
            String settingsJsonStr;

            if (!new File(path).exists()) {
                saveDefaultSettings();
            }
            reader = new BufferedReader(file);

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Configuration file is empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            settingsJsonStr = buffer.toString();

            settings = new JSONObject(settingsJsonStr);
        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.toString(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                            null,
                            e.toString(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return settings;
    }

    public void saveFile(String nameFile, Map<String, Object> values) {

        File file = new File(new File("") + nameFile);
        if (file.exists()) {
            if (file.delete()) {
                System.out.print("");
            }
        }
        try {
            settings = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        values.forEach((k, v) -> {
            try {
                settings.put(k, v);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        try {
            FileWriter fileWriter = new FileWriter(new File("") + nameFile, true);
            settings.write(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDefaultSettings() {

        Map<String, Object> defaultSettings = new HashMap<>();

        //Folders
        defaultSettings.put(KEY_PATH_VIDEOS, "");
        defaultSettings.put(KEY_PATH_SONGS, "");
        defaultSettings.put(KEY_PROMOTIONAL_VIDEO, false);
        defaultSettings.put(KEY_PATH_PROMOTIONAL_VIDEO, "");

        //Time
        defaultSettings.put(KEY_RANDOM_SONG, 1);
        defaultSettings.put(KEY_REPEAT_SONGS, 5);

        //Credits
        defaultSettings.put(KEY_AMOUNT_CREDITS, 1);
        defaultSettings.put(KEY_LOCK_SCREEN, true);
        defaultSettings.put(KEY_SAVE_SONGS, true);

        //Keys
        defaultSettings.put(KEY_UP_SONG, 38);
        defaultSettings.put(KEY_DOWN_SONG, 40);
        defaultSettings.put(KEY_UP_SONGS, 106);
        defaultSettings.put(KEY_DOWN_SONGS, 111);
        defaultSettings.put(KEY_UP_GENDER, 107);
        defaultSettings.put(KEY_DOWN_GENDER, 109);
        defaultSettings.put(KEY_ADD_COIN, 65);
        defaultSettings.put(KEY_REMOVE_COIN, 66);
        defaultSettings.put(KEY_NEXT_SONG, 78);
        defaultSettings.put(KEY_POWER_OFF, 70);
        defaultSettings.put(KEY_SETTINGS, 67);

        //View
        defaultSettings.put(KEY_COLOR1, "102,204,255");
        defaultSettings.put(KEY_COLOR2, "255,255,255");
        defaultSettings.put(KEY_FONT, "Consolas");
        defaultSettings.put(KEY_FOREGROUND, "000,000,000");
        defaultSettings.put(KEY_FONT_SIZE, 20);
        defaultSettings.put(KEY_BOLD, true);

        saveFile("config.json", defaultSettings);

    }
}
