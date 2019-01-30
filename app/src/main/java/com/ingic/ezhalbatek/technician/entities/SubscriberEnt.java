package com.ingic.ezhalbatek.technician.entities;

/**
 * Created on 6/25/18.
 */
public class SubscriberEnt {
    public SubscriberEnt(String type) {
        this.type = type;
    }

    private String type = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
