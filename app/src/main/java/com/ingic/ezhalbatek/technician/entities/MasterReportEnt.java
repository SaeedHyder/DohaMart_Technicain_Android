package com.ingic.ezhalbatek.technician.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 7/11/2018.
 */

public class MasterReportEnt {

    ArrayList<SubscriptionPaymentEnt> Subscription=new ArrayList<>();
    ArrayList<UserPaymentEnt> Service=new ArrayList<>();

    public ArrayList<SubscriptionPaymentEnt> getSubscription() {
        return Subscription;
    }

    public void setSubscription(ArrayList<SubscriptionPaymentEnt> subscription) {
        Subscription = subscription;
    }

    public ArrayList<UserPaymentEnt> getService() {
        return Service;
    }

    public void setService(ArrayList<UserPaymentEnt> service) {
        Service = service;
    }
}
