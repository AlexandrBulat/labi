package com.example.ironman.laba2;

import android.graphics.Bitmap;

/**
 * Created by Ironman on 27.10.2016.
 */
public class Contacts {


    private String name;
    private String image;


    public Contacts() {

    }

    public Contacts(String name, String image) {

        this.name = name;
        this.image = image;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}