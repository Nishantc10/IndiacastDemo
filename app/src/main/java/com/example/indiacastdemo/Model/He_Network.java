package com.example.indiacastdemo.Model;

import android.graphics.drawable.Drawable;

public class He_Network {

    private String Network_ID;
    private String Network_Name;
    private Drawable Photo;
    private String location;
    private String count;
    private String Status;
    private String MSO_name;
    private String CRN_no;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public He_Network(String network_ID, String network_Name, String created_date, String count, Drawable photo, String location,String MSO_name, String CRN_no) {
        Network_ID = network_ID;
        Network_Name = network_Name;
        Status = created_date;
        this.count = count;
        this.location = location;
        Photo = photo;
        this.MSO_name = MSO_name;
        this.CRN_no = CRN_no;

    }

    public He_Network(String network_ID, String network_Name, Drawable photo) {
        Network_ID = network_ID;
        Network_Name = network_Name;
        Photo = photo;
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

    public String getMSO_name() {
        return MSO_name;
    }

    public void setMSO_name(String MSO_name) {
        this.MSO_name = MSO_name;
    }

    public String getCRN_no() {
        return CRN_no;
    }

    public void setCRN_no(String CRN_no) {
        this.CRN_no = CRN_no;
    }
}
