package com.ingic.ezhalbatek.technician.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 7/18/2018.
 */

public class AdditionalJobMarkDone {



    @SerializedName("additional_job_item_id")
    @Expose
    private String additionalJobItemId;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public AdditionalJobMarkDone(String additionalJobItemId, String quantity) {
        this.additionalJobItemId = additionalJobItemId;
        this.quantity = quantity;
    }

    public String getAdditionalJobItemId() {
        return additionalJobItemId;
    }

    public void setAdditionalJobItemId(String additionalJobItemId) {
        this.additionalJobItemId = additionalJobItemId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
