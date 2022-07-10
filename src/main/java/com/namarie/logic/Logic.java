package com.namarie.logic;

import com.namarie.dao.FileManager;

import java.util.HashMap;
import java.util.Map;

import static com.namarie.gui.MainWindow.*;

/** This class control the logic of the application relative with settings.
 * @author Francisco Dueñas
 */
public class Logic {

    public static final String PATH = "settings.json";

    // Folders constants
    private static final String DEFAULT_KEY_PATH_VIDEOS = "";
    private static final String DEFAULT_KEY_PATH_SONGS = "";
    private static final boolean DEFAULT_KEY_PROMOTIONAL_VIDEO = false;
    private static final String DEFAULT_KEY_PATH_PROMOTIONAL_VIDEO = "";

    private static final FileManager fileManager = new FileManager();

    /** This method save a file with the path and values given.
     * @param path Path where save the file
     * @param values Values to save in the file
     * @author Francisco Dueñas
     */
    public static void saveAs(String path, Map<String, Object> values){

        fileManager.saveFile(path, values);

    }

    /** This method save a file (settings.json) with the default values in the path relative to the execution.
     * @author Francisco Dueñas
     */
    public static void saveDefault(){

        // Values of default settings
        Map<String, Object> defaultSettings = new HashMap<>();

        //Folders
        defaultSettings.put(KEY_PATH_VIDEOS, DEFAULT_KEY_PATH_VIDEOS);
        defaultSettings.put(KEY_PATH_SONGS, DEFAULT_KEY_PATH_SONGS);
        defaultSettings.put(KEY_PROMOTIONAL_VIDEO, DEFAULT_KEY_PROMOTIONAL_VIDEO);
        defaultSettings.put(KEY_PATH_PROMOTIONAL_VIDEO, DEFAULT_KEY_PATH_PROMOTIONAL_VIDEO);

        //Time
        defaultSettings.put(KEY_RANDOM_SONG, 1);
        defaultSettings.put(KEY_REPEAT_SONGS, 5);

        //Credits
        defaultSettings.put(KEY_AMOUNT_CREDITS, 1);
        defaultSettings.put(KEY_LOCK_SCREEN, false);
        defaultSettings.put(KEY_SAVE_SONGS, false);

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
        defaultSettings.put(KEY_FONT, "Arial");
        defaultSettings.put(KEY_FONT_STYLE, "Regular");
        defaultSettings.put(KEY_FONT_SIZE, 20);
        defaultSettings.put(KEY_FOREGROUND, "000,000,000");
        defaultSettings.put(KEY_BOLD, true);

        fileManager.saveFile(PATH, defaultSettings);

    }

}
