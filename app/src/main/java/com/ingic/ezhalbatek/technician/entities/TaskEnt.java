package com.ingic.ezhalbatek.technician.entities;

/**
 * Created on 6/28/18.
 */
public class TaskEnt {

    String id;
    String name;
    String quantity;

    public TaskEnt(String id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /*  private String taskTitle;
    private String taskPrice;
    private String currencyCode;

    public TaskEnt(String taskTitle, String taskPrice,String currencyCode) {
        this.taskTitle = taskTitle;
        this.taskPrice = taskPrice;
        this.currencyCode=currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskPrice() {
        return taskPrice;
    }*/
}
