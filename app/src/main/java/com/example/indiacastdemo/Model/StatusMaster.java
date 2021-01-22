package com.example.indiacastdemo.Model;

import android.graphics.drawable.Drawable;

public class StatusMaster {
    private String Network_ID;
    private String Network_Name;
    private String By;
    private String At;
    private Drawable Photo;
    private String created_Date;
    private String Status;

    public StatusMaster(String network_ID, String network_Name, String by, String at, Drawable photo, String created_Date, String status) {
        Network_ID = network_ID;
        Network_Name = network_Name;
        By = by;
        At = at;
        Photo = photo;
        this.created_Date = created_Date;
        Status = status;
    }

    public String getNetwork_ID() {
        return Network_ID;
    }

    public void setNetwork_ID(String network_ID) {
        Network_ID = network_ID;
    }

    public String getNetwork_Name() {
        return Network_Name;
    }

    public void setNetwork_Name(String network_Name) {
        Network_Name = network_Name;
    }

    public String getBy() {
        return By;
    }

    public void setBy(String by) {
        By = by;
    }

    public String getAt() {
        return At;
    }

    public void setAt(String at) {
        At = at;
    }

    public Drawable getPhoto() {
        return Photo;
    }

    public void setPhoto(Drawable photo) {
        Photo = photo;
    }

    public String getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(String created_Date) {
        this.created_Date = created_Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

