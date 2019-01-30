package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 7/18/2018.
 */

public class MarkRoomDone {

    @SerializedName("subscription_room_id")
    @Expose
    private String subscriptionRoomId;
    @SerializedName("technician_id")
    @Expose
    private String technicianId;
    @SerializedName("visit_id")
    @Expose
    private String visitId;
    @SerializedName("AdditionalJobs")
    @Expose
    private List<AdditionalJobMarkDone> additionalJobs = new ArrayList<>();

    public MarkRoomDone(String subscriptionRoomId, String technicianId, String visitId, List<AdditionalJobMarkDone> additionalJobs) {
        this.subscriptionRoomId = subscriptionRoomId;
        this.technicianId = technicianId;
        this.visitId = visitId;
        this.additionalJobs = additionalJobs;
    }

    public String getSubscriptionRoomId() {
        return subscriptionRoomId;
    }

    public void setSubscriptionRoomId(String subscriptionRoomId) {
        this.subscriptionRoomId = subscriptionRoomId;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public List<AdditionalJobMarkDone> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(List<AdditionalJobMarkDone> additionalJobs) {
        this.additionalJobs = additionalJobs;
    }
}
