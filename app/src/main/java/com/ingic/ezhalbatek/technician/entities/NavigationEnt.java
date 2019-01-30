package com.ingic.ezhalbatek.technician.entities;

/**
 * Created on 12/15/2017.
 */

public class NavigationEnt {
    private int ResId;
    private int Title;

    public NavigationEnt(int resId, int title) {
        ResId = resId;
        Title = title;

    }
    public int getResId() {

        return ResId;
    }

    public void setResId(int resId) {
        ResId = resId;
    }

    public int getTitle() {
        return Title;
    }

    public void setTitle(int title) {
        Title = title;
    }
}
