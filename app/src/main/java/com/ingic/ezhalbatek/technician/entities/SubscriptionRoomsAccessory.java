package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionRoomsAccessory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("accessory_id")
    @Expose
    private Integer accessoryId;
    @SerializedName("room_id")
    @Expose
    private Integer roomId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;
    @SerializedName("visit_id")
    @Expose
    private Integer visitId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("item_model_id")
    @Expose
    private Integer itemModelId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("accessory")
    @Expose
    private Accessory accessory;
    @SerializedName("type")
    @Expose
    private AccessoruesDetailItem type;
    @SerializedName("brand")
    @Expose
    private AccessoruesDetailItem brand;
    @SerializedName("item_model")
    @Expose
    private AccessoruesDetailItem itemModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Integer accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(Integer itemModelId) {
        this.itemModelId = itemModelId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public AccessoruesDetailItem getType() {
        return type;
    }

    public void setType(AccessoruesDetailItem type) {
        this.type = type;
    }

    public AccessoruesDetailItem getBrand() {
        return brand;
    }

    public void setBrand(AccessoruesDetailItem brand) {
        this.brand = brand;
    }

    public AccessoruesDetailItem getItemModel() {
        return itemModel;
    }

    public void setItemModel(AccessoruesDetailItem itemModel) {
        this.itemModel = itemModel;
    }
}
