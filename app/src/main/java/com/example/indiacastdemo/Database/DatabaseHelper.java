package com.example.indiacastdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    JSONArray channelmasterarray = null;
    JSONArray statusmasterarray = null;
    JSONArray updatecheckerarray = null;
    JSONArray networkdetailsarray = null;
    JSONArray networkchannelmappingarray = null;
    JSONArray networkchannelmappedarray = null;
    JSONArray networkmonitorsarray = null;
    JSONObject channelmasterobject = null;
    JSONObject statusmasterobject = null;
    JSONObject updatecheckerobject = null;
    JSONObject networkdetailsobject = null;
    JSONObject networkchannelmappingobject = null;
    JSONObject networkchannelmappedobject = null;
    JSONObject networkmonitorsobject = null;

    Cursor c;
    Context mContext;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Indiacast.db";
    //region tbl_user_details
    public static final String tbl_user_details = "tbl_user_details";
    public static final String User_ID = "User_ID";
    public static final String EMP_ID = "EMP_ID";
    public static final String Firstname = "Firstname";
    public static final String Lastname = "Lastname";
    public static final String DOB = "DOB";
    public static final String EmailID = "EmailID";
    public static final String PhoneNo = "PhoneNo";
    public static final String Address = "Address";
    public static final String User_Type_ID = "User_Type_ID";
    public static final String isValid = "isValid";
    public static final String Assigned_Town = "Assigned_Town";
    public static final String Created_Date = "Created_Date";
    public static final String Updated_Date = "Updated_Date";
    public static final String Token = "Token";
    public static final String IMAGE = "image";
    public static final String Login_ID = "Login_ID";
    public static final String MAC_Address = "MAC_Address";

    public String create_tbl_user_details = " create table tbl_user_details " +
            "            (" +
            "[User_ID] text PRIMARY KEY," +
            "[EMP_ID] text," +
            "Firstname text," +
            "Lastname text," +
            "DOB text," +
            "EmailID text," +
            "PhoneNo integer," +
            "[Address] text," +
            "User_Type_ID text ," +
            "isValid text," +
            "Assigned_Town text," +
            "Created_Date text," +
            "Updated_Date text," +
            "Token text," +
            "Login_ID text," +
            "IMAGE BLOB," +
            "MAC_Address text" +
            "            )";
    // endregion
    //region tbl_update_checker_phone
    public static final String tbl_update_checker_phone = "tbl_update_checker_phone";
    public static final String Updt_Chckr_ID_P = "Updt_Chckr_ID_P";

    public static final String Netwrk_ID = "Netwrk_ID";
    public static final String Netwrk_Change_Flag = "Netwrk_Change_Flag";
    public static final String Netwrk_Date = "Netwrk_Date";
    public static final String Channel_ID = "Channel_ID";
    public static final String Channel_Change_Flag = "Channel_Change_Flag";
    public static final String Channel_Date = "Channel_Date";
    public static final String Placement_Flag = "Placement_Flag";
    public static final String Placement_Date = "Placement_Date";
    public static final String Update_Date = "Update_Date";

    public String
            create_tbl_update_checker_phone = " create table tbl_update_checker_phone" +
            "            (" +
            "                    Updt_Chckr_ID_P text Primary Key NOT NULL," +
            "[User_ID] text References tbl_user_details([User_ID])," +
            "    Netwrk_ID text References tbl_network_details(Network_ID)," +
            "    Netwrk_Change_Flag int," +
            "    Netwrk_Date text," +
            "    Channel_ID text References tbl_channel_master(Channel_ID)," +
            "    Channel_Change_Flag int," +
            "    Channel_Date text," +
            "    Placement_Flag int," +
            "    Placement_Date text," +
            "    Update_Date text" +
            ")";
    //endregion
    //region tbl_network_monitors
    public static final String tbl_network_monitors = "tbl_network_monitors";
    public static final String Ntwrk_Monitor_ID = "Ntwrk_Monitor_ID";
    public static final String Network_ID = "Network_ID";
    public static final String Ground_subscribers = "Ground_subscribers";
    public static final String Technicians = "Technicians";
    public static final String Created_date = "Created_date";
    public static final String Updated_date = "Updated_date";


    public String create_tbl_network_monitors = "   create table tbl_network_monitors" +
            "            (" +
            "                    Ntwrk_Monitor_ID text Primary Key NOT NULL," +
            "                    Network_ID text References tbl_network_details(Network_ID)," +
            "    Ground_subscribers integer," +
            "    Technicians integer," +
            "    Created_date text," +
            "    Updated_date text" +
            ")";
    //endregion
    //region tbl_network_to_channel_mapping
    public static final String tbl_network_to_channel_mapping = "tbl_network_to_channel_mapping";
    public static final String Ntwrk_Chnnl_ID = "Ntwrk_Chnnl_ID";
    public static final String isActive = "isActive";

    public String create_tbl_network_to_channel_mapping = "   create table tbl_network_to_channel_mapping" +
            "            (" +
            "                    Ntwrk_Chnnl_ID text Primary Key NOT NULL," +
            "                    Network_ID text References tbl_network_details(Network_ID)," +
            "    Channel_ID text References tbl_channel_master(Channel_ID)," +
            "    isActive text," +
            "    Created_Date text," +
            "    Updated_Date text" +
            ")";
    //endregion
    //region tbl_network_details
    public static final String tbl_network_details = "tbl_network_details";
    public static final String Network_Name = "Network_Name";
    public static final String MSO_Name = "MSO_Name";
    public static final String Town = "Town";
    public static final String CRN_No = "CRN_No";
    public static final String Area_Mapping_Code = "Area_Mapping_Code";
    public static final String HE_FEED_Flag = "HE_FEED_Flag";
    public static final String IC_NON_IC_Flag = "IC_NON_IC_Flag";
    public static final String Status_ID = "Status_ID";
    public static final String Landing_Page_Flag = "Landing_Page_Flag";
    public static final String Landing_Page_ID = "Landing_Page_ID";

    public String create_tbl_network_details = "    create table tbl_network_details" +
            "            (" +
            "                    Network_ID text Primary Key," +
            "                    Network_Name text," +
            "                    MSO_Name text," +
            "                    Town text," +
            "                    CRN_No text," +
            "                    Area_Mapping_Code text," +
            "                    HE_FEED_Flag text," +
            "[IC_NON_IC_Flag] text," +
            "[Status_ID] text References tbl_status_master([ID])," +
            "[Landing_Page_Flag] text," +
            "            [Landing_Page_ID] text References tbl_channel_master(Channel_ID)," +
            "    Created_Date text," +
            "    Updated_Date text" +
            ")";
    //endregion
    //region tbl_channel_master
    public static final String tbl_channel_master = "tbl_channel_master";
    public static final String Channel_Name = "Channel_Name";
    public static final String SD_HD_Type = "SD_HD_Type";
    public static final String Genre = "Genre";
    public String create_tbl_channel_master = "" +
            "    create table tbl_channel_master" +
            "            (" +
            "                    Channel_ID char(255) Primary Key," +
            "    Channel_Name text," +
            "    SD_HD_Type text," +
            "    Genre text," +
            "    isActive text," +
            "    Created_Date text," +
            "    Updated_Date text" +
            ")";
    //endregion
    //region tbl_tasks
    public static final String tbl_tasks = "tbl_tasks";
    public static final String ID = "ID";
    public static final String Tasks = "Tasks";
    public static final String Comments = "Comments";
    public static final String Daily_Weekly_Flag = "Daily_Weekly_Flag";
    public static final String Year = "Year";
    public String create_tbl_tasks = " create table tbl_tasks" +
            "            (" +
            "                    ID INTEGER primary key autoincrement," +
            "[User_ID] text References tbl_user_details([User_ID])," +
            "    Tasks text," +
            "    Comments text," +
            "    Created_Date text," +
            "    Daily_Weekly_Flag text," +
            "[Year] integer" +
            ")";
    //endregion
    //region tbl_status_master
    public static final String tbl_status_master = "tbl_status_master";
    public static final String Status = "Status";
    public static final String Description = "Description";
    public String create_tbl_status_master = "create table tbl_status_master" +
            "            (" +
            "[ID] text Primary Key NOT NULL," +
            "[Status] text," +
            "[Description] text" +
            "            )";
    //endregion
    //region tbl_network_channel_mapped
    public static final String tbl_network_channel_mapped = "tbl_network_channel_mapped";
    public static final String LCN_No = "LCN_No";
    public static final String Position = "Position";
    public static final String Dual_LCN_Flag = "Dual_LCN_Flag";
    public static final String Triple_LCN_Flag = "Triple_LCN_Flag";
    public static final String Multiple_LCN = "Multiple_LCN";
    public static final String Week_no = "Week_no";
    public static final String On_Call_Site_Flag = "On_Call_Site_Flag";
    public static final String Location = "Location";
    public static final String Entered_By = "Entered_By";
    public static final String Uploaded_At = "Uploaded_At";
    public static final String Uploaded_By = "Uploaded_By";
    public static final String Uploaded_Date = "Uploaded_Date";
    public String create_tbl_network_channel_mapped = "  create table tbl_network_channel_mapped" +
            "            (" +
            "[ID] INTEGER Primary Key NOT NULL," +
            "Network_ID text References tbl_network_details(Network_ID)," +
            "    Network_Name text," +
            "    Channel_Name text," +
            "    LCN_No integer," +
            "    Genre text," +
            "    Position integer," +
            "[Landing_Page_Flag] text," +
            "            [Dual_LCN_Flag] text," +     //doubt
            "            [Triple_LCN_Flag] text," +     //doubt
            "            [Multiple_LCN] integer," +     //doubt
            "            [Status_ID] text References tbl_status_master([ID])," +
            "            [Week_no] integer," +     //doubt
            "            [Year] integer," +            //doubt
            "            [On_Call_Site_Flag] text," +    //doubt
            "            [Location] text," +              //doubt
            "    Entered_By text References tbl_user_details([User_ID])," +
            "    Created_Date text," +
            "    Updated_Date text," +
            "    Uploaded_At text," +
            "    Uploaded_By text References tbl_user_details([User_ID])," +
            "    Uploaded_Date text," +
            "    Comments text" +
            ")";
    //endregion
    //region tbl_network_channel_placement
    public static final String tbl_network_channel_placement = "tbl_network_channel_placement";
    public static final String On_Call_Site = "On_Call_Site";
    public static final String Approved_At = "Approved_At";
    public static final String Approved_By = "Approved_By";
    public static final String Approved_Date = "Approved_Date";
    public static final String Rejected_At = "Rejected_At";
    public static final String Rejected_By = "Rejected_By";
    public static final String Rejected_Date = "Rejected_Date";
    public String create_tbl_network_channel_placement = " create table tbl_network_channel_placement" +
            "            (" +
            "[ID] integer Primary Key autoincrement," +
            "Network_ID text References tbl_network_details(Network_ID)," +
            "    Network_Name text," +
            "    Channel_Name text," +
            "    LCN_No integer," +
            "    Genre text," +
            "    Position integer," +
            "[Landing_Page_Flag] text," +
            "            [Dual_LCN_Flag] text," +   //doubt
            "            [Triple_LCN_Flag] text," +   //doubt
            "            [Multiple_LCN] integer," +    //doubt
            "     Week_no integer," +     //doubt
            "            [Year] integer," +          //doubt
            "    On_Call_Site text," +
            "[Location] text," +
            "    Entered_By text References tbl_user_details([User_ID])," +
            "[Status_ID] text References tbl_status_master([ID])," +
            "    Created_Date text," +
            "    Updated_Date text," +
            "    Approved_At text," +
            "    Approved_By text References tbl_user_details([User_ID])," +
            "    Approved_Date text," +
            "    Rejected_At text," +
            "    Rejected_By text References tbl_user_details([User_ID])," +
            "    Rejected_Date text," +
            "    Comments text" +
            ")";

    //endregion
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_tbl_network_details);
        db.execSQL(create_tbl_channel_master);
        db.execSQL(create_tbl_network_to_channel_mapping);
        db.execSQL(create_tbl_status_master);
        db.execSQL(create_tbl_tasks);
        db.execSQL(create_tbl_network_monitors);
        db.execSQL(create_tbl_network_channel_mapped);
        db.execSQL(create_tbl_network_channel_placement);
        db.execSQL(create_tbl_user_details);
        db.execSQL(create_tbl_update_checker_phone);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // region insert_tbl_user_details
    public void insert_tbl_user_details(String MAC_Addresse, String Login_ID, String Token, String Updated_Date, String Created_Date,
                                        String Assigned_Town, String isValid, String User_Type_ID, String Address, String PhoneNo, String EmailID,
                                        String DOB, String Lastname, String Firstname, String EMP_ID, String User_ID) {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S.SSS");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MAC_Address, MAC_Addresse);
        contentValues.put(DatabaseHelper.Login_ID, Login_ID);
        contentValues.put(DatabaseHelper.Token, Token);
        contentValues.put(DatabaseHelper.Updated_Date, Updated_Date);
        contentValues.put(DatabaseHelper.Created_Date, Created_Date);
        contentValues.put(DatabaseHelper.Assigned_Town, Assigned_Town);
        contentValues.put(DatabaseHelper.isValid, isValid);
        contentValues.put(DatabaseHelper.User_Type_ID, User_Type_ID);
        contentValues.put(DatabaseHelper.Address, Address);
        contentValues.put(DatabaseHelper.PhoneNo, PhoneNo);
        contentValues.put(DatabaseHelper.EmailID, EmailID);
        contentValues.put(DatabaseHelper.DOB, DOB);
        contentValues.put(DatabaseHelper.Lastname, Lastname);
        contentValues.put(DatabaseHelper.Firstname, Firstname);
        contentValues.put(DatabaseHelper.EMP_ID, EMP_ID);
        contentValues.put(DatabaseHelper.User_ID, User_ID);
        try {
            db.insertOrThrow(DatabaseHelper.tbl_user_details, null, contentValues);
        } catch (Exception e) {
            db.execSQL("delete from " + tbl_user_details);
            db.insert(DatabaseHelper.tbl_user_details, null, contentValues);
        }
    }

    //endregion
// region insert_tbl_update_checker_phone
    public void insert_tbl_update_checker_phone(String Updt_Chckr_ID_P, String User_ID, String Netwrk_ID, String Netwrk_Change_Flag, String Netwrk_Date,
                                                String Channel_ID, String Channel_Change_Flag, String Channel_Date, String Placement_Flag, String Placement_Date,
                                                String Update_Date) {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //endregion
// region insert_tbl_tasks
    public void insert_tbl_tasks(String ID, String User_ID, String Tasks, String Created_Date, String Daily_Weekly_Flag,
                                 String Year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID, ID);
        contentValues.put(DatabaseHelper.User_ID, User_ID);
        contentValues.put(DatabaseHelper.Tasks, Tasks);
        contentValues.put(DatabaseHelper.Created_Date, Created_Date);
        contentValues.put(DatabaseHelper.Daily_Weekly_Flag, Daily_Weekly_Flag);
        contentValues.put(DatabaseHelper.Year, Year);
        try {
            db.insertOrThrow(DatabaseHelper.tbl_tasks, null, contentValues);
        } catch (Exception e) {
            db.execSQL("delete from " + tbl_tasks);
            db.insert(DatabaseHelper.tbl_tasks, null, contentValues);
        }
    }

    //endregion
// region insert_tbl_network_channel_placement
    public void insert_tbl_network_channel_placement(
            String ID, String Network_ID, String Network_Name, String Channel_Name, String LCN_No,
            String Genre, String Position, String Landing_Page_Flag, String Dual_LCN_Flag, String Triple_LCN_Flag, String Multiple_LCN,
            String On_Call_Site, String Year, String Location, String Entered_By, String Status_ID, String Created_Date, String Updated_Date,
            String Approved_At, String Approved_By, String Approved_Date, String Rejected_At, String Rejected_By,
            String Rejected_Date, String Comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID, ID);
        contentValues.put(DatabaseHelper.Network_ID, Network_ID);
        contentValues.put(DatabaseHelper.Network_Name, Network_Name);
        contentValues.put(DatabaseHelper.Channel_Name, Channel_Name);
        contentValues.put(DatabaseHelper.LCN_No, LCN_No);
        contentValues.put(DatabaseHelper.Genre, Genre);
        contentValues.put(DatabaseHelper.Position, Position);
        contentValues.put(DatabaseHelper.Landing_Page_Flag, Landing_Page_Flag);
        contentValues.put(DatabaseHelper.Dual_LCN_Flag, Dual_LCN_Flag);
        contentValues.put(DatabaseHelper.Triple_LCN_Flag, Triple_LCN_Flag);
        contentValues.put(DatabaseHelper.Multiple_LCN, Multiple_LCN);
        contentValues.put(DatabaseHelper.Year, Year);
        contentValues.put(DatabaseHelper.On_Call_Site, On_Call_Site);
        contentValues.put(DatabaseHelper.Location, Location);
        contentValues.put(DatabaseHelper.Entered_By, Entered_By);
        contentValues.put(DatabaseHelper.Status_ID, Status_ID);
        contentValues.put(DatabaseHelper.Created_Date, Created_Date);
        contentValues.put(DatabaseHelper.Updated_Date, Updated_Date);
        contentValues.put(DatabaseHelper.Approved_At, Approved_At);
        contentValues.put(DatabaseHelper.Approved_By, Approved_By);
        contentValues.put(DatabaseHelper.Approved_Date, Approved_Date);
        contentValues.put(DatabaseHelper.Rejected_At, Rejected_At);
        contentValues.put(DatabaseHelper.Rejected_By, Rejected_By);
        contentValues.put(DatabaseHelper.Rejected_Date, Rejected_Date);
        contentValues.put(DatabaseHelper.Comments, Comments);
        try {
            db.insertOrThrow(DatabaseHelper.tbl_network_channel_placement, null, contentValues);
        } catch (Exception e) {
            db.execSQL("delete from " + tbl_network_channel_placement);
            db.insert(DatabaseHelper.tbl_network_channel_placement, null, contentValues);
        }
    }
    //endregion
//// region inserttbl_network_monitors
//    public void inserttbl_network_monitors(String Ntwrk_Monitor_ID, String Network_ID, String Ground_subscribers, String Technicians, String Created_date,
//                                           String Updated_date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//    }
//
//    //endregion
//// region insert_tbl_status_master
//    public void insert_tbl_status_master(String ID, String Status, String Description) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//    }
//
//    //endregion
//// region insert_tbl_network_to_channel_mapping
//    public void insert_tbl_network_to_channel_mapping(String Ntwrk_Chnnl_ID, String Network_ID, String Channel_ID, String isActive, String Created_Date,
//                                                      String Updated_Date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//    }
//
//    //endregion
//// region insert_tbl_channel_master
//    public void insert_tbl_channel_master(String Channel_ID, String Channel_Name, String SD_HD_Type, String Genre, String isActive,
//                                          String Created_Date, String Updated_Date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//    }
//
//    //endregion
//// region insert_tbl_network_channel_mapped
//    public void insert_tbl_network_channel_mapped(int ID, String Network_ID, String Network_Name, String Channel_Name, String LCN_No,
//                                                  String Genre, String Position, String Landing_Page_Flag, String Dual_LCN_Flag, String Triple_LCN_Flag, String Multiple_LCN,
//                                                  String Status_Id, String Week_no, String Year, String On_Call_Site_Flag, String Location, String Entered_By, String Created_Date, String Updated_Date,
//                                                  String Uploaded_At, String Uploaded_By, String Uploaded_Date, String Comments) throws ParseException {
////        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S.SSS");
////        Date date = df.parse(Created_Date);
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.ID, ID);
//        contentValues.put(DatabaseHelper.Network_ID, Network_ID);
//        contentValues.put(DatabaseHelper.Network_Name, Network_Name);
//        contentValues.put(DatabaseHelper.Channel_Name, Channel_Name);
//        contentValues.put(DatabaseHelper.LCN_No, LCN_No);
//        contentValues.put(DatabaseHelper.Genre, Genre);
//        contentValues.put(DatabaseHelper.Position, Position);
//        contentValues.put(DatabaseHelper.Landing_Page_Flag, Landing_Page_Flag);
//        contentValues.put(DatabaseHelper.Dual_LCN_Flag, Dual_LCN_Flag);
//        contentValues.put(DatabaseHelper.Triple_LCN_Flag, Triple_LCN_Flag);
//        contentValues.put(DatabaseHelper.Multiple_LCN, Multiple_LCN);
//        contentValues.put(DatabaseHelper.Status_ID, Status_Id);
//        contentValues.put(DatabaseHelper.Week_no, Week_no);
//        contentValues.put(DatabaseHelper.Year, Year);
//        contentValues.put(DatabaseHelper.On_Call_Site_Flag, On_Call_Site_Flag);
//        contentValues.put(DatabaseHelper.Location, Location);
//        contentValues.put(DatabaseHelper.Entered_By, Entered_By);
//        contentValues.put(DatabaseHelper.Created_Date, Created_Date);
//        contentValues.put(DatabaseHelper.Updated_Date, Updated_Date);
//        contentValues.put(DatabaseHelper.Uploaded_At, Uploaded_At);
//        contentValues.put(DatabaseHelper.Uploaded_By, Uploaded_By);
//        contentValues.put(DatabaseHelper.Uploaded_Date, Uploaded_Date);
//        contentValues.put(DatabaseHelper.Comments, Comments);
//        try {
//            db.insertOrThrow(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
//        } catch (Exception e) {
//            db.execSQL("delete from " + tbl_network_channel_mapped);
//            db.insert(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
//        }
//    }
//
//    //endregion
//// region insert_tbl_network_details
//    public void insert_tbl_network_details(String Network_ID, String Network_Name, String MSO_Name, String Town, String CRN_No,
//                                           String Area_Mapping_Code, String HE_FEED_Flag, String IC_NON_IC_Flag, String Status_ID, String Landing_Page_Flag,
//                                           String Landing_Page_ID, String Created_Date, String Updated_Date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//    }
////endregion

    public void deleteAllTablesOnLogout() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(tbl_user_details, null, null);
        db.delete(tbl_channel_master, null, null);
        db.delete(tbl_network_channel_mapped, null, null);
        db.delete(tbl_network_channel_placement, null, null);
        db.delete(tbl_network_details, null, null);
        db.delete(tbl_network_monitors, null, null);
        db.delete(tbl_network_to_channel_mapping, null, null);
        db.delete(tbl_status_master, null, null);
        db.delete(tbl_tasks, null, null);
        db.delete(tbl_update_checker_phone, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public Cursor getAllHeNetworks(String str) {
        String TABLE_NAME = str;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        return c;
    }

    public long getUsersCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tbl_user_details);
        db.close();
        return count;
    }

    public Cursor getUserDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_user_details", null);
        return cursor;
    }

    public Cursor getAllData() {
        final String TABLE_NAME = "tbl_user_details";
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{"Login_ID", "Token", "User_ID"},
                null, null, null, null, null);
        return cursor;
    }

    public Cursor getAllChannels() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT distinct [Channel_ID],[Channel_Name] FROM [tbl_channel_master] where isActive='true'", null);
        return res;
    }

    public Cursor getAllGenre() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT distinct Genre FROM [tbl_channel_master] where isActive='true'", null);
        return res;
    }

    public Cursor getAllNetworksByStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select nd.[Network_ID],nd.[Network_Name],sm.[Status],nd.Created_Date ,(select count(ncm.Channel_Name) from tbl_network_channel_mapped as ncm where ncm.Network_ID=nd.Network_ID) as Number_of_channels from tbl_network_channel_mapped  nd left join tbl_status_master sm where sm.ID=nd.Status_ID and nd.Status_ID =  " + "'" + status + "'group by Network_Name", null);
        return res;
    }

    public Cursor getNetworkcount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT [Network_ID] from tbl_network_details group by Network_ID ", null);
        return res;
    }

    public Cursor getAllNetworks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT [Network_ID],[Network_Name],Created_Date from tbl_network_channel_mapped group by Network_ID  order by Network_Name", null);
        return res;
    }

    public Cursor getAllNetworksCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select nd.Network_ID,nd.Network_Name,ifnull(map.Rejected,0) as Rejected,ifnull(map.Completed,0) as Completed,ifnull(map.Submitted,0) as Submitted,ifnull(map.Approved,0) as Approved FROM\n" +
                "tbl_network_details nd left join (select Network_ID,Network_Name, \n" +
                "count(CASE WHEN Status='Rejected' THEN Status END) AS 'Rejected',\n" +
                "count(CASE WHEN Status='Completed' THEN Status END) AS 'Completed',\n" +
                "count(CASE WHEN Status='Submitted' THEN Status END) AS 'Submitted',\n" +
                "count(CASE WHEN Status='Approved' THEN Status END) AS 'Approved' \n" +
                "from (SELECT Network_ID,Network_Name,Status FROM \n" +
                "tbl_network_channel_placement ncp left join tbl_status_master sm on ncp.Status_ID=sm.id \n" +
                "GROUP BY Network_ID,Created_date\t\n" +
                ") as cp GROUP BY Network_ID) as map on nd.Network_ID=map.Network_ID", null);
        return res;
    }
//    select Network_ID,
//    count(CASE WHEN Status_ID='STS0005' THEN Status_ID END) AS 'Rejected',
//    count(CASE WHEN Status_ID='STS0006' THEN Status_ID END) AS 'Completed',
//    count(CASE WHEN Status_ID='STS0003' THEN Status_ID END) AS 'Submitted',
//    count(CASE WHEN Status_ID='STS0004' THEN Status_ID END) AS 'Approved'
//    from (SELECT Network_ID,Status_ID FROM tbl_network_channel_placement GROUP BY Network_ID,Created_date) as cp GROUP BY Network_ID


    public Cursor getHomePageNetworkCount() { // getNetworkCount on home page
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select Count(*) as count from (SELECT [Network_ID],[Network_Name],Created_Date from tbl_network_channel_mapped group by Network_ID  order by Network_Name)", null);
        return res;
    }

    public Cursor getAllNetworksFromPlacement() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT a.[Network_ID],a.[Network_Name],a.Created_Date,b.Status from tbl_network_channel_placement a left join tbl_status_master b On b.ID = a.Status_ID group by a.Created_Date  order by a.Created_Date desc ", null);
        return res;
    }

    public Cursor getNetworksBySearch(String networkName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select nd.[Network_ID],nd.[Network_Name],sm.[Status],nd.Created_Date , (select count(ncm.Channel_Name) from tbl_network_channel_mapped as ncm where ncm.Network_ID=nd.Network_ID) as Number_of_channels from tbl_network_channel_mapped  nd left join tbl_status_master sm where sm.ID=nd.Status_ID and nd.Network_Name like '%" + networkName + "%' group by nd.Network_ID order by nd.Network_Name", null);
        return res;
    }

    public Cursor getNetworksFromPlacementBySearch(String networkName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT a.[Network_ID],a.[Network_Name],a.Created_Date,b.Status from tbl_network_channel_placement a left join tbl_status_master b On b.ID = a.status_id   where Network_Name like '%" + networkName + "%' group by Created_Date  order by a.Created_Date desc ", null);
        return res;
    }

    public Cursor getChannelsFromNetwork(String networkid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT [ID],[Network_ID],[Network_Name],[Channel_Name],[LCN_No],[Genre],[Position],[Landing_Page_Flag],[Dual_LCN_Flag],[Triple_LCN_Flag],[Multiple_LCN],[Year],[On_Call_Site_Flag],[Location],[Entered_By],[Created_Date],[Updated_Date],[Uploaded_At],[Uploaded_By],[Uploaded_Date],[Comments]  FROM [tbl_network_channel_mapped] as ncm where ncm.Network_ID = " + "'" + networkid + "'order by [LCN_No] asc", null);
        return res;
    }

    public Cursor getChannelsFromNetworkFromPlacement(String networkid, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT [ID],[Network_ID],[Network_Name],[Channel_Name],[LCN_No],[Genre],[Position],[Landing_Page_Flag],[Dual_LCN_Flag],[Triple_LCN_Flag],[Multiple_LCN],[Year],[On_Call_Site],[Location],[Entered_By],[Created_Date],[Updated_Date],[Approved_At],[Approved_By],[Approved_Date],[Rejected_At],[Rejected_By],[Rejected_Date],[Comments] FROM [tbl_network_channel_placement] as ncm where ncm.Network_ID = " + "'" + networkid + "'and Created_Date = " + "'" + createdDate + "' order by [LCN_No] asc", null);
        return res;
    }

    public Cursor getChannelsFromNetworkBySearch(String networkid, String channelName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT [ID],[Network_ID],[Network_Name],[Channel_Name],[LCN_No],[Genre],[Position],[Landing_Page_Flag],[Dual_LCN_Flag],[Triple_LCN_Flag],[Multiple_LCN],[Year],[On_Call_Site_Flag],[Location],[Entered_By],[Created_Date],[Updated_Date],[Uploaded_At],[Uploaded_By],[Uploaded_Date],[Comments]  FROM [tbl_network_channel_mapped] as ncm where ncm.Network_ID = " + "'" + networkid + "'and ncm.Channel_Name like '%" + channelName + "%' or ncm.Network_ID = " + "'" + networkid + "' and ncm.LCN_No like '%" + channelName + "%'or ncm.Genre like '%" + channelName + "%'  order by LCN_No", null);
        return res;
    }

    public Cursor getChannelsFromNetworkFromPlacementBySearch(String networkid, String channelName, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT [ID],[Network_ID],[Network_Name],[Channel_Name],[LCN_No],[Genre],[Position],[Landing_Page_Flag],[Dual_LCN_Flag],[Triple_LCN_Flag],[Multiple_LCN],[Year],[On_Call_Site],[Location],[Entered_By],[Created_Date],[Updated_Date],[Approved_At],[Approved_By],[Approved_Date],[Rejected_At],[Rejected_By],[Rejected_Date],[Comments]  FROM [tbl_network_channel_placement] as ncm where ncm.Network_ID = " + "'" + networkid + "'and Created_Date = " + "'" + createdDate + "' and ncm.LCN_No like '%" + channelName + "%' or ncm.Channel_Name like '%" + channelName + "%'or ncm.Genre like '%" + channelName + "%'  order by LCN_No", null);
        return res;
    }

    public boolean updateByLcn(String channelname, String gnr, String prv_lcn, String Chgd_lcn, String pos, String networkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from tbl_network_channel_mapped where LCN_No = " + "'" + Chgd_lcn + "' " + "and Network_ID = " + "'" + networkId + "' ", null);
        if (res.getCount() > 0) {
            return false;
        } else {
            ContentValues cv = new ContentValues();
            ContentValues contentValues = new ContentValues();
            cv.put("LCN_No", Chgd_lcn); //These Fields should be your String values of actual column names
            cv.put("Channel_Name", channelname);
            cv.put("Position", pos);
            cv.put("Genre", gnr);
            cv.put("Status_ID", "STS0002");
            contentValues.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_mapped, cv, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + prv_lcn + "'", null);
            db.update(tbl_network_channel_mapped, contentValues, "Network_ID = " + "'" + networkId + "'", null);
            return true;
        }
    }

    public boolean updateByLcnFromPlacement(String channelname, String gnr, String prv_lcn, String Chgd_lcn, String pos, String networkId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String statusId;
        Cursor cursor = db.rawQuery("select * from tbl_network_channel_placement where Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                statusId = cursor.getString(cursor.getColumnIndex("Status_ID"));
                cursor.moveToNext();
            }
        }
        Cursor res = db.rawQuery("select * from tbl_network_channel_placement where LCN_No = " + "'" + Chgd_lcn + "' " + "and Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        if (res.getCount() > 0) {
            return false;
        } else {
            ContentValues cv = new ContentValues();
            ContentValues contentValues = new ContentValues();
            cv.put("LCN_No", Chgd_lcn); //These Fields should be your String values of actual column names
            cv.put("Channel_Name", channelname);
            cv.put("Position", pos);
            cv.put("Genre", gnr);
            cv.put("Status_ID", "STS0002");
            contentValues.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + prv_lcn + "'and Created_Date = " + "'" + createdDate + "' ", null);
            db.update(tbl_network_channel_placement, contentValues, "Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
            return true;
        }
    }

    public void updateByCnlPosGen(String channelname, String gnr, String prv_lcn, String Chgd_lcn, String pos, String networkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ContentValues contentValues = new ContentValues();
        cv.put("LCN_No", Chgd_lcn); //These Fields should be your String values of actual column names
        cv.put("Channel_Name", channelname);
        cv.put("Position", pos);
        cv.put("Genre", gnr);
        cv.put("Status_ID", "STS0002");
        contentValues.put("Status_ID", "STS0002");
        db.update(tbl_network_channel_mapped, cv, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + Chgd_lcn + "'", null);
        db.update(tbl_network_channel_mapped, contentValues, "Network_ID = " + "'" + networkId + "'", null);
    }

    public void updateByCnlPosGenFromPlacement(String channelname, String gnr, String prv_lcn, String Chgd_lcn, String pos, String networkId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ContentValues contentValues = new ContentValues();
        cv.put("LCN_No", Chgd_lcn); //These Fields should be your String values of actual column names
        cv.put("Channel_Name", channelname);
        cv.put("Position", pos);
        cv.put("Genre", gnr);
        cv.put("Status_ID", "STS0002");
        contentValues.put("Status_ID", "STS0002");
        db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + Chgd_lcn + "'and Created_Date = " + "'" + createdDate + "' ", null);
        db.update(tbl_network_channel_placement, contentValues, "Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
    }

    public boolean insert_newChannel_tbl_network_channel_mapped(String Network_ID, String Network_Name, String Channel_Name, String LCN_No,
                                                                String Genre, String Position, String Landing_Page_Flag, String Dual_LCN_Flag, String Triple_LCN_Flag, int Multiple_LCN,
                                                                int Year, String On_Call_Site_Flag, String Location, String Entered_By, String Created_Date, String Updated_Date,
                                                                String Uploaded_At, String Uploaded_By, String Uploaded_Date, String Comments) {
        try {
            int Id = 0;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from tbl_network_channel_mapped where LCN_No = " + "'" + LCN_No + "' " + "and Network_ID = " + "'" + Network_ID + "' ", null);
            if (res.getCount() > 0) {
                return false;
            } else {
                Cursor id = db.rawQuery("select max(ID) as id from tbl_network_channel_mapped", null);
                if (id.moveToFirst()) {
                    Id = Integer.parseInt(id.getString(id.getColumnIndex("id"))) + 1;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.ID, Id);
                contentValues.put(DatabaseHelper.Network_ID, Network_ID);
                contentValues.put(DatabaseHelper.Network_Name, Network_Name);
                contentValues.put(DatabaseHelper.Channel_Name, Channel_Name);
                contentValues.put(DatabaseHelper.LCN_No, LCN_No);
                contentValues.put(DatabaseHelper.Genre, Genre);
                contentValues.put(DatabaseHelper.Position, Position);
                contentValues.put(DatabaseHelper.Landing_Page_Flag, Landing_Page_Flag);
                contentValues.put(DatabaseHelper.Dual_LCN_Flag, Dual_LCN_Flag);
                contentValues.put(DatabaseHelper.Triple_LCN_Flag, Triple_LCN_Flag);
                contentValues.put(DatabaseHelper.Multiple_LCN, Multiple_LCN);
                contentValues.put(DatabaseHelper.Year, Year);
                contentValues.put(DatabaseHelper.On_Call_Site_Flag, On_Call_Site_Flag);
                contentValues.put(DatabaseHelper.Location, Location);
                contentValues.put(DatabaseHelper.Entered_By, Entered_By);
                contentValues.put(DatabaseHelper.Created_Date, Created_Date);
                contentValues.put(DatabaseHelper.Updated_Date, Updated_Date);
                contentValues.put(DatabaseHelper.Uploaded_At, Uploaded_At);
                contentValues.put(DatabaseHelper.Uploaded_By, Uploaded_By);
                contentValues.put(DatabaseHelper.Uploaded_Date, Uploaded_Date);
                contentValues.put(DatabaseHelper.Comments, Comments);
                try {
                    db.insertOrThrow(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert_newChannel_tbl_network_channel_mappedFromPlacement(String Network_ID, String Network_Name, String Channel_Name, String LCN_No,
                                                                             String Genre, String Position, String Landing_Page_Flag, String Dual_LCN_Flag, String Triple_LCN_Flag, int Multiple_LCN, int Week_no,
                                                                             int Year, String On_Call_Site_Flag, String Location, String Entered_By, String Created_Date, String Updated_Date,
                                                                             String Approved_At, String Approved_By, String Approved_Date, String Rejected_At, String Rejected_By, String Rejected_Date, String Comments) {
        try {
            int Id = 0;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from tbl_network_channel_placement where LCN_No = " + "'" + LCN_No + "' " + "and Network_ID = " + "'" + Network_ID + "'Created_Date = " + "'" + Created_Date + "' ", null);
            if (res.getCount() > 0) {
                return false;
            } else {
                Cursor id = db.rawQuery("select max(ID) as id from tbl_network_channel_placement", null);
                if (id.moveToFirst()) {
                    Id = Integer.parseInt(id.getString(id.getColumnIndex("id"))) + 1;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.ID, Id);
                contentValues.put(DatabaseHelper.Network_ID, Network_ID);
                contentValues.put(DatabaseHelper.Network_Name, Network_Name);
                contentValues.put(DatabaseHelper.Channel_Name, Channel_Name);
                contentValues.put(DatabaseHelper.LCN_No, LCN_No);
                contentValues.put(DatabaseHelper.Genre, Genre);
                contentValues.put(DatabaseHelper.Position, Position);
                contentValues.put(DatabaseHelper.Landing_Page_Flag, Landing_Page_Flag);
                contentValues.put(DatabaseHelper.Dual_LCN_Flag, Dual_LCN_Flag);
                contentValues.put(DatabaseHelper.Triple_LCN_Flag, Triple_LCN_Flag);
                contentValues.put(DatabaseHelper.Multiple_LCN, Multiple_LCN);
                contentValues.put(DatabaseHelper.Week_no, Week_no);
                contentValues.put(DatabaseHelper.Year, Year);
                contentValues.put(DatabaseHelper.On_Call_Site_Flag, On_Call_Site_Flag);
                contentValues.put(DatabaseHelper.Location, Location);
                contentValues.put(DatabaseHelper.Entered_By, Entered_By);
                contentValues.put(DatabaseHelper.Created_Date, Created_Date);
                contentValues.put(DatabaseHelper.Updated_Date, Updated_Date);
                contentValues.put(DatabaseHelper.Approved_At, Approved_At);
                contentValues.put(DatabaseHelper.Approved_By, Approved_By);
                contentValues.put(DatabaseHelper.Approved_Date, Approved_Date);
                contentValues.put(DatabaseHelper.Rejected_At, Rejected_At);
                contentValues.put(DatabaseHelper.Rejected_By, Rejected_By);
                contentValues.put(DatabaseHelper.Rejected_Date, Rejected_Date);
                contentValues.put(DatabaseHelper.Comments, Comments);
                try {
                    db.insertOrThrow(DatabaseHelper.tbl_network_channel_placement, null, contentValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteChannelFromNetwork(String network_ID, String deletelcn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_network_channel_mapped, "LCN_No = " + "'" + deletelcn + "' " + "and Network_ID = " + "'" + network_ID + "' ", null);
        return true;
    }

    public boolean deleteChannelFromNetworkFromPlacement(String network_ID, String deletelcn, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_network_channel_placement, "LCN_No = " + "'" + deletelcn + "' " + "and Network_ID = " + "'" + network_ID + "'and Created_Date = " + "'" + createdDate + "' ", null);
        return true;
    }

    public Cursor getMonitoringPoint(String networkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_monitors where Network_ID = " + "'" + networkId + "' ", null);
        return res;
    }

    public boolean setMonitoringPoint(String networkId, String tec, String grd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_monitors where Network_ID = " + "'" + networkId + "' ", null);
        if (res.getCount() > 0) {
            ContentValues cv = new ContentValues();
            cv.put("Technicians", tec);
            cv.put("Ground_subscribers", grd);
            ContentValues conV = new ContentValues();
            conV.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_mapped, conV, "Network_ID = " + "'" + networkId + "'", null);
            db.update(tbl_network_monitors, cv, "Network_ID = " + "'" + networkId + "' ", null);
            return true;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.Ntwrk_Monitor_ID, "M" + networkId);
            contentValues.put(DatabaseHelper.Network_ID, networkId);
            contentValues.put(DatabaseHelper.Ground_subscribers, grd);
            contentValues.put(DatabaseHelper.Technicians, tec);
            try {
                db.insertOrThrow(DatabaseHelper.tbl_network_monitors, null, contentValues);
            } catch (Exception e) {
            }
            return true;
        }
    }

    public Cursor getLandingPage(String networkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_mapped where Landing_Page_Flag = 'true' and Network_ID = " + "'" + networkId + "' ", null);
        return res;
    }

    public Cursor getLandingPageFromPlacement(String networkId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_placement where Landing_Page_Flag = 'true' and Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        return res;
    }

    public Cursor getDataByLandingPage(String networkId, String lcn) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_mapped where LCN_No = " + "'" + lcn + "' " + "and Network_ID = " + "'" + networkId + "' ", null);
        return res;
    }

    public Cursor getDataByLandingPageFromPlacement(String networkId, String lcn, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_placement where LCN_No = " + "'" + lcn + "' " + "and Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        return res;
    }

    public void setLandingPage(String networkId, String lcn) {
        if (lcn.equals("")) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("Landing_Page_Flag", "false");
            cv.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_mapped, cv, "Network_ID = " + "'" + networkId + "'", null);
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("Landing_Page_Flag", "false");
            cv.put("Status_ID", "STS0002");
            ContentValues contentValues = new ContentValues();
            contentValues.put("Landing_Page_Flag", "true");
            contentValues.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_mapped, cv, "Network_ID = " + "'" + networkId + "'", null);
            db.update(tbl_network_channel_mapped, contentValues, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + lcn + "'", null);
        }
    }

    public void setLandingPageFromPlacement(String networkId, String lcn, String createdDate) {
        if (lcn.equals("")) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("Landing_Page_Flag", "false");
            cv.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("Landing_Page_Flag", "false");
            cv.put("Status_ID", "STS0002");
            ContentValues contentValues = new ContentValues();
            contentValues.put("Landing_Page_Flag", "true");
            contentValues.put("Status_ID", "STS0002");
            db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
            db.update(tbl_network_channel_placement, contentValues, "Network_ID = " + "'" + networkId + "' and LCN_No = " + "'" + lcn + "'and Created_Date = " + "'" + createdDate + "' ", null);
        }
    }

    public String getUserId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String User_ID = "";
        Cursor cursor = db.rawQuery("select * from tbl_user_details", null);
        if (cursor.moveToFirst()) {
            User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
        }
        return User_ID;
    }

    public boolean submitNetwork(String networkId, String comments, String onCallSite) {
        String User_ID = getUserId();
        String Created_Date = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S");
        SimpleDateFormat df1 = new SimpleDateFormat("YYYY-MM-d");
        try {
            Cursor cursor = db.rawQuery("Select Created_Date from tbl_network_channel_mapped where Network_ID = " + "'" + networkId + "' group by Network_ID ", null);
            if (cursor.moveToFirst()) {
                Created_Date = cursor.getString(cursor.getColumnIndex("Created_Date")).split(" ")[0];
            }
//            String date1  = df1.format(Created_Date);
            if (df1.format(date).equals(Created_Date)) {
                return false;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContentValues cv = new ContentValues();
        cv.put("Comments", comments);
        cv.put("On_Call_Site_Flag", onCallSite);
        cv.put("Status_ID", "STS0003");
        cv.put("Entered_By", User_ID);
        cv.put("Updated_Date", df.format(date));
        cv.put("Created_Date", df.format(date));
        db.update(tbl_network_channel_mapped, cv, "Network_ID = " + "'" + networkId + "'", null);
        db.delete(tbl_network_channel_placement, null, null);
        db.execSQL("insert into tbl_network_channel_placement (ID,Network_ID,Network_Name,Channel_Name,LCN_No,Genre,Position,Landing_Page_Flag,Dual_LCN_Flag,Triple_LCN_Flag,Multiple_LCN,Year,Status_ID,Week_no,On_Call_Site,Location,Entered_By,Created_date,Updated_Date,Comments) select ID,Network_ID,Network_Name,Channel_Name,LCN_No,Genre,Position,Landing_Page_Flag,Dual_LCN_Flag,Triple_LCN_Flag,Multiple_LCN,Year,Status_ID,Week_no,On_Call_Site_Flag,Location,Entered_By,Created_date,Updated_Date,Comments from tbl_network_channel_mapped where Network_ID = " + "'" + networkId + "'");
        return true;
    }

    public boolean submitNetworkFromPlacement(String networkId, String comments, String onCallSite, String createdDate) {
        String User_ID = getUserId();
        String Created_Date = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S");
        ContentValues cv = new ContentValues();
        cv.put("Comments", comments);
        cv.put("On_Call_Site", onCallSite);
        cv.put("Status_ID", "STS0003");
        cv.put("Entered_By", User_ID);
        cv.put("Updated_Date", df.format(date));
        db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + networkId + "'and Created_Date = " + "'" + createdDate + "' ", null);
        return true;
    }

    public Cursor tbl_network_channel_mapping() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_placement", null);
        return res;
    }

    public Cursor tbl_network_channel_placement(String network_ID, String created_Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from tbl_network_channel_placement where Network_ID = " + "'" + network_ID + "'and Created_Date = " + "'" + created_Date + "' ", null);
        return res;
    }

    public Cursor getCurrentWeekName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ('Week '||max(Week_no)||' - '||Year) as Week_Year from tbl_network_channel_mapped where [Year] = (select max([Year]) from tbl_network_channel_mapped)", null);
        return res;
    }

    public Cursor getTownName(String network_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Town From tbl_network_details where Network_ID =  " + "'" + network_ID + "' ", null);
        return cursor;
    }

    public boolean getMappedResponse(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            networkchannelmappedarray = jsonArray.getJSONArray(0);
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                for (int i = 0; i < networkchannelmappedarray.length(); i++) {
                    networkchannelmappedobject = networkchannelmappedarray.getJSONObject(i);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.ID, networkchannelmappedobject.getInt("ID"));
                    contentValues.put(DatabaseHelper.Network_ID, networkchannelmappedobject.getString("Network_ID"));
                    contentValues.put(DatabaseHelper.Network_Name, networkchannelmappedobject.getString("Network_Name"));
                    contentValues.put(DatabaseHelper.Channel_Name, networkchannelmappedobject.getString("Channel_Name"));
                    contentValues.put(DatabaseHelper.LCN_No, networkchannelmappedobject.getString("LCN_No"));
                    contentValues.put(DatabaseHelper.Genre, networkchannelmappedobject.getString("Genre"));
                    contentValues.put(DatabaseHelper.Position, networkchannelmappedobject.getString("Position"));
                    contentValues.put(DatabaseHelper.Landing_Page_Flag, networkchannelmappedobject.getString("Landing_Page_Flag"));
                    contentValues.put(DatabaseHelper.Dual_LCN_Flag, networkchannelmappedobject.getString("Dual LCN Flag"));
                    contentValues.put(DatabaseHelper.Triple_LCN_Flag, networkchannelmappedobject.getString("Triple LCN Flag"));
                    contentValues.put(DatabaseHelper.Multiple_LCN, networkchannelmappedobject.getString("Multiple LCN"));
                    contentValues.put(DatabaseHelper.Status_ID, networkchannelmappedobject.getString("Status_Id"));
                    contentValues.put(DatabaseHelper.Week_no, networkchannelmappedobject.getString("Week_no"));
                    contentValues.put(DatabaseHelper.Year, networkchannelmappedobject.getString("Year"));
                    contentValues.put(DatabaseHelper.On_Call_Site_Flag, networkchannelmappedobject.getString("On_Call_Site Flag"));
                    contentValues.put(DatabaseHelper.Location, networkchannelmappedobject.getString("Location"));
                    contentValues.put(DatabaseHelper.Entered_By, networkchannelmappedobject.getString("Entered_By"));
                    contentValues.put(DatabaseHelper.Created_Date, networkchannelmappedobject.getString("Created_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Updated_Date, networkchannelmappedobject.getString("Updated_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Uploaded_At, networkchannelmappedobject.getString("Uploaded_At"));
                    contentValues.put(DatabaseHelper.Uploaded_By, networkchannelmappedobject.getString("Uploaded_By"));
                    contentValues.put(DatabaseHelper.Uploaded_Date, networkchannelmappedobject.getString("Uploaded_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Comments, networkchannelmappedobject.getString("Comments"));
                    try {
                        db.insertOrThrow(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
                    } catch (Exception e) {
                        db.insert(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getResponse(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            channelmasterarray = jsonArray.getJSONArray(0);
            statusmasterarray = jsonArray.getJSONArray(1);
            networkdetailsarray = jsonArray.getJSONArray(3);
            networkchannelmappedarray = jsonArray.getJSONArray(5);
            networkchannelmappingarray = jsonArray.getJSONArray(4);//null
            networkmonitorsarray = jsonArray.getJSONArray(6);//null
            updatecheckerarray = jsonArray.getJSONArray(2);//null
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                try {
                    for (int i = 0; i < channelmasterarray.length(); i++) {
                        channelmasterobject = channelmasterarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.Channel_ID, channelmasterobject.getString("Channel_ID"));
                        contentValues.put(DatabaseHelper.Channel_Name, channelmasterobject.getString("Channel_Name"));
                        contentValues.put(DatabaseHelper.SD_HD_Type, channelmasterobject.getString("SD_HD_Type"));
                        contentValues.put(DatabaseHelper.Genre, channelmasterobject.getString("Genre"));
                        contentValues.put(DatabaseHelper.isActive, channelmasterobject.getString("isActive"));
                        contentValues.put(DatabaseHelper.Created_Date, channelmasterobject.getString("Created_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Updated_Date, channelmasterobject.getString("Updated_Date").replace('T', ' '));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_channel_master, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_channel_master);
                            db.insert(DatabaseHelper.tbl_channel_master, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < statusmasterarray.length(); i++) {
                        statusmasterobject = statusmasterarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.ID, statusmasterobject.getString("ID"));
                        contentValues.put(DatabaseHelper.Status, statusmasterobject.getString("Status"));
                        contentValues.put(DatabaseHelper.Description, statusmasterobject.getString("Description"));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_status_master, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_status_master);
                            db.insert(DatabaseHelper.tbl_status_master, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < updatecheckerarray.length(); i++) {
                        updatecheckerobject = updatecheckerarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.Updt_Chckr_ID_P, updatecheckerobject.getString("Updt_Chckr_ID_P"));
                        contentValues.put(DatabaseHelper.User_ID, updatecheckerobject.getString("User_ID"));
                        contentValues.put(DatabaseHelper.Netwrk_ID, updatecheckerobject.getString("Netwrk_ID"));
                        contentValues.put(DatabaseHelper.Netwrk_Change_Flag, updatecheckerobject.getString("Netwrk_Change_Flag"));
                        contentValues.put(DatabaseHelper.Netwrk_Date, updatecheckerobject.getString("Netwrk_Date"));
                        contentValues.put(DatabaseHelper.Channel_ID, updatecheckerobject.getString("Channel_ID"));
                        contentValues.put(DatabaseHelper.Channel_Change_Flag, updatecheckerobject.getString("Channel_Change_Flag"));
                        contentValues.put(DatabaseHelper.Channel_Date, updatecheckerobject.getString("Channel_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Placement_Flag, updatecheckerobject.getString("Placement_Flag"));
                        contentValues.put(DatabaseHelper.Placement_Date, updatecheckerobject.getString("Placement_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Update_Date, updatecheckerobject.getString("Update_Date").replace('T', ' '));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_update_checker_phone, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_update_checker_phone);
                            db.insert(DatabaseHelper.tbl_update_checker_phone, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < networkdetailsarray.length(); i++) {
                        networkdetailsobject = networkdetailsarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.Network_ID, networkdetailsobject.getString("Network_ID"));
                        contentValues.put(DatabaseHelper.Network_Name, networkdetailsobject.getString("Network_Name"));
                        contentValues.put(DatabaseHelper.MSO_Name, networkdetailsobject.getString("MSO_Name"));
                        contentValues.put(DatabaseHelper.Town, networkdetailsobject.getString("Town"));
                        contentValues.put(DatabaseHelper.CRN_No, networkdetailsobject.getString("CRN_No"));
                        contentValues.put(DatabaseHelper.Area_Mapping_Code, networkdetailsobject.getString("Area_Mapping_Code"));
                        contentValues.put(DatabaseHelper.HE_FEED_Flag, networkdetailsobject.getString("HE_FEED_Flag"));
                        contentValues.put(DatabaseHelper.IC_NON_IC_Flag, networkdetailsobject.getString("IC_NON_IC Flag"));
                        contentValues.put(DatabaseHelper.Status_ID, networkdetailsobject.getString("Status_ID"));
                        contentValues.put(DatabaseHelper.Landing_Page_Flag, networkdetailsobject.getString("Landing_Page_Flag"));
                        contentValues.put(DatabaseHelper.Landing_Page_ID, networkdetailsobject.getString("Landing_Page_ID"));
                        contentValues.put(DatabaseHelper.Created_Date, networkdetailsobject.getString("Created_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Updated_Date, networkdetailsobject.getString("Updated_Date").replace('T', ' '));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_network_details, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_network_details);
                            db.insert(DatabaseHelper.tbl_network_details, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < networkchannelmappingarray.length(); i++) {
                        networkchannelmappingobject = networkchannelmappingarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.Ntwrk_Chnnl_ID, networkchannelmappingobject.getString("Ntwrk_Chnnl_ID"));
                        contentValues.put(DatabaseHelper.Network_ID, networkchannelmappingobject.getString("Network_ID"));
                        contentValues.put(DatabaseHelper.Channel_ID, networkchannelmappingobject.getString("Channel_ID"));
                        contentValues.put(DatabaseHelper.isActive, networkchannelmappingobject.getString("isActive"));
                        contentValues.put(DatabaseHelper.Created_Date, networkchannelmappingobject.getString("Created_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Updated_Date, networkchannelmappingobject.getString("Updated_Date").replace('T', ' '));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_network_to_channel_mapping, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_network_to_channel_mapping);
                            db.insert(DatabaseHelper.tbl_network_to_channel_mapping, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < networkchannelmappedarray.length(); i++) {
                        networkchannelmappedobject = networkchannelmappedarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.ID, networkchannelmappedobject.getInt("ID"));
                        contentValues.put(DatabaseHelper.Network_ID, networkchannelmappedobject.getString("Network_ID"));
                        contentValues.put(DatabaseHelper.Network_Name, networkchannelmappedobject.getString("Network_Name"));
                        contentValues.put(DatabaseHelper.Channel_Name, networkchannelmappedobject.getString("Channel_Name"));
                        contentValues.put(DatabaseHelper.LCN_No, networkchannelmappedobject.getString("LCN_No"));
                        contentValues.put(DatabaseHelper.Genre, networkchannelmappedobject.getString("Genre"));
                        contentValues.put(DatabaseHelper.Position, networkchannelmappedobject.getString("Position"));
                        contentValues.put(DatabaseHelper.Landing_Page_Flag, networkchannelmappedobject.getString("Landing_Page_Flag"));
                        contentValues.put(DatabaseHelper.Dual_LCN_Flag, networkchannelmappedobject.getString("Dual LCN Flag"));
                        contentValues.put(DatabaseHelper.Triple_LCN_Flag, networkchannelmappedobject.getString("Triple LCN Flag"));
                        contentValues.put(DatabaseHelper.Multiple_LCN, networkchannelmappedobject.getString("Multiple LCN"));
                        contentValues.put(DatabaseHelper.Status_ID, networkchannelmappedobject.getString("Status_Id"));
                        contentValues.put(DatabaseHelper.Week_no, networkchannelmappedobject.getString("Week_no"));
                        contentValues.put(DatabaseHelper.Year, networkchannelmappedobject.getString("Year"));
                        contentValues.put(DatabaseHelper.On_Call_Site_Flag, networkchannelmappedobject.getString("On_Call_Site Flag"));
                        contentValues.put(DatabaseHelper.Location, networkchannelmappedobject.getString("Location"));
                        contentValues.put(DatabaseHelper.Entered_By, networkchannelmappedobject.getString("Entered_By"));
                        contentValues.put(DatabaseHelper.Created_Date, networkchannelmappedobject.getString("Created_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Updated_Date, networkchannelmappedobject.getString("Updated_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Uploaded_At, networkchannelmappedobject.getString("Uploaded_At"));
                        contentValues.put(DatabaseHelper.Uploaded_By, networkchannelmappedobject.getString("Uploaded_By"));
                        contentValues.put(DatabaseHelper.Uploaded_Date, networkchannelmappedobject.getString("Uploaded_Date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Comments, networkchannelmappedobject.getString("Comments"));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from tbl_network_channel_mapped");
                            db.insert(DatabaseHelper.tbl_network_channel_mapped, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    for (int i = 0; i < networkmonitorsarray.length(); i++) {
                        networkmonitorsobject = networkmonitorsarray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.Ntwrk_Monitor_ID, networkmonitorsobject.getString("Ntwrk_Monitor_ID"));
                        contentValues.put(DatabaseHelper.Network_ID, networkmonitorsobject.getString("Network_ID"));
                        contentValues.put(DatabaseHelper.Ground_subscribers, networkmonitorsobject.getString("Ground_subscribers"));
                        contentValues.put(DatabaseHelper.Technicians, networkmonitorsobject.getString("Technicians"));
                        contentValues.put(DatabaseHelper.Created_date, networkmonitorsobject.getString("Created_date").replace('T', ' '));
                        contentValues.put(DatabaseHelper.Updated_date, networkmonitorsobject.getString("Updated_date").replace('T', ' '));
                        try {
                            db.insertOrThrow(DatabaseHelper.tbl_network_monitors, null, contentValues);
                        } catch (Exception e) {
                            db.execSQL("delete from " + tbl_network_monitors);
                            db.insert(DatabaseHelper.tbl_network_monitors, null, contentValues);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
//                try {
//                    for (int i = 0; i < networkcountarray.length(); i++) {
//                        networkcountobject = networkcountarray.getJSONObject(i);
//
//
////                        ContentValues contentValues = new ContentValues();
////                        contentValues.put(DatabaseHelper.Ntwrk_Monitor_ID, networkmonitorsobject.getString("Ntwrk_Monitor_ID"));
////                        contentValues.put(DatabaseHelper.Network_ID, networkmonitorsobject.getString("Network_ID"));
////                        contentValues.put(DatabaseHelper.Ground_subscribers, networkmonitorsobject.getString("Ground_subscribers"));
////                        contentValues.put(DatabaseHelper.Technicians, networkmonitorsobject.getString("Technicians"));
////                        contentValues.put(DatabaseHelper.Created_date, networkmonitorsobject.getString("Created_date").replace('T', ' '));
////                        contentValues.put(DatabaseHelper.Updated_date, networkmonitorsobject.getString("Updated_date").replace('T', ' '));
////                        try {
////                            db.insertOrThrow(DatabaseHelper.tbl_network_monitors, null, contentValues);
////                        } catch (Exception e) {
////                            db.execSQL("delete from " + tbl_network_monitors);
////                            db.insert(DatabaseHelper.tbl_network_monitors, null, contentValues);
////                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                db.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getPlacement(String jsondata) throws JSONException {
        JSONArray networkchannelplacementarray = null;
        JSONObject networkchannelplacementobject = null;
        try {
            JSONArray jsonArray = new JSONArray(jsondata);
            networkchannelplacementarray = jsonArray.getJSONArray(0);
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("delete from tbl_network_channel_placement");
            try {
                for (int i = 0; i < networkchannelplacementarray.length(); i++) {
                    networkchannelplacementobject = networkchannelplacementarray.getJSONObject(i);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.ID, networkchannelplacementobject.getInt("ID"));
                    contentValues.put(DatabaseHelper.Network_ID, networkchannelplacementobject.getString("Network_ID"));
                    contentValues.put(DatabaseHelper.Network_Name, networkchannelplacementobject.getString("Network_Name"));
                    contentValues.put(DatabaseHelper.Channel_Name, networkchannelplacementobject.getString("Channel_Name"));
                    contentValues.put(DatabaseHelper.LCN_No, networkchannelplacementobject.getString("LCN_No"));
                    contentValues.put(DatabaseHelper.Genre, networkchannelplacementobject.getString("Genre"));
                    contentValues.put(DatabaseHelper.Position, networkchannelplacementobject.getString("Position"));
                    contentValues.put(DatabaseHelper.Landing_Page_Flag, networkchannelplacementobject.getString("Landing_Page_Flag"));
                    contentValues.put(DatabaseHelper.Dual_LCN_Flag, networkchannelplacementobject.getString("Dual_LCN_Flag"));
                    contentValues.put(DatabaseHelper.Triple_LCN_Flag, networkchannelplacementobject.getString("Triple_LCN_Flag"));
                    contentValues.put(DatabaseHelper.Multiple_LCN, networkchannelplacementobject.getString("Multiple_LCN"));
                    contentValues.put(DatabaseHelper.Status_ID, networkchannelplacementobject.getString("Status_ID"));
                    contentValues.put(DatabaseHelper.Week_no, networkchannelplacementobject.getString("Week_no"));
                    contentValues.put(DatabaseHelper.Year, networkchannelplacementobject.getString("Year"));
                    contentValues.put(DatabaseHelper.On_Call_Site, networkchannelplacementobject.getString("On_Call_Site"));
                    contentValues.put(DatabaseHelper.Location, networkchannelplacementobject.getString("Location"));
                    contentValues.put(DatabaseHelper.Entered_By, networkchannelplacementobject.getString("Entered_By"));
                    contentValues.put(DatabaseHelper.Created_Date, networkchannelplacementobject.getString("Created_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Updated_Date, networkchannelplacementobject.getString("Updated_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Approved_At, networkchannelplacementobject.getString("Approved_At"));
                    contentValues.put(DatabaseHelper.Approved_By, networkchannelplacementobject.getString("Approved_By"));
                    contentValues.put(DatabaseHelper.Approved_Date, networkchannelplacementobject.getString("Approved_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Rejected_At, networkchannelplacementobject.getString("Rejected_At"));
                    contentValues.put(DatabaseHelper.Rejected_By, networkchannelplacementobject.getString("Rejected_By"));
                    contentValues.put(DatabaseHelper.Rejected_Date, networkchannelplacementobject.getString("Rejected_Date").replace('T', ' '));
                    contentValues.put(DatabaseHelper.Comments, networkchannelplacementobject.getString("Comments"));
                    try {
                        db.insertOrThrow(DatabaseHelper.tbl_network_channel_placement, null, contentValues);
                    } catch (Exception e) {
                        db.execSQL("delete from tbl_network_channel_placement");
                        db.insert(DatabaseHelper.tbl_network_channel_placement, null, contentValues);
                    }
                }
                db.setTransactionSuccessful();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                db.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePlacementStatus(String network_ID, String created_Date, String LastStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Network_ID", network_ID);
        cv.put("Created_Date", created_Date);
        cv.put("Status_ID", LastStatus);
        db.update(tbl_network_channel_placement, cv, "Network_ID = " + "'" + network_ID + "'and Created_Date = " + "'" + created_Date + "' ", null);
    }

    public void insertImage(byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IMAGE, imageBytes);
        db.insert(tbl_user_details, null, cv);
    }
}