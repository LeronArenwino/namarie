package com.namarie.entity;

import java.io.File;
import java.util.Optional;

import static com.namarie.logic.MultimediaLogic.FORMAT_MULTIMEDIA;

public class Multimedia {

    private final int number;
    private final String name;

    public Multimedia(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Optional<String> pathToVideo(String path) {

        String pathToVideo = String.format(FORMAT_MULTIMEDIA, path, File.separator, getName());

        return Optional.ofNullable(pathToVideo);

    }

    @Override
    public String toString() {
        return String.format(" %05d %s ", getNumber(), getName().substring(0, getName().length() - 4));
    }

}
