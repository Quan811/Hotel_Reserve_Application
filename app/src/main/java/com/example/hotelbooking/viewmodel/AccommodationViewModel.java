package com.example.hotelbooking.viewmodel;

import com.example.hotelbooking.model.Accommodation;

public class AccommodationViewModel {
    private static AccommodationViewModel instance;
    private Accommodation accommodation;

    private AccommodationViewModel() {
    }

    public static AccommodationViewModel getInstance() {
        if (instance == null) {
            instance = new AccommodationViewModel();
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
