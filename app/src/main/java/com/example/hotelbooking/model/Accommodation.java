package com.example.hotelbooking.model;

import java.io.Serializable;
import java.util.List;

public class Accommodation implements Serializable {
    String accommodationName;
    String accommodationDescription;
    String accommodationRating;
    String accommodationType;
    String thumbnail;
    List<String> imgURL;
    String location;
    List<Room> roomList;

    public Accommodation() {
    }

    public Accommodation(String accommodationName, String accommodationDescription, String accommodationRating, String accommodationType, String thumbnail, List<String> imgURL, String location, List<Room> roomList) {
        this.accommodationName = accommodationName;
        this.accommodationDescription = accommodationDescription;
        this.accommodationRating = accommodationRating;
        this.accommodationType = accommodationType;
        this.thumbnail = thumbnail;
        this.imgURL = imgURL;
        this.location = location;
        this.roomList = roomList;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getAccommodationDescription() {
        return accommodationDescription;
    }

    public void setAccommodationDescription(String accommodationDescription) {
        this.accommodationDescription = accommodationDescription;
    }

    public String getAccommodationRating() {
        return accommodationRating;
    }

    public void setAccommodationRating(String accommodationRating) {
        this.accommodationRating = accommodationRating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getImgURL() {
        return imgURL;
    }

    public void setImgURL(List<String> imgURL) {
        this.imgURL = imgURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
}
