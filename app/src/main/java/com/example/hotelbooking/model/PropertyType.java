package com.example.hotelbooking.model;

public class PropertyType {
    int resourceImg;
    String propertyTypeName;

    public PropertyType(int resourceImg, String propertyTypeName) {
        this.resourceImg = resourceImg;
        this.propertyTypeName = propertyTypeName;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }
}
