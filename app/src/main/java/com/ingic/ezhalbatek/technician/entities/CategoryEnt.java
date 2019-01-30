package com.ingic.ezhalbatek.technician.entities;

/**
 * Created on 5/23/18.
 */
public class CategoryEnt {
    private String title;
    private int resID;

    public CategoryEnt(String title, int resID) {
        this.title = title;
        this.resID = resID;
    }

    public String getTitle() {
        return title;
    }

    public int getResID() {
        return resID;
    }
}
