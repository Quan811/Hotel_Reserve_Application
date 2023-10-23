package com.example.hotelbooking.model;

public class Category {
    int resourceImg;
    String categoryName;

    public Category(int resourceImg, String categoryName) {
        this.resourceImg = resourceImg;
        this.categoryName = categoryName;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
