package com.ingic.ezhalbatek.technician.entities;

public class HashMapEnt {

    String name;
    String quantity;
    private boolean isSelected =false;

    public HashMapEnt(String name, String quantity,boolean isSelected) {
        this.name = name;
        this.quantity = quantity;
        this.isSelected=isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
