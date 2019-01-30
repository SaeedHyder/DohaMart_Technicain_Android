package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VisitDetailEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;
    @SerializedName("technician_id")
    @Expose
    private Integer technicianId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("visit_date")
    @Expose
    private String visitDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("subscription")
    @Expose
    private Subscription subscription;
    @SerializedName("user")
    @Expose
    private UserEnt user;
    @SerializedName("additional_jobs")
    @Expose
    private ArrayList<Object> additionalJobs = new ArrayList<>();
    @SerializedName("subscription_rooms")
    @Expose
    private ArrayList<CreateRoomEnt> subscriptionRooms = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public UserEnt getUser() {
        return user;
    }

    public void setUser(UserEnt user) {
        this.user = user;
    }

    public ArrayList<Object> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(ArrayList<Object> additionalJobs) {
        this.additionalJobs = additionalJobs;
    }

    public ArrayList<CreateRoomEnt> getSubscriptionRooms() {
        return subscriptionRooms;
    }

    public void setSubscriptionRooms(ArrayList<CreateRoomEnt> subscriptionRooms) {
        this.subscriptionRooms = subscriptionRooms;
    }
}
