package com.namarie.entity;

import java.io.File;
import java.util.Optional;

import static com.namarie.logic.SettingsSingleton.ACTION_SONG;

public class Song extends Multimedia {

    private final String singer;
    private final String gender;

    public Song(int number, String name, String singer, String gender) {
        super(number, name);
        this.singer = singer;
        this.gender = gender;
    }

    public String getSinger() {
        return singer;
    }

    public String getGender() {
        return gender;
    }

    public Optional<String> pathToSong(String path){

        String pathToSong = String.format(ACTION_SONG, path, File.separator, getGender(), File.separator, getSinger(), File.separator, getName());

        return Optional.ofNullable(pathToSong);

    }

    @Override
    public String toString() {
        return String.format(" %05d %s %s ", getNumber(), getSinger(), getName().substring(0, getName().length() - 4));
    }
}