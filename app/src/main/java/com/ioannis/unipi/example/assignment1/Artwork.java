package com.ioannis.unipi.example.assignment1;


// for the popular cards
public class Artwork {

    private final int imageResId;
    private final String title;
    private final String description;
    private final String artist;
    private final String year;
    private final String medium;
    private final String dimensions;
    private final String art_movement;
    private final String location;


    public Artwork(int imageResId, String title, String description, String artist, String year,
                   String medium, String dimensions, String art_movement, String location) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.year = year;
        this.medium = medium;
        this.dimensions = dimensions;
        this.art_movement = art_movement;
        this.location = location;
    }

    public int getImageResId(){
            return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getArtist() { return artist; }

    public String getYear() { return year; }

    public String getMedium() {
        return medium;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getArtMovement() {
        return art_movement;
    }

    public String getLocation() {
        return location;
    }

}
