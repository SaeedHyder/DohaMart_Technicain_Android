package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 7/20/2018.
 */

public class MarkJobDone {

    @SerializedName("request_id")
    @Expose
    private String request_id;
    @SerializedName("technician_id")
    @Expose
    private String technicianId;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("AdditionalJobs")
    @Expose
    private List<AdditionalJobMarkDone> additionalJobs = new ArrayList<>();

    public MarkJobDone(String request_id, String technicianId, String total, List<AdditionalJobMarkDone> additionalJobs) {
        this.request_id = request_id;
        this.technicianId = technicianId;
        this.total = total;
        this.additionalJobs = additionalJobs;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<AdditionalJobMarkDone> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(List<AdditionalJobMarkDone> additionalJobs) {
        this.additionalJobs = additionalJobs;
    }
}
