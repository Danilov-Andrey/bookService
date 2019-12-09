package com.nc.bookservice.models;

public class SaveBook {
    private int year;
    private String name;
    private String authorFirstName;
    private String authorLastName;
    private String publisherName;
    private int rate;
    private int count;

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public int getRate() {
        return rate;
    }

    public int getCount() {
        return count;
    }
}
