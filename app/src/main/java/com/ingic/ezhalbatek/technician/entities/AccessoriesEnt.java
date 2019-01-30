package com.ingic.ezhalbatek.technician.entities;

/**
 * Created on 6/27/18.
 */
public class AccessoriesEnt {
    private String itemName;
    private String itemQuantity;

    public AccessoriesEnt(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
