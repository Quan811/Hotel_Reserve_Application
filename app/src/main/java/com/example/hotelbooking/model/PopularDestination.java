package com.example.hotelbooking.model;

public class PopularDestination {
    String popularDesinationName;
    int resourceImg;
    int numberAccomodation;

    public PopularDestination(int resourceImg,String popularDesinationName, int numberAccomodation) {
        this.resourceImg = resourceImg;
        this.popularDesinationName = popularDesinationName;
        this.numberAccomodation = numberAccomodation;
    }

    public String getPopularDesinationName() {
        return popularDesinationName;
    }

    public void setPopularDesinationName(String popularDesinationName) {
        this.popularDesinationName = popularDesinationName;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public int getNumberAccomodation() {
        return numberAccomodation;
    }

    public void setNumberAccomodation(int numberAccomodation) {
        this.numberAccomodation = numberAccomodation;
    }
}
