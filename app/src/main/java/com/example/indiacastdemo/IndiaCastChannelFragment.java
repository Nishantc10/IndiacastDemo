package com.example.indiacastdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.IndiaCastChannel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class IndiaCastChannelFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<IndiaCastChannel> lst_indiacast_channel;
    private ArrayList<JSONObject> tbl_network_channel_placement = new ArrayList<>();
    private ArrayList<JSONObject> tbl_placement_indiacast_channels_details_lst = new ArrayList<>();
    private boolean IStatusFlag = false;
    BubbleScrollBar scrollBar;
    String iStatusValue;
    TextView txt_comment;
    Bundle bundle;
    String networkId, Login_ID, Token, User_ID, Network_ID, Channel_name, LCN_No = null;
    RadioGroup radio_group;
    RadioButton radio_Button;
    DatabaseHelper db;
    Cursor cursor;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    FloatingActionButton fab_send;
    ProgressBar progressBar;
    private ProgressDialog progress;

    public IndiaCastChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_india_cast_channel, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.indiacast_channel_fragment_recy);
        fab_send = v.findViewById(R.id.fab_send);
        progressBar = v.findViewById(R.id.progress_bar);
        networkId = getArguments().getString("networkId");
        bundle = new Bundle();
        bundle.putString("networkId", networkId);
        db = new DatabaseHelper(getContext());
        lst_indiacast_channel = new ArrayList<>();
        getIndiaCastChannelsFromNetwork();
        adapter = new IndiaCastChannelAdapter(getContext(), lst_indiacast_channel);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        scrollBar = v.findViewById(R.id.bubbleScrollBar);
        scrollBar.attachToRecyclerView(recyclerView);
        scrollBar.setBubbleTextProvider(new BubbleTextProvider() {
            @Override
            public String provideBubbleText(int i) {
                return new StringBuilder(lst_indiacast_channel.get(i).getIndiaCastChannelName().substring(0, 1)).toString();
            }
        });
        //region FAB Send
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Cursor cursor = db.getIndiaCastChannels(networkId);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        iStatusValue = cursor.getString(cursor.getColumnIndex("IStatusID"));
                        if (iStatusValue == null) {
                            progressBar.setVisibility(View.GONE);
                            IStatusFlag = false;
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Alert!");
                            builder.setMessage("Status cannot be empty!");
                            builder.setCancelable(true);
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.show();
                            break;
                        } else {
                            IStatusFlag = true;
                        }
                        cursor.moveToNext();
                    }
                    db.close();
                }
                if (IStatusFlag == true) {
                    final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
                    dialogBuilder.setCancelable(false);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.network_sumbit, null);
                    txt_comment = dialogView.findViewById(R.id.txt_comment);
                    Button btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);
                    ImageView btn_cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);
                    TextView channelname_tv = (TextView) dialogView.findViewById(R.id.channelname_tv);
                    TextView lcn_tv = (TextView) dialogView.findViewById(R.id.lcn_tv);
                    Cursor res = db.getLandingPage(networkId);
                    if (res.getCount() >= 1) {
                        if (res.moveToFirst()) {
                            Channel_name = res.getString(res.getColumnIndex("Channel_Name")).trim();
                            LCN_No = res.getString(res.getColumnIndex("LCN_No"));
                            res.moveToNext();
                            channelname_tv.setText(Channel_name);
                            if (LCN_No.equals(null)) {
                                lcn_tv.setText("");
                            } else {
                                lcn_tv.setText("(" + LCN_No + ")");
                            }
                        }
                    }

                    TableLayout IndiaCastChannelsTableLayout = dialogView.findViewById(R.id.IndiaCastChannelsTableLayout);
                    cursor = db.getIndiaCastChannels(networkId);
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.indiacastchannels_table_item, null, false);
                            TextView channelname = tableRow.findViewById(R.id.channelname);
                            TextView iStatus = tableRow.findViewById(R.id.iStatus);
                            channelname.setText(cursor.getString(cursor.getColumnIndex("Channel_Name")));
                            iStatusValue = cursor.getString(cursor.getColumnIndex("IStatusID"));
//                                    if (iStatusValue == null) {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle("Alert!");
//                                        builder.setMessage("Status cannot be empty!");
//                                        builder.setCancelable(false);
//                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                            }
//                                        });
//                                        builder.show();
//                                        break;
//                                    } else {
//
//                                    }
//                        iStatus.setText(cursor.getString(cursor.getColumnIndex("IStatusID")));
                            Cursor IStatusIdcrs = db.getIndiaCastChannelStatusById(iStatusValue);
                            if (IStatusIdcrs.moveToFirst()) {
                                String istatusText = IStatusIdcrs.getString(IStatusIdcrs.getColumnIndex("IStatus"));
                                iStatus.setText(istatusText);
                            } else {
                                iStatus.setText(null);
                            }
                            IndiaCastChannelsTableLayout.addView(tableRow);
                            cursor.moveToNext();
                        }
                    }
                    db.close();

                    TableLayout tableLayout = dialogView.findViewById(R.id.tableLayout);
                    cursor = db.getChannelsFromNetwork(networkId);
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.table_item, null, false);
                            TextView channelname = tableRow.findViewById(R.id.channelname);
                            TextView lcnno = tableRow.findViewById(R.id.lcnno);
                            TextView position = tableRow.findViewById(R.id.position);
                            TextView genre = tableRow.findViewById(R.id.genre);
                            channelname.setText(cursor.getString(cursor.getColumnIndex("Channel_Name")));
                            lcnno.setText(cursor.getString(cursor.getColumnIndex("LCN_No")));
                            position.setText(cursor.getString(cursor.getColumnIndex("Position")));
                            genre.setText(cursor.getString(cursor.getColumnIndex("Genre")));
                            tableLayout.addView(tableRow);
                            cursor.moveToNext();
                        }
                    }
                    db.close();
                    btn_cancel.setOnClickListener(view -> {
                        progressBar.setVisibility(View.GONE);
                        dialogBuilder.dismiss();
                    });
                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConnectivityManager cm =
                                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                            boolean isConnected = activeNetwork != null &&
                                    activeNetwork.isConnectedOrConnecting();
                            progressBar.setVisibility(View.VISIBLE);
                            radio_group = dialogView.findViewById(R.id.radio_group);
                            int selectedId = radio_group.getCheckedRadioButtonId();
                            radio_Button = dialogView.findViewById(selectedId);
                            String comment = txt_comment.getText().toString();
                            if (selectedId != -1) {
                                String radio = radio_Button.getText().toString();
                                if (isConnected) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        if (db.submitNetwork(networkId, comment, radio)) {
                                            Cursor res = db.tbl_network_channel_mapping();
                                            Cursor ICPLcrs = db.getIndiaCastChannels(networkId);
                                            tbl_network_channel_placement.clear();
                                            tbl_placement_indiacast_channels_details_lst.clear();
                                            res.moveToFirst();
                                            while (!res.isAfterLast()) {
                                                try {
                                                    JSONObject postData = new JSONObject();
                                                    postData.put("Id", res.getString(res.getColumnIndex("ID")));
                                                    postData.put("Network_ID", res.getString(res.getColumnIndex("Network_ID")));
                                                    postData.put("Network_Name", res.getString(res.getColumnIndex("Network_Name")));
                                                    postData.put("Channel_Name", res.getString(res.getColumnIndex("Channel_Name")));
                                                    postData.put("LCN_No", res.getInt(res.getColumnIndex("LCN_No")));
                                                    postData.put("Genre", res.getString(res.getColumnIndex("Genre")));
                                                    postData.put("Position", res.getInt(res.getColumnIndex("Position")));
                                                    postData.put("Landing_Page_Flag", res.getString(res.getColumnIndex("Landing_Page_Flag")));
                                                    postData.put("Dual_LCN_Flag", res.getString(res.getColumnIndex("Dual_LCN_Flag")));
                                                    postData.put("Triple_LCN_Flag", res.getString(res.getColumnIndex("Triple_LCN_Flag")));
                                                    postData.put("Multiple_LCN", res.getInt(res.getColumnIndex("Multiple_LCN")));
                                                    postData.put("Week_no", res.getInt(res.getColumnIndex("Week_no")));
                                                    postData.put("Year", res.getInt(res.getColumnIndex("Year")));
                                                    postData.put("On_Call_Site", res.getString(res.getColumnIndex("On_Call_Site")));
                                                    postData.put("Location", res.getString(res.getColumnIndex("Location")));
                                                    postData.put("Entered_By", res.getString(res.getColumnIndex("Entered_By")));
                                                    postData.put("Status_ID", res.getString(res.getColumnIndex("Status_ID")));
                                                    postData.put("Created_Date", res.getString(res.getColumnIndex("Created_Date")));
                                                    postData.put("Updated_Date", res.getString(res.getColumnIndex("Updated_Date")));
                                                    postData.put("Comments", res.getString(res.getColumnIndex("Comments")));
                                                    tbl_network_channel_placement.add(postData);
                                                    res.moveToNext();
                                                } catch (JSONException e) {
                                                    progressBar.setVisibility(View.GONE);
                                                    e.printStackTrace();
                                                }
                                            }
                                            ICPLcrs.moveToFirst();
                                            while (!ICPLcrs.isAfterLast()) {
                                                try {
                                                    JSONObject postICPLData = new JSONObject();
                                                    postICPLData.put("ICPID", ICPLcrs.getString(ICPLcrs.getColumnIndex("ICPID")));
                                                    postICPLData.put("ChannelID", ICPLcrs.getString(ICPLcrs.getColumnIndex("ChannelID")));
                                                    postICPLData.put("LCN", ICPLcrs.getInt(ICPLcrs.getColumnIndex("LCN")));
                                                    postICPLData.put("Position", ICPLcrs.getInt(ICPLcrs.getColumnIndex("Position")));
                                                    postICPLData.put("CPosition", ICPLcrs.getInt(ICPLcrs.getColumnIndex("CPosition")));
                                                    postICPLData.put("StatusID", ICPLcrs.getInt(ICPLcrs.getColumnIndex("IStatusID")));
                                                    postICPLData.put("Network_ID", ICPLcrs.getString(ICPLcrs.getColumnIndex("NetworkID")));
                                                    postICPLData.put("Created_Date", ICPLcrs.getString(ICPLcrs.getColumnIndex("Created_Date")));
                                                    postICPLData.put("Others", ICPLcrs.getString(ICPLcrs.getColumnIndex("Others")));
                                                    tbl_placement_indiacast_channels_details_lst.add(postICPLData);
                                                    ICPLcrs.moveToNext();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            try {
                                                postRequestICPL(tbl_placement_indiacast_channels_details_lst.toString());
                                                postRequest(tbl_network_channel_placement.toString());
                                            } catch (IOException e) {
                                                progressBar.setVisibility(View.GONE);
                                                e.printStackTrace();
                                            }
                                            dialogBuilder.dismiss();
                                        } else {
                                            try {
                                                progressBar.setVisibility(View.GONE);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Alert!");
                                                builder.setMessage("Network can not be submitted twice in a day!");
                                                builder.setCancelable(false);
                                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                                builder.show();
                                                dialogBuilder.dismiss();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } else {
                                    try {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Alert!");
                                        builder.setMessage("No Internet Connection! Network can not be submitted");
                                        builder.setCancelable(false);
                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        builder.show();
                                        dialogBuilder.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                try {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Alert!");
                                    builder.setMessage("Select On Call or On Site!");
                                    builder.setCancelable(false);
                                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    builder.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();
                }
                db.close();
            }
        });
        //endregion
        return v;
    }

    public void getIndiaCastChannelsFromNetwork() {
        cursor = db.getIndiaCastChannels(networkId);
        if (cursor.getCount() <= 0) {
            Log.d("IndiaCastChannels: ", "NO indiacast channels");
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Network_ID = cursor.getString(cursor.getColumnIndex("NetworkID"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                String LCN_No = cursor.getString(cursor.getColumnIndex("LCN"));
                String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                String CPosition = cursor.getString(cursor.getColumnIndex("CPosition"));
                Cursor IStatusIdcrs = db.getIndiaCastChannelStatusById(IStatusID);
                if (IStatusIdcrs.moveToFirst()) {
                    IStatusID = IStatusIdcrs.getString(IStatusIdcrs.getColumnIndex("IStatus"));
                } else {
                    IStatusID = null;
                }
                lst_indiacast_channel.add(new IndiaCastChannel(Channel_Name, LCN_No, Position, CPosition, IStatusID, Network_ID, ""));
                cursor.moveToNext();
            }
        }
        db.close();
        Fragment currentFragment = getFragmentManager().findFragmentByTag("ICCF");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, currentFragment, "ICCF");
//        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    void postRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/setlist/placement";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, postBody);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(postUrl)
                    .addHeader("content-type", "application/json")
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        db.updateAfterFailureWhileSubmitting(networkId);
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Server connection lost placement!");

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String jsonData = response.body().string();
                    String name = null;
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(jsonData);
                        JSONObject Name = jsonArray.getJSONObject(0);
                        name = Name.getString("Name");
                        if (name.equals("Data Updated Successfully")) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Alert!");
                                        builder.setMessage("Mapping Submitted");
                                        builder.setCancelable(false);
                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progress = new ProgressDialog(getActivity());
                                                progress.setCancelable(false);
                                                progress.setTitle("Please wait....");
                                                progress.setMessage("Fetching details");
                                                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                progress.show();
                                                try {
                                                    db = new DatabaseHelper(getContext());
                                                    Cursor cursor = db.getUserDetails();
                                                    if (cursor.moveToFirst()) {
                                                        while (!cursor.isAfterLast()) {
                                                            Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
                                                            Token = cursor.getString(cursor.getColumnIndex("Token"));
                                                            User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
                                                            cursor.moveToNext();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                ConnectivityManager cm =
                                                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                                final boolean isConnected = activeNetwork != null &&
                                                        activeNetwork.isConnectedOrConnecting();
                                                if (isConnected) {
                                                    JSONObject postData = new JSONObject();
                                                    try {
                                                        postData.put("loginid", Login_ID);
                                                        postData.put("token", Token);
                                                        networkCountPostRequest(postData.toString());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                                        builder.show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        db.updateAfterFailureWhileSubmitting(networkId);
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Error occurred during Channel submission!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                db.updateAfterFailureWhileSubmitting(networkId);
                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void postRequestICPL(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/setlist/icplacementchannels";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, postBody);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(postUrl)
                    .addHeader("content-type", "application/json")
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        db.updateAfterFailureWhileSubmitting(networkId);

                                        progressBar.setVisibility(View.GONE);
                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Server connection lost indiacastcannels!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String jsonData = response.body().string();
                    String name = null;
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(jsonData);
                        JSONObject Name = jsonArray.getJSONObject(0);
                        name = Name.getString("Name");
                        if (name.equals("Data Updated Successfully")) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Alert!");
                                        builder.setMessage("IndiaCast Channels Submitted");
                                        builder.setCancelable(false);
                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progress = new ProgressDialog(getActivity());
                                                progress.setCancelable(false);
                                                progress.setTitle("Please wait....");
                                                progress.setMessage("Fetching details");
                                                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                progress.show();
                                                try {
                                                    db = new DatabaseHelper(getContext());
                                                    Cursor cursor = db.getUserDetails();
                                                    if (cursor.moveToFirst()) {
                                                        while (!cursor.isAfterLast()) {
                                                            Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
                                                            Token = cursor.getString(cursor.getColumnIndex("Token"));
                                                            User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
                                                            cursor.moveToNext();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                ConnectivityManager cm =
                                                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                                final boolean isConnected = activeNetwork != null &&
                                                        activeNetwork.isConnectedOrConnecting();
                                                if (isConnected) {
                                                    JSONObject postData = new JSONObject();
                                                    try {
                                                        postData.put("loginid", Login_ID);
                                                        postData.put("token", Token);
                                                        networkCountPostRequest(postData.toString());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {

                                                }

                                            }
                                        });
                                        builder.show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {                db.updateAfterFailureWhileSubmitting(networkId);

                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Error occurred during IndiaCast Channel submission!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {                db.updateAfterFailureWhileSubmitting(networkId);

                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void networkCountPostRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/history/placement";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, postBody);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(postUrl)
                    .addHeader("content-type", "application/json")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Server connection lost placement count!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    try {
                        if (db.getPlacement(response.body().string())) {
                            progress.dismiss();
                            Intent intent = new Intent(getActivity(), Main2Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}