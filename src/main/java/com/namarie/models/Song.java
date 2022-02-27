package com.namarie.models;

public class Song {

    private final int number;
    private final String name;
    private final String singer;
    private final String gender;

    public Song(int number, String name, String singer, String gender) {
        this.number = number;
        this.name = name;
        this.singer = singer;
        this.gender = gender;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return String.format("%05d %s %s", getNumber(), getSinger(), getName().substring(0,getName().length()-4));
    }
}
