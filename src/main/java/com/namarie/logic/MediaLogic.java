package com.namarie.logic;

import com.namarie.entity.Media;
import com.namarie.entity.Song;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.namarie.logic.SettingsLogic.*;

/**
 * This class control the logic of the application relative with media, like generate music list, video list, genders list, etc.
 *
 * @author Francisco Dueñas
 */
public class MediaLogic {

    public static final String ACTION_MEDIA = "%s%s%s";
    public static final String ACTION_LIST = "%s%s%s%s%s";
    public static final String ACTION_SONG = "%s%s%s%s%s%s%s";

    // Regex
    private static final String PATTERN = "(\\S+(\\.(?i)(mp3|mp4|wav|wma|mov|wmv|avi|flv|mkv|mpg|mpeg))$)";
    private static final String PATTERN_VIDEO = "(\\S+(\\.(?i)(mp4|mov|wmv|avi|flv|mkv|mpg))$)";
    private static final String PATTERN_AUDIO = "(\\S+(\\.(?i)(mp3|wav|wma|mpeg))$)";

    // Folders
    private static String videosPath;
    private static String songsPath;
    private static boolean promotionalVideoValidate;
    private static String promotionalVideoPath;

    // Time
    private static int randomSong;

    // Keys
    private static int upSong;
    private static int downSong;
    private static int upSongs;
    private static int downSongs;
    private static int upGender;
    private static int downGender;
    private static int addCoin;
    private static int removeCoin;
    private static int powerOff;
    private static int nextSong;

    private static final Pattern patternMedia;
    private static final Pattern patternVideo;
    private static final Pattern patternAudio;

    private static String[] genders;
    private static List<Song> musicList;

    private static int songCounter = 0;

    static {

        // Load data from JSON file
        MediaLogic.loadSettingsValues(SettingsLogic.loadSettings());

        // Regex to extensions
        patternMedia = Pattern.compile(PATTERN);
        patternVideo = Pattern.compile(PATTERN_VIDEO);
        patternAudio = Pattern.compile(PATTERN_AUDIO);

        // Music
        gendersList();
        musicList();

    }

    private MediaLogic() {
        throw new IllegalStateException("Utility class");
    }

    public static String getVideosPath() {
        return videosPath;
    }

    public static String getSongsPath() {
        return songsPath;
    }

    public static boolean isPromotionalVideoValidate() {
        return promotionalVideoValidate;
    }

    public static String getPromotionalVideoPath() {
        return promotionalVideoPath;
    }

    public static int getRandomSong() {
        return randomSong;
    }

    public static int getUpSong() {
        return upSong;
    }

    public static int getDownSong() {
        return downSong;
    }

    public static int getUpSongs() {
        return upSongs;
    }

    public static int getDownSongs() {
        return downSongs;
    }

    public static int getUpGender() {
        return upGender;
    }

    public static int getDownGender() {
        return downGender;
    }

    public static int getAddCoin() {
        return addCoin;
    }

    public static int getRemoveCoin() {
        return removeCoin;
    }

    public static int getPowerOff() {
        return powerOff;
    }

    public static int getNextSong() {
        return nextSong;
    }

    public static Pattern getPatternMedia() {
        return patternMedia;
    }

    public static Pattern getPatternVideo() {
        return patternVideo;
    }

    public static Pattern getPatternAudio() {
        return patternAudio;
    }

    public static List<Song> getMusicList() {
        return musicList;
    }

    public static String[] getGenders() {
        return genders;
    }

    /**
     * This method generates a genders list with directories name relative to path given.
     */
    public static void gendersList() {

        genders = null;

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) genders = new String[0];

        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format(ACTION_MEDIA, songsPath, File.separator, gender));

            if (!genderDirectory.isDirectory()) {
                List<String> gendersList = new ArrayList<>(Arrays.asList(genders));
                gendersList.remove(gender);
                genders = gendersList.toArray(new String[0]);
            }

        }

    }

    /**
     * This method generates a videos list with videos name relative to path given.
     *
     * @param videosPath Path where is videos.
     * @return the ArrayList<Media> with the videos name or null if this doesn't contain videos.
     */
    public static List<Media> getVideos(String videosPath) {

        String[] videos = null;
        int videoCounter = 0;
        List<Media> videoList = new ArrayList<>();

        File directory = new File(videosPath);

        if (directory.isDirectory()) videos = directory.list();

        if (videos == null) return Collections.emptyList();

        Arrays.sort(videos, String::compareToIgnoreCase);

        for (String video : videos) {

            File videoDirectory = new File(String.format(ACTION_MEDIA, videosPath, File.separator, video));

            if (videoDirectory.isFile()) {

                Matcher matcher = patternMedia.matcher(videoDirectory.getName());

                if (matcher.find()) {

                    videoList.add(new Media(videoCounter, video));
                    videoCounter++;

                }

            }

        }

        return videoList;

    }

    /**
     * This method load the values in a file (settings.json) to fields.
     *
     * @param values JSONObject with the values to load.
     */
    public static void loadSettingsValues(JSONObject values) {

        try {
            //Folders
            videosPath = (String) values.get(KEY_PATH_VIDEOS);
            songsPath = (String) values.get(KEY_PATH_SONGS);
            promotionalVideoValidate = (boolean) values.get(KEY_PROMOTIONAL_VIDEO);
            promotionalVideoPath = (String) values.get(KEY_PATH_PROMOTIONAL_VIDEO);

            // Time
            randomSong = (int) values.get(KEY_RANDOM_SONG);

            //Keys
            upSong = (int) values.get(KEY_UP_SONG);
            downSong = (int) values.get(KEY_DOWN_SONG);
            upSongs = (int) values.get(KEY_UP_SONGS);
            downSongs = (int) values.get(KEY_DOWN_SONGS);
            upGender = (int) values.get(KEY_UP_GENDER);
            downGender = (int) values.get(KEY_DOWN_GENDER);
            addCoin = (int) values.get(KEY_ADD_COIN);
            removeCoin = (int) values.get(KEY_REMOVE_COIN);
            powerOff = (int) values.get(KEY_POWER_OFF);
            nextSong = (int) values.get(KEY_NEXT_SONG);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void musicList() {

        String[] genders = null;
        String[] singers;

        musicList = new ArrayList<>();

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return;
        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format(ACTION_MEDIA, songsPath, File.separator, gender));

            if (genderDirectory.isDirectory()) {
                singers = genderDirectory.list();
                musicList = Stream.of(musicList, getSongsBySinger(singers, gender))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            }
        }

    }

    public static List<List<Song>> musicListByGenders(List<Song> musicList, String[] gendersList) {

        List<List<Song>> musicListByGenders = new ArrayList<>();

        if (gendersList == null) return Collections.emptyList();

        Arrays.sort(gendersList, String::compareToIgnoreCase);

        for (String gender : gendersList) {
            List<Song> musicListByGender = new ArrayList<>();
            for (Song song : musicList) {
                if (gender.equals(song.getGender())) {
                    musicListByGender.add(song);
                }

            }
            musicListByGenders.add(musicListByGender);
        }

        return musicListByGenders;
    }

    public static List<Song> getSongs(String[] songs, String singer, String gender) {

        List<Song> musicList = new ArrayList<>();

        if (songs == null) return Collections.emptyList();
        Arrays.sort(songs, String::compareToIgnoreCase);

        for (String song : songs) {

            File songFile = new File(String.format(ACTION_SONG, songsPath, File.separator, gender, File.separator, singer, File.separator, song));

            if (songFile.isFile()) {
                Matcher matcher = patternMedia.matcher(songFile.getName());
                if (matcher.find()) {
                    musicList.add(new Song(songCounter, song, singer, gender));
                    songCounter++;
                }
            }
        }
        return musicList;
    }

    public static List<Song> getSongsBySinger(String[] singers, String gender) {

        List<Song> musicList = new ArrayList<>();
        String[] songs;

        if (singers == null) return Collections.emptyList();
        Arrays.sort(singers, String::compareToIgnoreCase);

        for (String singer : singers) {

            File singerDirectory = new File(String.format(ACTION_LIST, songsPath, File.separator, gender, File.separator, singer));

            if (singerDirectory.isDirectory()) {
                songs = singerDirectory.list();
                musicList = Stream.of(musicList, getSongs(songs, singer, gender))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            }
        }
        return musicList;
    }

    public static void shutdown() throws IllegalArgumentException, IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
            shutdownCommand = "shutdown -h now";
        } else if (operatingSystem.contains("Windows")) {
            shutdownCommand = "shutdown.exe -s -t 0";
        } else {
            throw new IllegalArgumentException("Unsupported operating system.");
        }
        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }

}
