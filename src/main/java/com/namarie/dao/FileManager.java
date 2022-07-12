package com.namarie.dao;

import com.namarie.logic.SettingsLogic;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
import java.util.Map;

/** This class
 * @author Francisco Dueñas
 */
public class FileManager {

    // Create a Logger
    private final Logger logger
            = Logger.getLogger(
            FileManager.class.getName());
    private JSONObject settings;

    public JSONObject openFile(String path) {

        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {

            FileReader file = new FileReader(path);
            String settingsJsonStr;

            if (!new File(path).exists()) {
                SettingsLogic.saveDefault();
            }
            reader = new BufferedReader(file);

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            settingsJsonStr = buffer.toString();

            settings = new JSONObject(settingsJsonStr);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Reader close error!");
                }
            }
        }
        return settings;
    }

    public void saveFile(String path, Map<String, Object> values) {

        // Convert file object
        File file = new File(path);

        // Check if this file object exists and delete if exists
        if (file.exists() && file.delete()) {
            logger.log(Level.WARNING, "File delete failed!");
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