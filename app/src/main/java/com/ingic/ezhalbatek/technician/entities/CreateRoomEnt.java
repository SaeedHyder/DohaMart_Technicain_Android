package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 7/16/2018.
 */

public class CreateRoomEnt {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("user_subs_visit_id")
    @Expose
    private String userSubsVisitId;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("accessories")
    @Expose
    private ArrayList<Accessory> subscriptionRoomsAccessories=new ArrayList<>();

    boolean isRoomSelected=false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRoomSelected() {
        return isRoomSelected;
    }

    public void setRoomSelected(boolean roomSelected) {
        isRoomSelected = roomSelected;
    }

    public ArrayList<Accessory> getSubscriptionRoomsAccessories() {
        return subscriptionRoomsAccessories;
    }

    public void setSubscriptionRoomsAccessories(ArrayList<Accessory> subscriptionRoomsAccessories) {
        this.subscriptionRoomsAccessories = subscriptionRoomsAccessories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUserSubsVisitId() {
        return userSubsVisitId;
    }

    public void setUserSubsVisitId(String userSubsVisitId) {
        this.userSubsVisitId = userSubsVisitId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
