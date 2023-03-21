package com.namarie.logic;

import java.awt.*;
import java.util.Properties;

public class SettingsSingleton {

    private SettingsSingleton() {
    }

    // Relevant messages
    public static final String ADVERTISEMENT_MESSAGE = "Error media-player!";
    public static final String NAMARIE_TITLE = "Namarie jukebox";

    // General graphics environment
    public static final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    // Screen resolution
    public static final GraphicsDevice RESOLUTION = graphicsEnvironment.getDefaultScreenDevice();
    public static final int RESOLUTION_WIDTH = RESOLUTION.getDisplayMode().getWidth();
    public static final int RESOLUTION_HEIGHT = RESOLUTION.getDisplayMode().getHeight();

    // Path to default settings file
    public static final String PATH_TO_SETTINGS = "settings";

    // Folders TabPanel
    public static final String KEY_PATH_TO_VIDEOS = "pathToVideos";
    public static final String KEY_PATH_TO_SONGS = "pathToSongs";
    public static final String KEY_IS_PROMOTIONAL_VIDEOS = "isPromotionalVideos";
    public static final String KEY_PATH_TO_PROMOTIONAL_VIDEOS = "pathToPromotionalVideos";

    // Time TabPanel
    public static final String KEY_TIME_TO_PLAY_RANDOM_SONGS = "timeToPlayRandomSongs";
    public static final String KEY_TIME_TO_REPEAT_SONGS = "timeToRepeatSongs";

    // Credits TabPanel
    public static final String KEY_AMOUNT_CREDITS = "amountCredits";
    public static final String KEY_LOCK_SCREEN = "lockScreen";
    public static final String KEY_SAVE_SONGS = "saveSongs";

    // View TabPanel
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";
    public static final String KEY_FOREGROUND = "foreground";
    public static final String KEY_FONT = "font";
    public static final String KEY_FONT_STYLE = "fontStyle";
    public static final String KEY_FONT_SIZE = "fontSize";

    // Song list view TabPanel
    public static final String KEY_SONG_LIST_BACKGROUND_COLOR = "songListBackgroundColor";
    public static final String KEY_SONG_LIST_FOREGROUND = "songListForeground";
    public static final String KEY_SONG_LIST_FONT = "songListFont";
    public static final String KEY_SONG_LIST_FONT_SIZE = "songListFontSize";

    // Folders constants
    private static final String DEFAULT_VALUE_PATH_VIDEOS = "";
    private static final String DEFAULT_VALUE_PATH_SONGS = "";
    private static final String DEFAULT_VALUE_PROMOTIONAL_VIDEO = "false";
    private static final String DEFAULT_VALUE_PATH_PROMOTIONAL_VIDEO = "";

    //Time constants
    private static final String DEFAULT_VALUE_RANDOM_SONG = "1";
    private static final String DEFAULT_VALUE_REPEAT_SONGS = "5";

    //Credits constants
    private static final String DEFAULT_VALUE_AMOUNT_CREDITS = "1";
    private static final String DEFAULT_VALUE_LOCK_SCREEN = "false";
    private static final String DEFAULT_VALUE_SAVE_SONGS = "false";

    //View constants
    private static final String DEFAULT_VALUE_BACKGROUND_COLOR = "#66CCFF";
    private static final String DEFAULT_VALUE_FOREGROUND = "#FFFFFF";
    private static final String DEFAULT_VALUE_FONT = "Arial";
    private static final String DEFAULT_VALUE_FONT_STYLE = "Regular";
    private static final String DEFAULT_VALUE_FONT_SIZE = "20";

    // Paths control variables
    private static String pathToVideos;
    private static String pathToSongs;
    private static boolean promotionalVideos;
    private static String pathToPromotionalVideos;

    // Time control variables
    private static int timeToPlayRandomSongs;
    private static int timeToRepeatSongs;

    // Credits control variables
    private static int amountCredits;
    private static boolean lockScreen;
    private static boolean saveSongs;

    // View control variables
    private static String valueBackgroundColor;
    private static String valueForeground;
    private static String valueFont;
    private static String valueFontStyle;
    private static int valueFontSize;

    // Song list control variables
    private static String songListValueBackgroundColor;
    private static String songListValueForeground;
    private static String songListValueFont;
    private static int songListValueFontSize;

    public static Properties defaultSettingsProperties() {

        Properties properties = new Properties();

        // Paths section
        properties.put(KEY_PATH_TO_VIDEOS, DEFAULT_VALUE_PATH_VIDEOS);
        properties.put(KEY_PATH_TO_SONGS, DEFAULT_VALUE_PATH_SONGS);
        properties.put(KEY_IS_PROMOTIONAL_VIDEOS, DEFAULT_VALUE_PROMOTIONAL_VIDEO);
        properties.put(KEY_PATH_TO_PROMOTIONAL_VIDEOS, DEFAULT_VALUE_PATH_PROMOTIONAL_VIDEO);

        // Time section
        properties.put(KEY_TIME_TO_PLAY_RANDOM_SONGS, DEFAULT_VALUE_RANDOM_SONG);
        properties.put(KEY_TIME_TO_REPEAT_SONGS, DEFAULT_VALUE_REPEAT_SONGS);

        // Credits section
        properties.put(KEY_AMOUNT_CREDITS, DEFAULT_VALUE_AMOUNT_CREDITS);
        properties.put(KEY_LOCK_SCREEN, DEFAULT_VALUE_LOCK_SCREEN);
        properties.put(KEY_SAVE_SONGS, DEFAULT_VALUE_SAVE_SONGS);

        // View section
        properties.put(KEY_BACKGROUND_COLOR, DEFAULT_VALUE_BACKGROUND_COLOR);
        properties.put(KEY_FOREGROUND, DEFAULT_VALUE_FOREGROUND);
        properties.put(KEY_FONT, DEFAULT_VALUE_FONT);
        properties.put(KEY_FONT_STYLE, DEFAULT_VALUE_FONT_STYLE);
        properties.put(KEY_FONT_SIZE, DEFAULT_VALUE_FONT_SIZE);

        // Song list view section
        properties.put(KEY_SONG_LIST_BACKGROUND_COLOR, DEFAULT_VALUE_BACKGROUND_COLOR);
        properties.put(KEY_SONG_LIST_FOREGROUND, DEFAULT_VALUE_FOREGROUND);
        properties.put(KEY_SONG_LIST_FONT, DEFAULT_VALUE_FONT);
        properties.put(KEY_SONG_LIST_FONT_SIZE, DEFAULT_VALUE_FONT_SIZE);

        return properties;

    }

    public static void setSettingsFromProperties(Properties properties) {

        // Set paths control variables
        setPathToVideos(properties.getProperty(KEY_PATH_TO_VIDEOS));
        setPathToSongs(properties.getProperty(KEY_PATH_TO_SONGS));
        setPromotionalVideos(Boolean.parseBoolean(properties.getProperty(KEY_IS_PROMOTIONAL_VIDEOS)));
        setPathToPromotionalVideos(properties.getProperty(KEY_PATH_TO_PROMOTIONAL_VIDEOS));

        // Set time control variables
        setTimeToPlayRandomSongs(Integer.parseInt(properties.getProperty(KEY_TIME_TO_PLAY_RANDOM_SONGS)));
        setTimeToRepeatSongs(Integer.parseInt(properties.getProperty(KEY_TIME_TO_REPEAT_SONGS)));

        // Set credits control variables
        setAmountCredits(Integer.parseInt(properties.getProperty(KEY_AMOUNT_CREDITS)));
        setLockScreen(Boolean.parseBoolean(properties.getProperty(KEY_LOCK_SCREEN)));
        setSaveSongs(Boolean.parseBoolean(properties.getProperty(KEY_SAVE_SONGS)));

        // Set view control variables
        setValueBackgroundColor(properties.getProperty(KEY_BACKGROUND_COLOR));
        setValueForeground(properties.getProperty(KEY_FOREGROUND));
        setValueFont(properties.getProperty(KEY_FONT));
        setValueFontStyle(properties.getProperty(KEY_FONT_STYLE));
        setValueFontSize(Integer.parseInt(properties.getProperty(KEY_FONT_SIZE)));

        // Set view control variables
        setSongListValueBackgroundColor(properties.getProperty(KEY_SONG_LIST_BACKGROUND_COLOR));
        setSongListValueForeground(properties.getProperty(KEY_SONG_LIST_FOREGROUND));
        setSongListValueFont(properties.getProperty(KEY_SONG_LIST_FONT));
        setSongListValueFontSize(Integer.parseInt(properties.getProperty(KEY_SONG_LIST_FONT_SIZE)));

    }

    public static String getPathToVideos() {
        return pathToVideos;
    }

    public static void setPathToVideos(String pathToVideos) {
        SettingsSingleton.pathToVideos = pathToVideos;
    }

    public static String getPathToSongs() {
        return pathToSongs;
    }

    public static void setPathToSongs(String pathToSongs) {
        SettingsSingleton.pathToSongs = pathToSongs;
    }

    public static boolean isPromotionalVideos() {
        return promotionalVideos;
    }

    public static void setPromotionalVideos(boolean promotionalVideos) {
        SettingsSingleton.promotionalVideos = promotionalVideos;
    }

    public static String getPathToPromotionalVideos() {
        return pathToPromotionalVideos;
    }

    public static void setPathToPromotionalVideos(String pathToPromotionalVideos) {
        SettingsSingleton.pathToPromotionalVideos = pathToPromotionalVideos;
    }

    public static int getTimeToPlayRandomSongs() {
        return timeToPlayRandomSongs;
    }

    public static void setTimeToPlayRandomSongs(int timeToPlayRandomSongs) {
        SettingsSingleton.timeToPlayRandomSongs = timeToPlayRandomSongs;
    }

    public static int getTimeToRepeatSongs() {
        return timeToRepeatSongs;
    }

    public static void setTimeToRepeatSongs(int timeToRepeatSongs) {
        SettingsSingleton.timeToRepeatSongs = timeToRepeatSongs;
    }

    public static int getAmountCredits() {
        return amountCredits;
    }

    public static void setAmountCredits(int amountCredits) {
        SettingsSingleton.amountCredits = amountCredits;
    }

    public static boolean isLockScreen() {
        return lockScreen;
    }

    public static void setLockScreen(boolean lockScreen) {
        SettingsSingleton.lockScreen = lockScreen;
    }

    public static boolean isSaveSongs() {
        return saveSongs;
    }

    public static void setSaveSongs(boolean saveSongs) {
        SettingsSingleton.saveSongs = saveSongs;
    }

    public static String getValueBackgroundColor() {
        return valueBackgroundColor;
    }

    public static void setValueBackgroundColor(String valueBackgroundColor) {
        SettingsSingleton.valueBackgroundColor = valueBackgroundColor;
    }

    public static String getValueFont() {
        return valueFont;
    }

    public static void setValueFont(String valueFont) {
        SettingsSingleton.valueFont = valueFont;
    }

    public static String getValueFontStyle() {
        return valueFontStyle;
    }

    public static void setValueFontStyle(String valueFontStyle) {
        SettingsSingleton.valueFontStyle = valueFontStyle;
    }

    public static int getValueFontSize() {
        return valueFontSize;
    }

    public static void setValueFontSize(int valueFontSize) {
        SettingsSingleton.valueFontSize = valueFontSize;
    }

    public static String getValueForeground() {
        return valueForeground;
    }

    public static void setValueForeground(String valueForeground) {
        SettingsSingleton.valueForeground = valueForeground;
    }

    public static String getSongListValueBackgroundColor() {
        return songListValueBackgroundColor;
    }

    public static void setSongListValueBackgroundColor(String songListValueBackgroundColor) {
        SettingsSingleton.songListValueBackgroundColor = songListValueBackgroundColor;
    }

    public static String getSongListValueForeground() {
        return songListValueForeground;
    }

    public static void setSongListValueForeground(String songListValueForeground) {
        SettingsSingleton.songListValueForeground = songListValueForeground;
    }

    public static String getSongListValueFont() {
        return songListValueFont;
    }

    public static void setSongListValueFont(String songListValueFont) {
        SettingsSingleton.songListValueFont = songListValueFont;
    }

    public static int getSongListValueFontSize() {
        return songListValueFontSize;
    }

    public static void setSongListValueFontSize(int songListValueFontSize) {
        SettingsSingleton.songListValueFontSize = songListValueFontSize;
    }

}
