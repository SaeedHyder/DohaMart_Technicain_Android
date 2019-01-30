package com.ingic.ezhalbatek.technician.entities;

import java.util.List;

/**
 * Created on 5/21/18.
 */
public class SubscriptionEntity {
    private String title;
    private List<String> features;
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
