package com.namarie.logic;

import com.namarie.entity.Multimedia;
import com.namarie.entity.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.namarie.logic.SettingsSingleton.*;

/**
 * This class control the logic of the application relative with multimedia, like generate music list, video list, genders list, etc.
 *
 * @author Francisco Dueñas
 */
public class MultimediaLogic {

    // Create a Logger
    private static final Logger logger = Logger.getLogger(MultimediaLogic.class.getName());

    // String format
    public static final String FORMAT_MULTIMEDIA = "%s%s%s";
    public static final String FORMAT_LIST = "%s%s%s%s%s";

    // Regex file extensions
    private static final List<String> MULTIMEDIA_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("mp3", "mp4", "wav", "wma", "mov", "wmv", "avi", "flv", "mkv", "mpg", "mpeg"));
    private static final List<String> AUDIO_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("mp3", "wav", "wma", "mpeg"));
    private static final List<String> VIDEO_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("mp4", "mov", "wmv", "avi", "flv", "mkv", "mpg"));

    private static List<Song> musicList;
    private static List<List<Song>> musicListByGenders;
    private static List<String> gendersList;
    private static List<Multimedia> videosList;
    private static List<Multimedia> promotionalVideosList;

    private static int songCounter = 0;
    private static int videosCounter = 0;
    private static int promotionalVideosCounter = 0;

    static {

        setGendersList(listDirectories(getPathToSongs()));
        setMusicList(generateMusicList(getPathToSongs()));
        setMusicListByGenders(generateMusicListByGender(getMusicList(), getGendersList()));
        setVideosList(videosList(filesListByExtensions(getPathToVideos(), VIDEO_EXTENSIONS)));
        setPromotionalVideosList(promotionalVideosList(filesListByExtensions(getPathToPromotionalVideos(), VIDEO_EXTENSIONS)));

    }

    private MultimediaLogic() {
        throw new IllegalStateException("Utility class");
    }

    // Getters and setters


    public static List<Song> getMusicList() {
        return musicList;
    }

    public static void setMusicList(List<Song> musicList) {
        MultimediaLogic.musicList = musicList;
    }

    public static List<List<Song>> getMusicListByGenders() {
        return musicListByGenders;
    }

    public static void setMusicListByGenders(List<List<Song>> musicListByGenders) {
        MultimediaLogic.musicListByGenders = musicListByGenders;
    }

    public static List<String> getGendersList() {
        return gendersList;
    }

    public static void setGendersList(List<String> gendersList) {
        MultimediaLogic.gendersList = gendersList;
    }

    public static List<Multimedia> getVideosList() {
        return videosList;
    }

    public static void setVideosList(List<Multimedia> videosList) {
        MultimediaLogic.videosList = videosList;
    }

    public static List<Multimedia> getPromotionalVideosList() {
        return promotionalVideosList;
    }

    public static void setPromotionalVideosList(List<Multimedia> promotionalVideosList) {
        MultimediaLogic.promotionalVideosList = promotionalVideosList;
    }


    public static List<String> getAudioExtensions() {
        return AUDIO_EXTENSIONS;
    }

    public static List<String> getVideoExtensions() {
        return VIDEO_EXTENSIONS;
    }

    // Methods
    private static List<String> filesListByExtensions(String path, List<String> extensions) {

        List<String> songsList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(path), 1)) {
            songsList = stream.map(Path::normalize)
                    .filter(Files::isRegularFile)
                    .map(file -> file.getFileName().toString())
                    .filter(file -> extensions.stream().anyMatch(file::endsWith))
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            logger.log(Level.WARNING, () -> "IOException error! " + exception);
        }

        return songsList;

    }

    private static List<Song> generateMusicList(String path) {

        List<Song> musicList = new ArrayList<>();

        if (!getGendersList().isEmpty()) musicList = Stream.of(musicList, songsListByGender(path, getGendersList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return musicList;

    }

    private static List<List<Song>> generateMusicListByGender(List<Song> musicList, List<String> gendersList) {

        List<List<Song>> musicListByGenders = new ArrayList<>();

        if (!gendersList.isEmpty()) for (String gender : gendersList) {

            List<Song> musicListByGender = new ArrayList<>();
            for (Song song : musicList) {
                if (gender.equals(song.getGender())) musicListByGender.add(song);
            }
            musicListByGenders.add(musicListByGender);
        }

        return musicListByGenders;

    }

    /**
     * This method generates a list with all directories name relative to path given.
     *
     * @param path Path of directory
     * @return List String with all directories names relatives to path given.
     */
    private static List<String> listDirectories(String path) {

        List<String> directoriesList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(path), 1)) {
            directoriesList = stream.map(Path::normalize)
                    .filter(Files::isDirectory)
                    .map(directory -> directory.getFileName().toString())
                    .collect(Collectors.toList());
            directoriesList.remove(0);
        } catch (IOException exception) {
            logger.log(Level.WARNING, () -> "IOException error! " + exception);
        }

        return directoriesList;

    }

    private static List<Song> songsList(List<String> filesList, String gender, String singer) {

        List<Song> songsList = new ArrayList<>();

        if (!filesList.isEmpty()) for (String name : filesList) {

            songsList.add(new Song(songCounter++, name, singer, gender));

        }

        return songsList;

    }

    private static List<Song> songsListBySinger(String path, String gender, List<String> singers) {

        List<Song> songsList = new ArrayList<>();
        List<String> songsFromFiles;

        if (!singers.isEmpty()) for (String singer : singers) {

            songsFromFiles = filesListByExtensions(String.format(FORMAT_LIST, path, File.separator, gender, File.separator, singer), MULTIMEDIA_EXTENSIONS);

            songsList = Stream.of(songsList, songsList(songsFromFiles, gender, singer))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        }

        return songsList;

    }

    private static List<Song> songsListByGender(String path, List<String> genders) {

        List<Song> songsListByGender = new ArrayList<>();
        List<String> singersByGender;

        if (!genders.isEmpty()) for (String gender : genders) {

            singersByGender = listDirectories(String.format(FORMAT_MULTIMEDIA, path, File.separator, gender));

            songsListByGender = Stream.of(songsListByGender, songsListBySinger(path, gender, singersByGender))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        }

        return songsListByGender;

    }

    private static List<Multimedia> promotionalVideosList(List<String> filesList) {

        List<Multimedia> promotionalVideosList = new ArrayList<>();

        if (!filesList.isEmpty()) for (String name : filesList) {
            promotionalVideosList.add(new Multimedia(promotionalVideosCounter++, name));
        }

        return promotionalVideosList;

    }

    private static List<Multimedia> videosList(List<String> filesList) {

        List<Multimedia> videosList = new ArrayList<>();

        if (!filesList.isEmpty()) for (String name : filesList) {
            videosList.add(new Multimedia(videosCounter++, name));
        }

        return videosList;

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
