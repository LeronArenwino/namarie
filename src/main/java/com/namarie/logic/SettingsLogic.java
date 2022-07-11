package com.namarie.logic;

import com.namarie.dao.FileManager;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class control the logic of the application relative with settings.
 *
 * @author Francisco Dueñas.
 */
public class SettingsLogic {

    public static final String PATH = "settings.json";
    public static final Dimension RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();

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

    private SettingsLogic() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This method load a file (settings.json) with the values in the path relative to the execution.
     *
     * @return A JSONObject with the settings.
     */
    public static JSONObject loadSettings() {

        File file = new File(PATH);
        if (!file.exists())
            SettingsLogic.saveDefault();

        return fileManager.openFile(PATH);

    }

    /**
     * This method save a file (settings.json) with the values given in the path relative to the execution.
     *
     * @param values Values to save in the file.
     */
    public static void save(Map<String, Object> values) {

        fileManager.saveFile(PATH, values);

    }

    /**
     * This method save a file (settings.json) with the default values in the path relative to the execution.
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
