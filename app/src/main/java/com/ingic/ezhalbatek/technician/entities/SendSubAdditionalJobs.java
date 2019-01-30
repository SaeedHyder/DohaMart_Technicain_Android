package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SendSubAdditionalJobs {

    @SerializedName("visit_id")
    @Expose
    private String visit_id;
    @SerializedName("technician_id")
    @Expose
    private String technicianId;
    @SerializedName("AdditionalJobs")
    @Expose
    private List<AdditionalJobMarkDone> additionalJobs = new ArrayList<>();

    public SendSubAdditionalJobs(String visit_id, String technicianId, List<AdditionalJobMarkDone> additionalJobs) {
        this.visit_id = visit_id;
        this.technicianId = technicianId;
        this.additionalJobs = additionalJobs;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }


    public List<AdditionalJobMarkDone> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(List<AdditionalJobMarkDone> additionalJobs) {
        this.additionalJobs = additionalJobs;
    }
}
