package com.example.indiacastdemo.Model;

public class Channel {
    private String ChannelName;
    private String LCN;
    private String  Position;
    private String Genre;
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

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
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

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public Channel(String channelName, String LCN, String position, String genre, String networkId, String createdDate) {
        ChannelName = channelName;
        this.LCN = LCN;
        Position = position;
        Genre = genre;
        NetworkId = networkId;
        this.createdDate = createdDate;
    }

    public Channel() {
    }

}
