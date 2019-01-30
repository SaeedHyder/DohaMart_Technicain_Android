package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 7/16/2018.
 */

public class AccessoriesDataEnt {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("accessory_id")
    @Expose
    private String accessoryId;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("visit_id")
    @Expose
    private String visitId;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("item_model_id")
    @Expose
    private String itemModelId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
  /*  @SerializedName("accessory")
    @Expose
    private AccessoruesDetailItem accessory;*/
    @SerializedName("brand")
    @Expose
    private AccessoruesDetailItem brand;
    @SerializedName("type")
    @Expose
    private AccessoruesDetailItem type;
    @SerializedName("item_model")
    @Expose
    private AccessoruesDetailItem itemModel;

    private boolean isChecked=false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public AccessoruesDetailItem getBrand() {
        return brand;
    }

    public void setBrand(AccessoruesDetailItem brand) {
        this.brand = brand;
    }

    public AccessoruesDetailItem getType() {
        return type;
    }

    public void setType(AccessoruesDetailItem type) {
        this.type = type;
    }

    public AccessoruesDetailItem getItemModel() {
        return itemModel;
    }

    public void setItemModel(AccessoruesDetailItem itemModel) {
        this.itemModel = itemModel;
    }

   /* public AccessoruesDetailItem getAccessory() {
        return accessory;
    }

    public void setAccessory(AccessoruesDetailItem accessory) {
        this.accessory = accessory;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(String itemModelId) {
        this.itemModelId = itemModelId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
