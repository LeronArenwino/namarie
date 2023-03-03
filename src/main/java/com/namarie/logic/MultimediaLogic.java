package com.namarie.logic;

import com.namarie.entity.Multimedia;
import com.namarie.entity.Song;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.namarie.logic.SettingsSingleton.*;

/**
 * This class control the logic of the application relative with media, like generate music list, video list, genders list, etc.
 *
 * @author Francisco Dueñas
 */
public class MultimediaLogic {

    // Regex file extensions
    private static final String PATTERN = "(\\S+(\\.(?i)(mp3|mp4|wav|wma|mov|wmv|avi|flv|mkv|mpg|mpeg))$)";
    private static final String PATTERN_VIDEO = "(\\S+(\\.(?i)(mp4|mov|wmv|avi|flv|mkv|mpg))$)";
    private static final String PATTERN_AUDIO = "(\\S+(\\.(?i)(mp3|wav|wma|mpeg))$)";

    private static final Pattern patternMultimedia;
    private static final Pattern patternVideo;
    private static final Pattern patternAudio;

    private static String[] genders;
    private static List<Song> musicList;

    private static int songCounter = 0;

    static {

        // Regex to extensions
        patternMultimedia = Pattern.compile(PATTERN);
        patternVideo = Pattern.compile(PATTERN_VIDEO);
        patternAudio = Pattern.compile(PATTERN_AUDIO);

        // Music
        loadGendersList(getPathToSongs());
        musicList();

    }

    private MultimediaLogic() {
        throw new IllegalStateException("Utility class");
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
    public static void loadGendersList(String path) {

        genders = null;

        File directory = new File(path);

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) genders = new String[0];

        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format(ACTION_MULTIMEDIA, path, File.separator, gender));

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
    public static List<Multimedia> getVideos(String videosPath) {

        String[] videos = null;
        int videoCounter = 0;
        List<Multimedia> videoList = new ArrayList<>();

        File directory = new File(videosPath);

        if (directory.isDirectory()) videos = directory.list();

        if (videos == null) return Collections.emptyList();

        Arrays.sort(videos, String::compareToIgnoreCase);

        for (String video : videos) {

            File videoDirectory = new File(String.format(ACTION_MULTIMEDIA, videosPath, File.separator, video));

            if (videoDirectory.isFile()) {

                Matcher matcher = patternMultimedia.matcher(videoDirectory.getName());

                if (matcher.find()) {

                    videoList.add(new Multimedia(videoCounter, video));
                    videoCounter++;

                }

            }

        }

        return videoList;

    }

    /**
     * This method load the values in a file (settings.json) to fields.
     *
     */

    public static void musicList() {

        String[] genders = null;
        String[] singers;

        musicList = new ArrayList<>();

        File directory = new File(getPathToSongs());

        if (directory.isDirectory()) genders = directory.list();

        if (genders == null) return;
        Arrays.sort(genders, String::compareToIgnoreCase);

        for (String gender : genders) {

            File genderDirectory = new File(String.format(ACTION_MULTIMEDIA, getPathToSongs(), File.separator, gender));

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

            File songFile = new File(String.format(ACTION_SONG, getPathToSongs(), File.separator, gender, File.separator, singer, File.separator, song));

            if (songFile.isFile()) {
                Matcher matcher = patternMultimedia.matcher(songFile.getName());
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

            File singerDirectory = new File(String.format(ACTION_LIST, getPathToSongs(), File.separator, gender, File.separator, singer));

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
