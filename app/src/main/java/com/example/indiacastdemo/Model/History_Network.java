package com.example.indiacastdemo.Model;

import android.graphics.drawable.Drawable;

public class History_Network {

    private String Network_ID;
    private String Network_Name;
    private String Created_Date;
    private Drawable Photo;
    private String count;
    private String Status;

    public  History_Network(String network_ID, String network_Name, String created_Date, Drawable photo, String status) {
        Network_ID = network_ID;
        Network_Name = network_Name;
        Created_Date = created_Date;
        Photo = photo;
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

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public Drawable getPhoto() {
        return Photo;
    }

    public void setPhoto(Drawable photo) {
        Photo = photo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
