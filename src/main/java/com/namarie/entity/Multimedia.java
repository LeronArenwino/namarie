package com.namarie.entity;

import java.io.File;

import static com.namarie.controller.JukeboxController.FORMAT_MULTIMEDIA;

public class Multimedia {

    private final int number;
    private final String path;

    public Multimedia(int number, String path) {
        this.number = number;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getNumber() {
        return number;
    }

    public String pathToFileMultimedia(String parentDirectoryPath) {

        return String.format(FORMAT_MULTIMEDIA, parentDirectoryPath, File.separator, this.path);

    }

    @Override
    public String toString() {
        return String.format(" %05d %s ", this.number, this.path);
    }

}
