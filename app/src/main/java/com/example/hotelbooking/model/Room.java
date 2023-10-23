package com.example.hotelbooking.model;

import java.io.Serializable;

public class Room implements Serializable {
    String roomType;
    String price;
    String roomDescription;
    String numberAvailable;
    String roomImg;

    public Room() {
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    public Room(String roomType, String price, String roomDescription, String numberAvailable, String roomImg) {
        this.roomType = roomType;
        this.price = price;
        this.roomDescription = roomDescription;
        this.numberAvailable = numberAvailable;
        this.roomImg = roomImg;
    }


    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getNumberAvailable() {
        return numberAvailable;
    }

    public void setNumberAvailable(String numberAvailable) {
        this.numberAvailable = numberAvailable;
    }
}
