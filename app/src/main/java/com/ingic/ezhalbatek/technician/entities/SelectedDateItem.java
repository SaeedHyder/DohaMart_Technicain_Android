package com.ingic.ezhalbatek.technician.entities;

import java.util.Date;

/**
 * Created by saeedhyder on 7/12/2018.
 */

public class SelectedDateItem {

    Date date;
    String type;

    public SelectedDateItem(Date date, String type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
