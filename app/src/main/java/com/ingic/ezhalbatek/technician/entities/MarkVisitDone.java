package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 7/19/2018.
 */

public class MarkVisitDone {

    @SerializedName("technician_id")
    @Expose
    private String technicianId;
    @SerializedName("visit_id")
    @Expose
    private String visitId;
    @SerializedName("AdditionalJobs")
    @Expose
    private List<AdditionalJobMarkDone> additionalJobs = new ArrayList<>();


    public MarkVisitDone(String technicianId, String visitId, List<AdditionalJobMarkDone> additionalJobs) {
        this.technicianId = technicianId;
        this.visitId = visitId;
        this.additionalJobs = additionalJobs;
    }

    public List<AdditionalJobMarkDone> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(List<AdditionalJobMarkDone> additionalJobs) {
        this.additionalJobs = additionalJobs;
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
}
