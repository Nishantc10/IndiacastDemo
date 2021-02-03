package com.example.indiacastdemo.Model;

public class IndiaCastChannel {
    private String IndiaCastChannelName;
    private String LCN;
    private String  Position;
    private String Indiacast_status;
    private String NetworkId;
    private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getNetworkId() {
        return NetworkId;
    }

    public void setNetworkId(String networkId) {
        NetworkId = networkId;
    }

    public String getIndiaCastChannelName() {
        return IndiaCastChannelName;
    }

    public void setIndiaCastChannelName(String indiaCastChannelName) {
        IndiaCastChannelName = indiaCastChannelName;
    }

    public String getIndiacast_status() {
        return Indiacast_status;
    }

    public void setIndiacast_status(String indiacast_status) {
        Indiacast_status = indiacast_status;
    }

    public String getLCN() {
        return LCN;
    }

    public void setLCN(String LCN) {
        this.LCN = LCN;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }


    public IndiaCastChannel(String indiaCastChannelName, String LCN, String position, String indiacast_status, String networkId, String createdDate) {
        IndiaCastChannelName = indiaCastChannelName;
        this.LCN = LCN;
        Position = position;
        NetworkId = networkId;
        Indiacast_status = indiacast_status;
        this.createdDate = createdDate;
    }

    public IndiaCastChannel() {
    }

}
