package com.namarie.models;

public class Song {

    private int number;
    private String name;
    private String singer;
    private String gender;

    public Song(int number, String name, String singer, String gender) {
        this.number = number;
        this.name = name;
        this.singer = singer;
        this.gender = gender;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("%05d | %s  %s", getNumber(), getSinger(), getName().substring(0,getName().length()-4));
    }
}
