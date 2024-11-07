package HelperClasses.HomeAdapter;

import android.os.Parcel;
import android.os.Parcelable;

// artwork needs to be Parcelable so its' objects can be passed fom an intent to another
public class Artwork implements Parcelable {

    private int imageResId;
    private String title;
    private String description;
    private String artist;
    private String year;
    private String medium;
    private String dimensions;
    private String artMovement;
    private String location;

    // Constructor
    public Artwork(int imageResId, String title, String description, String artist, String year,
                   String medium, String dimensions, String artMovement, String location) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.year = year;
        this.medium = medium;
        this.dimensions = dimensions;
        this.artMovement = artMovement;
        this.location = location;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getArtist() {
        return artist;
    }

    public String getYear() {
        return year;
    }

    public String getMedium() {
        return medium;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getArtMovement() {
        return artMovement;
    }

    public String getLocation() {
        return location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setArtMovement(String art_movement) {
        this.artMovement = art_movement;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // parcelable implementation
    protected Artwork(Parcel in) {
        imageResId = in.readInt();
        title = in.readString();
        description = in.readString();
        artist = in.readString();
        year = in.readString();
        medium = in.readString();
        dimensions = in.readString();
        artMovement = in.readString();
        location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(artist);
        dest.writeString(year);
        dest.writeString(medium);
        dest.writeString(dimensions);
        dest.writeString(artMovement);
        dest.writeString(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };
}
