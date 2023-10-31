package com.example.hotelbooking.data;

import com.example.hotelbooking.model.Accommodation;

public class AccommodationHolder {
    private static AccommodationHolder instance;
    private Accommodation accommodation;

    private AccommodationHolder() {
        // Khởi tạo lớp Singleton
    }

    public static AccommodationHolder getInstance() {
        if (instance == null) {
            instance = new AccommodationHolder();
        }
        return instance;
    }

    public Accommodation getDataToPass() {
        return accommodation;
    }

    public void setDataToPass(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
