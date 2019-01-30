package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ItemMapping")
    @Expose
    private ItemMapping itemMapping;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemMapping getItemMapping() {
        return itemMapping;
    }

    public void setItemMapping(ItemMapping itemMapping) {
        this.itemMapping = itemMapping;
    }
}
