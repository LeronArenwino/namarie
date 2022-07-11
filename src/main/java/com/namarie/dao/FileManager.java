package com.namarie.dao;

import com.namarie.logic.Logic;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.Map;

/** This class
 * @author Francisco Dueñas
 */
public class FileManager {

    private JSONObject settings;

    public JSONObject openFile(String path) {

        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {

            FileReader file = new FileReader(path);
            String settingsJsonStr;

            if (!new File(path).exists()) {
                Logic.saveDefault();
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

    public void saveFile(String path, Map<String, Object> values) {

        // Convert file object
        File file = new File(path);

        // Check if this file object exists and delete if exists
        if (file.exists()) {
            if (!file.delete())
                System.out.println("File delete failed!");
        }

        try {
            settings = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        values.forEach((key, value) -> {
            try {
                settings.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        try {
            FileWriter fileWriter = new FileWriter(path, true);
            settings.write(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

}