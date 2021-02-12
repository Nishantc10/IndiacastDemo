package com.example.indiacastdemo.Model;

public class Network {

    private String NetworkName;
    private int Photo;
    private String Status;

    public void setNetworkName(String networkName) {
        NetworkName = networkName;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getNetworkName() {
        return NetworkName;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public Network(String networkName, int photo, String status) {
        NetworkName = networkName;
        Photo = photo;
        Status = status;

    }

    public Network() {
    }

}
