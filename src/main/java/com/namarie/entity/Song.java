package com.namarie.entity;

import java.io.File;

import static com.namarie.controller.JukeboxController.FORMAT_MULTIMEDIA;

public class Song extends Multimedia {

    private static final String FORMAT_SONG_LIST = "%s%s%s%s%s%s%s";

    private final String genre;
    private final String name;
    private final String singer;

    public Song(int number, String path, String genre, String name, String singer) {
        super(number, path);
        this.genre = genre;
        this.name = name;
        this.singer = singer;
    }

    public String getGenre() {
        return genre;
    }

    public String pathToFileSong(String parentDirectoryPath, boolean hasMetadata) {

        return hasMetadata ? String.format(FORMAT_MULTIMEDIA, parentDirectoryPath, File.separator, getPath()) : String.format(FORMAT_SONG_LIST, parentDirectoryPath, File.separator, this.genre, File.separator, this.singer, File.separator, getPath());

    }

    @Override
    public String toString() {
        return String.format(" %05d %s %s ", getNumber(), this.singer, this.name);
    }
}