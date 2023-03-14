package com.namarie.entity;

public class Song extends Multimedia {

    private final String singer;
    private final String gender;

    public Song(int number, String name, String extension, String singer, String gender) {
        super(number, name, extension);
        this.singer = singer;
        this.gender = gender;
    }

    public String getSinger() {
        return singer;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return String.format(" %05d %s %s ", getNumber(), getSinger(), getName().substring(0, getName().length() - 4));
    }
}