package com.namarie.logic;

import com.namarie.entity.Media;
import com.namarie.entity.Song;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.namarie.logic.SettingsLogic.*;

/**
 * This class control the logic of the application relative with media, like generate music list, video list, genders list, etc.
 *
 * @author Francisco Dueñas
 */
public class MediaLogic {

    // Regex
    private static final String PATTERN = "(\\S+(\\.(?i)(mp3|mp4|wav|wma|mov|wmv|avi|flv|mkv|mpg|mpeg))$)";
    private static final String PATTERN_VIDEO = "(\\S+(\\.(?i)(mp4|mov|wmv|avi|flv|mkv|mpg))$)";
    private static final String PATTERN_AUDIO = "(\\S+(\\.(?i)(mp3|wav|wma|mpeg))$)";

    // Folders
    public static String videosPath;
    public static String songsPath;
    public static boolean promotionalVideoValidate;
    public static String promotionalVideoPath;

    // Time TODO
    public static int randomSong;
//    private static int repeatSong;

    // Credits TODO

    // Keys
    public static int upSong;
    public static int downSong;
    public static int upSongs;
    public static int downSongs;
    public static int upGender;
    public static int downGender;
    public static int addCoin;
    public static int removeCoin;
    public static int powerOff;
    public static int nextSong;

    public static Pattern patternMedia;
    public static Pattern patternVideo;
    public static Pattern patternAudio;

    static {

        // Load data from JSON file
        MediaLogic.loadSettingsValues(SettingsLogic.loadSettings());

        // Regex to extensions
        patternMedia = Pattern.compile(PATTERN);
        patternVideo = Pattern.compile(PATTERN_VIDEO);
        patternAudio = Pattern.compile(PATTERN_AUDIO);

    }

    private MediaLogic() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This method generates a genders list with directories name relative to path given.
     *
     * @return A String[] with the directories name list (genders list). If directory don't contain
     * directories or have a bad structure, this return null.
     */
    public static String[] gendersList() {

        String[] genders = null;

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return null;

        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s" + File.separator + "%s", songsPath, gender));

            if (!genderDirectory.isDirectory()) {
                List<String> gendersList = new ArrayList<>(Arrays.asList(genders));
                gendersList.remove(gender);
                genders = gendersList.toArray(new String[0]);
            }

        }

        return genders;
    }

    /**
     * This method generates a videos list with videos name relative to path given.
     *
     * @param videosPath Path where is videos.
     * @return the ArrayList<Media> with the videos name or null if this doesn't contain videos.
     */
    public static ArrayList<Media> getVideos(String videosPath) {

        String[] videos = null;
        int videoCounter = 0;
        ArrayList<Media> videoList = new ArrayList<>();

        File directory = new File(videosPath);

        if (directory.isDirectory()) videos = directory.list();

        if (videos == null) return null;

        Arrays.sort(videos, String::compareToIgnoreCase);

        for (String video : videos) {

            File videoDirectory = new File(String.format("%s" + File.separator + "%s", videosPath, video));

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

            // Time TODO
            randomSong = (int) values.get(KEY_RANDOM_SONG);
//            repeatSong = (int) values.get(KEY_REPEAT_SONGS);

            // Credits TODO
//            creditsAmount = (int) values.get(KEY_AMOUNT_CREDITS);
//            lockScreen = (boolean) values.get(KEY_LOCK_SCREEN);
//            saveSongs = (boolean) values.get(KEY_SAVE_SONGS);

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

    public static ArrayList<Song> musicList() {

        String[] genders = null;
        String[] singers;
        String[] songs;
        int songCounter = 0;
        ArrayList<Song> musicList = new ArrayList<>();

        File directory = new File(songsPath);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return null;

        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format("%s" + File.separator + "%s", songsPath, gender));

            if (genderDirectory.isDirectory()) {

                singers = genderDirectory.list();

                if (singers == null) return null;

                Arrays.sort(singers, String::compareToIgnoreCase);

                for (String singer : singers) {

                    File singerDirectory = new File(String.format("%s" + File.separator + "%s" + File.separator + "%s", songsPath, gender, singer));

                    if (singerDirectory.isDirectory()) {

                        songs = singerDirectory.list();

                        if (songs == null) return null;

                        Arrays.sort(songs, String::compareToIgnoreCase);

                        for (String song : songs) {

                            File songFile = new File(String.format("%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s", songsPath, gender, singer, song));

                            if (songFile.isFile()) {

                                Matcher matcher = patternMedia.matcher(songFile.getName());

                                if (matcher.find()) {

                                    musicList.add(new Song(songCounter, song, singer, gender));
                                    songCounter++;

                                }
                            }
                        }
                    }
                }
            }
        }

        return musicList;

    }

    public static ArrayList<ArrayList<Song>> musicListByGenders(ArrayList<Song> musicList, String[] gendersList) {

        ArrayList<ArrayList<Song>> musicListByGenders = new ArrayList<>();

        if (gendersList == null) return null;

        Arrays.sort(gendersList, String::compareToIgnoreCase);

        for (String gender : gendersList) {
            ArrayList<Song> musicListByGender = new ArrayList<>();
            for (Song song : musicList) {
                if (gender.equals(song.getGender())) {
                    musicListByGender.add(song);
                }

            }
            musicListByGenders.add(musicListByGender);
        }

        return musicListByGenders;
    }

    public static void shutdown() throws RuntimeException, IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
            shutdownCommand = "shutdown -h now";
        }
        else if ("Windows".equals(operatingSystem)) {
            shutdownCommand = "shutdown.exe -s -t 0";
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }

}
