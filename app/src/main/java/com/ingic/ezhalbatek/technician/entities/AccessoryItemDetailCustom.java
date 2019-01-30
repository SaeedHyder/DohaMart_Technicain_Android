package com.ingic.ezhalbatek.technician.entities;

/**
 * Created by saeedhyder on 7/17/2018.
 */

public class AccessoryItemDetailCustom {

    String brandName;
    String type;
    String model;
    String quantity;

    public AccessoryItemDetailCustom(String brandName, String type, String model, String quantity) {
        this.brandName = brandName;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
