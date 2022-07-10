package com.namarie.logic;

import com.namarie.dao.FileManager;

import java.util.HashMap;
import java.util.Map;

import static com.namarie.gui.MainWindow.*;

/**
 * This class control the logic of the application relative with settings.
 *
 * @author Francisco Dueñas
 */
public class Logic {

    public static final String PATH = "settings.json";

    // Folders constants
    private static final String DEFAULT_VALUE_PATH_VIDEOS = "";
    private static final String DEFAULT_VALUE_PATH_SONGS = "";
    private static final boolean DEFAULT_VALUE_PROMOTIONAL_VIDEO = false;
    private static final String DEFAULT_VALUE_PATH_PROMOTIONAL_VIDEO = "";

    //Time constants
    private static final int DEFAULT_VALUE_RANDOM_SONG = 1;
    private static final int DEFAULT_VALUE_REPEAT_SONGS = 5;

    //Credits constants
    private static final int DEFAULT_VALUE_AMOUNT_CREDITS = 1;
    private static final boolean DEFAULT_VALUE_LOCK_SCREEN = false;
    private static final boolean DEFAULT_VALUE_SAVE_SONGS = false;

    //Keys constants
    private static final int DEFAULT_VALUE_UP_SONG = 38;
    private static final int DEFAULT_VALUE_DOWN_SONG = 40;
    private static final int DEFAULT_VALUE_UP_SONGS = 106;
    private static final int DEFAULT_VALUE_DOWN_SONGS = 111;
    private static final int DEFAULT_VALUE_UP_GENDER = 107;
    private static final int DEFAULT_VALUE_DOWN_GENDER = 109;
    private static final int DEFAULT_VALUE_ADD_COIN = 65;
    private static final int DEFAULT_VALUE_REMOVE_COIN = 66;
    private static final int DEFAULT_VALUE_NEXT_SONG = 78;
    private static final int DEFAULT_VALUE_POWER_OFF = 70;
    private static final int DEFAULT_VALUE_SETTINGS = 67;

    //View constants
    private static final String DEFAULT_VALUE_BACKGROUND_COLOR = "102,204,255";
    private static final String DEFAULT_VALUE_TEXT_COLOR = "255,255,255";
    private static final String DEFAULT_VALUE_FONT = "Arial";
    private static final String DEFAULT_VALUE_FONT_STYLE = "Regular";
    private static final int DEFAULT_VALUE_FONT_SIZE = 20;
    private static final String DEFAULT_VALUE_FOREGROUND = "000,000,000";
    private static final boolean DEFAULT_VALUE_BOLD = false;

    private static final FileManager fileManager = new FileManager();

    /**
     * This method save a file with the path and values given.
     *
     * @param path   Path where save the file
     * @param values Values to save in the file
     * @author Francisco Dueñas
     */
    public static void saveAs(String path, Map<String, Object> values) {

        fileManager.saveFile(path, values);

    }

    /**
     * This method save a file (settings.json) with the default values in the path relative to the execution.
     *
     * @author Francisco Dueñas
     */
    public static void saveDefault() {

        // Values of default settings
        Map<String, Object> defaultSettings = new HashMap<>();

        //Folders
        defaultSettings.put(KEY_PATH_VIDEOS, DEFAULT_VALUE_PATH_VIDEOS);
        defaultSettings.put(KEY_PATH_SONGS, DEFAULT_VALUE_PATH_SONGS);
        defaultSettings.put(KEY_PROMOTIONAL_VIDEO, DEFAULT_VALUE_PROMOTIONAL_VIDEO);
        defaultSettings.put(KEY_PATH_PROMOTIONAL_VIDEO, DEFAULT_VALUE_PATH_PROMOTIONAL_VIDEO);

        //Time
        defaultSettings.put(KEY_RANDOM_SONG, DEFAULT_VALUE_RANDOM_SONG);
        defaultSettings.put(KEY_REPEAT_SONGS, DEFAULT_VALUE_REPEAT_SONGS);

        //Credits
        defaultSettings.put(KEY_AMOUNT_CREDITS, DEFAULT_VALUE_AMOUNT_CREDITS);
        defaultSettings.put(KEY_LOCK_SCREEN, DEFAULT_VALUE_LOCK_SCREEN);
        defaultSettings.put(KEY_SAVE_SONGS, DEFAULT_VALUE_SAVE_SONGS);

        //Keys
        defaultSettings.put(KEY_UP_SONG, DEFAULT_VALUE_UP_SONG);
        defaultSettings.put(KEY_DOWN_SONG, DEFAULT_VALUE_DOWN_SONG);
        defaultSettings.put(KEY_UP_SONGS, DEFAULT_VALUE_UP_SONGS);
        defaultSettings.put(KEY_DOWN_SONGS, DEFAULT_VALUE_DOWN_SONGS);
        defaultSettings.put(KEY_UP_GENDER, DEFAULT_VALUE_UP_GENDER);
        defaultSettings.put(KEY_DOWN_GENDER, DEFAULT_VALUE_DOWN_GENDER);
        defaultSettings.put(KEY_ADD_COIN, DEFAULT_VALUE_ADD_COIN);
        defaultSettings.put(KEY_REMOVE_COIN, DEFAULT_VALUE_REMOVE_COIN);
        defaultSettings.put(KEY_NEXT_SONG, DEFAULT_VALUE_NEXT_SONG);
        defaultSettings.put(KEY_POWER_OFF, DEFAULT_VALUE_POWER_OFF);
        defaultSettings.put(KEY_SETTINGS, DEFAULT_VALUE_SETTINGS);

        //View
        defaultSettings.put(KEY_BACKGROUND_COLOR, DEFAULT_VALUE_BACKGROUND_COLOR);
        defaultSettings.put(KEY_TEXT_COLOR, DEFAULT_VALUE_TEXT_COLOR);
        defaultSettings.put(KEY_FONT, DEFAULT_VALUE_FONT);
        defaultSettings.put(KEY_FONT_STYLE, DEFAULT_VALUE_FONT_STYLE);
        defaultSettings.put(KEY_FONT_SIZE, DEFAULT_VALUE_FONT_SIZE);
        defaultSettings.put(KEY_FOREGROUND, DEFAULT_VALUE_FOREGROUND);
        defaultSettings.put(KEY_BOLD, DEFAULT_VALUE_BOLD);

        fileManager.saveFile(PATH, defaultSettings);

    }

}
