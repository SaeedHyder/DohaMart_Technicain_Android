package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 7/10/2018.
 */

public class AdditionalJob {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("request_job_id")
    @Expose
    private Integer requestJobId;
    @SerializedName("additional_job_item_id")
    @Expose
    private Integer additionalJobItemId;
    @SerializedName("user_subs_visit_id")
    @Expose
    private String userSubsVisitId;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("item")
    @Expose
    private AdditionalJobItem item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRequestJobId() {
        return requestJobId;
    }

    public void setRequestJobId(Integer requestJobId) {
        this.requestJobId = requestJobId;
    }

    public Integer getAdditionalJobItemId() {
        return additionalJobItemId;
    }

    public void setAdditionalJobItemId(Integer additionalJobItemId) {
        this.additionalJobItemId = additionalJobItemId;
    }

    public String getUserSubsVisitId() {
        return userSubsVisitId;
    }

    public void setUserSubsVisitId(String userSubsVisitId) {
        this.userSubsVisitId = userSubsVisitId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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

    public AdditionalJobItem getItem() {
        return item;
    }

    public void setItem(AdditionalJobItem item) {
        this.item = item;
    }
}
