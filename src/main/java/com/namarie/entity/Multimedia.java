package com.namarie.entity;

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

    @Override
    public String toString() {
        return String.format(" %05d %s ", getNumber(), getName().substring(0, getName().length() - 4));
    }

}
