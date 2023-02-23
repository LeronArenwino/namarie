package com.namarie.logic;

import java.awt.*;
import java.util.Properties;

public class SettingsSingleton {

    private static final SettingsSingleton instance;

    private SettingsSingleton() {
    }

    // Screen resolution
    public static final GraphicsDevice RESOLUTION = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
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
    private static final String DEFAULT_VALUE_PROMOTIONAL_VIDEO = "false";
    private static final String DEFAULT_VALUE_PATH_PROMOTIONAL_VIDEO = "";

    //Time constants
    private static final String DEFAULT_VALUE_RANDOM_SONG = "1";
    private static final String DEFAULT_VALUE_REPEAT_SONGS = "5";

    //Credits constants
    private static final String DEFAULT_VALUE_AMOUNT_CREDITS = "1";
    private static final String DEFAULT_VALUE_LOCK_SCREEN = "false";
    private static final String DEFAULT_VALUE_SAVE_SONGS = "false";

    //Keys constants
    private static final String DEFAULT_VALUE_UP_SONG = "38";
    private static final String DEFAULT_VALUE_DOWN_SONG = "40";
    private static final String DEFAULT_VALUE_UP_SONGS = "106";
    private static final String DEFAULT_VALUE_DOWN_SONGS = "111";
    private static final String DEFAULT_VALUE_UP_GENDER = "107";
    private static final String DEFAULT_VALUE_DOWN_GENDER = "109";
    private static final String DEFAULT_VALUE_ADD_COIN = "112";
    private static final String DEFAULT_VALUE_REMOVE_COIN = "113";
    private static final String DEFAULT_VALUE_NEXT_SONG = "114";
    private static final String DEFAULT_VALUE_POWER_OFF = "123";

    //View constants
    private static final String DEFAULT_VALUE_BACKGROUND_COLOR = "#66CCFF";
    private static final String DEFAULT_VALUE_TEXT_COLOR = "#FFFFFF";
    private static final String DEFAULT_VALUE_FONT = "Arial";
    private static final String DEFAULT_VALUE_FONT_STYLE = "Regular";
    private static final String DEFAULT_VALUE_FONT_SIZE = "20";
    private static final String DEFAULT_VALUE_FOREGROUND = "#000000";
    private static final String DEFAULT_VALUE_BOLD = "false";

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

    // Keys control variables
    private static int valueToUpIndex;
    private static int valueToDownIndex;
    private static int valueToUpIndexes;
    private static int valueToDownIndexes;
    private static int valueToChangeGenderToUp;
    private static int valueToChangeGenderToDown;
    private static int valueToAddCoin;
    private static int valueToRemoveCoin;
    private static int valueToPlayNextSong;
    private static int valueToPowerOff;

    // View control variables
    private static String valueBackgroundColor;
    private static String valueTextColor;
    private static String valueFont;
    private static String valueFontStyle;
    private static int valueFontSize;
    private static String valueForeground;
    private static boolean valueBold;

    static {
        try {
            instance = new SettingsSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static SettingsSingleton getInstance(){
        return instance;
    }

    public static Properties defaultSettingsProperties(){

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

        // Keys section
        properties.put(KEY_UP_SONG, DEFAULT_VALUE_UP_SONG);
        properties.put(KEY_DOWN_SONG, DEFAULT_VALUE_DOWN_SONG);
        properties.put(KEY_UP_SONGS, DEFAULT_VALUE_UP_SONGS);
        properties.put(KEY_DOWN_SONGS, DEFAULT_VALUE_DOWN_SONGS);
        properties.put(KEY_UP_GENDER, DEFAULT_VALUE_UP_GENDER);
        properties.put(KEY_DOWN_GENDER, DEFAULT_VALUE_DOWN_GENDER);
        properties.put(KEY_ADD_COIN, DEFAULT_VALUE_ADD_COIN);
        properties.put(KEY_REMOVE_COIN, DEFAULT_VALUE_REMOVE_COIN);
        properties.put(KEY_NEXT_SONG, DEFAULT_VALUE_NEXT_SONG);
        properties.put(KEY_POWER_OFF, DEFAULT_VALUE_POWER_OFF);

        // View section
        properties.put(KEY_BACKGROUND_COLOR, DEFAULT_VALUE_BACKGROUND_COLOR);
        properties.put(KEY_TEXT_COLOR, DEFAULT_VALUE_TEXT_COLOR);
        properties.put(KEY_FONT, DEFAULT_VALUE_FONT);
        properties.put(KEY_FONT_STYLE, DEFAULT_VALUE_FONT_STYLE);
        properties.put(KEY_FONT_SIZE, DEFAULT_VALUE_FONT_SIZE);
        properties.put(KEY_FOREGROUND, DEFAULT_VALUE_FOREGROUND);
        properties.put(KEY_BOLD, DEFAULT_VALUE_BOLD);

        return properties;

    }

    public static void setSettingsFromProperties(Properties properties){

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

        // Set keys control variables
        setValueToUpIndex(Integer.parseInt(properties.getProperty(KEY_UP_SONG)));
        setValueToDownIndex(Integer.parseInt(properties.getProperty(KEY_DOWN_SONG)));
        setValueToUpIndexes(Integer.parseInt(properties.getProperty(KEY_UP_SONGS)));
        setValueToDownIndexes(Integer.parseInt(properties.getProperty(KEY_DOWN_SONGS)));
        setValueToChangeGenderToUp(Integer.parseInt(properties.getProperty(KEY_UP_GENDER)));
        setValueToChangeGenderToDown(Integer.parseInt(properties.getProperty(KEY_DOWN_GENDER)));
        setValueToAddCoin(Integer.parseInt(properties.getProperty(KEY_ADD_COIN)));
        setValueToRemoveCoin(Integer.parseInt(properties.getProperty(KEY_REMOVE_COIN)));
        setValueToPlayNextSong(Integer.parseInt(properties.getProperty(KEY_NEXT_SONG)));
        setValueToPowerOff(Integer.parseInt(properties.getProperty(KEY_POWER_OFF)));

        // Set view control variables
        setValueBackgroundColor(properties.getProperty(KEY_BACKGROUND_COLOR));
        setValueTextColor(properties.getProperty(KEY_TEXT_COLOR));
        setValueFont(properties.getProperty(KEY_FONT));
        setValueFontStyle(properties.getProperty(KEY_FONT_STYLE));
        setValueFontSize(Integer.parseInt(properties.getProperty(KEY_FONT_SIZE)));
        setValueForeground(properties.getProperty(KEY_FOREGROUND));
        setValueBold(Boolean.parseBoolean(properties.getProperty(KEY_BOLD)));

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

    public static int getValueToUpIndex() {
        return valueToUpIndex;
    }

    public static void setValueToUpIndex(int valueToUpIndex) {
        SettingsSingleton.valueToUpIndex = valueToUpIndex;
    }

    public static int getValueToDownIndex() {
        return valueToDownIndex;
    }

    public static void setValueToDownIndex(int valueToDownIndex) {
        SettingsSingleton.valueToDownIndex = valueToDownIndex;
    }

    public static int getValueToUpIndexes() {
        return valueToUpIndexes;
    }

    public static void setValueToUpIndexes(int valueToUpIndexes) {
        SettingsSingleton.valueToUpIndexes = valueToUpIndexes;
    }

    public static int getValueToDownIndexes() {
        return valueToDownIndexes;
    }

    public static void setValueToDownIndexes(int valueToDownIndexes) {
        SettingsSingleton.valueToDownIndexes = valueToDownIndexes;
    }

    public static int getValueToChangeGenderToUp() {
        return valueToChangeGenderToUp;
    }

    public static void setValueToChangeGenderToUp(int valueToChangeGenderToUp) {
        SettingsSingleton.valueToChangeGenderToUp = valueToChangeGenderToUp;
    }

    public static int getValueToChangeGenderToDown() {
        return valueToChangeGenderToDown;
    }

    public static void setValueToChangeGenderToDown(int valueToChangeGenderToDown) {
        SettingsSingleton.valueToChangeGenderToDown = valueToChangeGenderToDown;
    }

    public static int getValueToAddCoin() {
        return valueToAddCoin;
    }

    public static void setValueToAddCoin(int valueToAddCoin) {
        SettingsSingleton.valueToAddCoin = valueToAddCoin;
    }

    public static int getValueToRemoveCoin() {
        return valueToRemoveCoin;
    }

    public static void setValueToRemoveCoin(int valueToRemoveCoin) {
        SettingsSingleton.valueToRemoveCoin = valueToRemoveCoin;
    }

    public static int getValueToPlayNextSong() {
        return valueToPlayNextSong;
    }

    public static void setValueToPlayNextSong(int valueToPlayNextSong) {
        SettingsSingleton.valueToPlayNextSong = valueToPlayNextSong;
    }

    public static int getValueToPowerOff() {
        return valueToPowerOff;
    }

    public static void setValueToPowerOff(int valueToPowerOff) {
        SettingsSingleton.valueToPowerOff = valueToPowerOff;
    }

    public static String getValueBackgroundColor() {
        return valueBackgroundColor;
    }

    public static void setValueBackgroundColor(String valueBackgroundColor) {
        SettingsSingleton.valueBackgroundColor = valueBackgroundColor;
    }

    public static String getValueTextColor() {
        return valueTextColor;
    }

    public static void setValueTextColor(String valueTextColor) {
        SettingsSingleton.valueTextColor = valueTextColor;
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

    public static boolean isValueBold() {
        return valueBold;
    }

    public static void setValueBold(boolean valueBold) {
        SettingsSingleton.valueBold = valueBold;
    }

}
