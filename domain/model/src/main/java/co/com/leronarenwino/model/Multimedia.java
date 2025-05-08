package co.com.leronarenwino.model;

import lombok.Getter;

import java.io.File;

@Getter
public class Multimedia {

    public static final String FORMAT_MULTIMEDIA = "%s%s%s";

    private final int number;
    private final String path;

    public Multimedia(int number, String path) {
        this.number = number;
        this.path = path;
    }

    public String pathToFileMultimedia(String parentDirectoryPath) {

        return String.format(FORMAT_MULTIMEDIA, parentDirectoryPath, File.separator, this.path);

    }

    @Override
    public String toString() {
        return String.format(" %05d %s ", this.number, this.path);
    }

}
