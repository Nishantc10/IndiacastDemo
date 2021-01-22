package com.example.indiacastdemo.Model;

public class cls_tbl_network_channel_mapped {
    public String ID, CHNL_MPD, Network_ID, Network_Name, Channel_Name, LCN_No, Genre, Position, Landing_Page_Flag, Dual_LCN_Flag, Triple_LCN_Flag, Multiple_LCN, Year, On_Call_Site_Flag, Location, Entered_By, Created_Date, Updated_Date, Uploaded_At, Uploaded_By, Uploaded_Date, Comments;

    public cls_tbl_network_channel_mapped(String ID,String network_ID, String network_Name, String channel_Name,
                                          String LCN_No, String genre, String position, String landing_Page_Flag, String dual_LCN_Flag,
                                          String triple_LCN_Flag, String multiple_LCN, String year, String on_Call_Site_Flag,
                                          String location, String entered_By, String created_Date, String updated_Date, String uploaded_At,
                                          String uploaded_By, String uploaded_Date, String comments) {
        this.ID = ID;
        Network_ID = network_ID;
        Network_Name = network_Name;
        Channel_Name = channel_Name;
        this.LCN_No = LCN_No;
        Genre = genre;
        Position = position;
        Landing_Page_Flag = landing_Page_Flag;
        Dual_LCN_Flag = dual_LCN_Flag;
        Triple_LCN_Flag = triple_LCN_Flag;
        Multiple_LCN = multiple_LCN;
        Year = year;
        On_Call_Site_Flag = on_Call_Site_Flag;
        Location = location;
        Entered_By = entered_By;
        Created_Date = created_Date;
        Updated_Date = updated_Date;
        Uploaded_At = uploaded_At;
        Uploaded_By = uploaded_By;
        Uploaded_Date = uploaded_Date;
        Comments = comments;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCHNL_MPD() {
        return CHNL_MPD;
    }

    public void setCHNL_MPD(String CHNL_MPD) {
        this.CHNL_MPD = CHNL_MPD;
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

    public String getChannel_Name() {
        return Channel_Name;
    }

    public void setChannel_Name(String channel_Name) {
        Channel_Name = channel_Name;
    }

    public String getLCN_No() {
        return LCN_No;
    }

    public void setLCN_No(String LCN_No) {
        this.LCN_No = LCN_No;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getLanding_Page_Flag() {
        return Landing_Page_Flag;
    }

    public void setLanding_Page_Flag(String landing_Page_Flag) {
        Landing_Page_Flag = landing_Page_Flag;
    }

    public String getDual_LCN_Flag() {
        return Dual_LCN_Flag;
    }

    public void setDual_LCN_Flag(String dual_LCN_Flag) {
        Dual_LCN_Flag = dual_LCN_Flag;
    }

    public String getTriple_LCN_Flag() {
        return Triple_LCN_Flag;
    }

    public void setTriple_LCN_Flag(String triple_LCN_Flag) {
        Triple_LCN_Flag = triple_LCN_Flag;
    }

    public String getMultiple_LCN() {
        return Multiple_LCN;
    }

    public void setMultiple_LCN(String multiple_LCN) {
        Multiple_LCN = multiple_LCN;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getOn_Call_Site_Flag() {
        return On_Call_Site_Flag;
    }

    public void setOn_Call_Site_Flag(String on_Call_Site_Flag) {
        On_Call_Site_Flag = on_Call_Site_Flag;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getEntered_By() {
        return Entered_By;
    }

    public void setEntered_By(String entered_By) {
        Entered_By = entered_By;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public String getUpdated_Date() {
        return Updated_Date;
    }

    public void setUpdated_Date(String updated_Date) {
        Updated_Date = updated_Date;
    }

    public String getUploaded_At() {
        return Uploaded_At;
    }

    public void setUploaded_At(String uploaded_At) {
        Uploaded_At = uploaded_At;
    }

    public String getUploaded_By() {
        return Uploaded_By;
    }

    public void setUploaded_By(String uploaded_By) {
        Uploaded_By = uploaded_By;
    }

    public String getUploaded_Date() {
        return Uploaded_Date;
    }

    public void setUploaded_Date(String uploaded_Date) {
        Uploaded_Date = uploaded_Date;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
