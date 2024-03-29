package com.example.indiacastdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.ConnectionCheck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.Channel;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HistoryChannelPageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Channel> lst_channel;
    private ArrayList<JSONObject> tbl_network_channel_placement = new ArrayList<>();
    ArrayList<IdentifiableObject> channels = new ArrayList<>();
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    FloatingActionButton fab_add, fab_more, fab_send;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    DatabaseHelper db;
    ConnectionCheck connectionCheck;
    Cursor cursor;
    String LCN_No = null;
    SpinnerDialog chanels;
    ArrayList<IdentifiableObject> channelist = new ArrayList<>();
    SwipeControllerChannel swipeControllerChannel = null;
    BubbleScrollBar scrollBar;
    EditText edt_search, edt_position, edt_lcn;
    TextView edt_channelName, edt_genreName, txt_comment, network_name;
    String networkId, networkName, createdDate, status,Network_ID,Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position;
    RadioGroup radio_goup;
    RadioButton radio_Button;
    ProgressBar progressBar;
    private ProgressDialog progress_bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_channel_page, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        createdDate = getArguments().getString("createdDate");
        status = getArguments().getString("status");
        network_name = v.findViewById(R.id.network_name);
        progressBar = v.findViewById(R.id.progress_bar);
        lst_channel = new ArrayList<>();
        db = new DatabaseHelper(getContext());
        adapter = new HistoryChannelViewAdapter(getContext(), lst_channel);
        try {
            String ABCD = getArguments().getString("ABCD");
            if (ABCD.equals("abcd")) {
                //Date/time pattern of input date
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa"); //2/3/2020 1:18:00 PM
                //Date/time pattern of desired output date
                DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    //Conversion of input String to date
                    date = df.parse(createdDate);
                    //old date format to new date format
                    createdDate = outputformat.format(date);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cursor = db.getChannelsFromNetworkFromPlacement(networkId, createdDate);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String Network_Name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
//                String Channel_ID = cursor.getString(cursor.getColumnIndex("Channel_ID"));
                String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                lst_channel.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, createdDate));
                cursor.moveToNext();
            }
        }
        db.close();
        adapter.notifyDataSetChanged();
//        boolean IntStatus = connectionCheck.getConnectivityStatusString(getContext());
//        if (IntStatus) {
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Alert!");
//            builder.setMessage("No Internet Connection!");
//            builder.setCancelable(false);
//            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//            builder.show();
////            Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
//        }
        recyclerView = (RecyclerView) v.findViewById(R.id.channel_fragment_recy);
        fab_add = v.findViewById(R.id.fab_add);
        fab_more = v.findViewById(R.id.fab_more);
        fab_send = v.findViewById(R.id.fab_send);
        if (status.equals("Approved")) {
            fab_more.setVisibility(View.GONE);
        } else if (status.equals("Submitted")) {
            fab_more.setVisibility(View.GONE);
        } else if (status.equals("Completed")) {
            fab_more.setVisibility(View.GONE);
        } else {
            fab_more.setVisibility(View.VISIBLE);
        }
        fab_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
                dialogBuilder.setCancelable(false);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_add_task, null);
                edt_channelName = (TextView) dialogView.findViewById(R.id.edt_channelName);
                edt_genreName = dialogView.findViewById(R.id.edt_genreName);
                edt_lcn = (EditText) dialogView.findViewById(R.id.edt_lcn);
                edt_position = (EditText) dialogView.findViewById(R.id.edt_position);
                Button buttonSubmit = (Button) dialogView.findViewById(R.id.buttonSubmit);
                ImageView btn_close = (ImageView) dialogView.findViewById(R.id.buttonCancel);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                edt_channelName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cursor = db.getAllChannels();
                        channelist.clear();
                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {
                                String name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                                channelist.add(new IdentifiableObjectImpl(name, null, 0, R.drawable.ic_checkbox_marked_circle_outline_white_18dp));
                                cursor.moveToNext();
                            }
                        }
                        db.close();
                        chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search City", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
                        chanels.showSpinerDialog();
                        chanels.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(IdentifiableObject item, int position) {
                                String getchannel = item.getTitle();
                                edt_channelName.setText(getchannel);
                            }
                        });
                    }
                });
                edt_genreName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cursor = db.getAllGenre();
                        channelist.clear();
                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {
                                String name = cursor.getString(cursor.getColumnIndex("Genre"));
                                channelist.add(new IdentifiableObjectImpl(name, null, 0, R.drawable.ic_checkbox_marked_circle_outline_white_18dp));
                                cursor.moveToNext();
                            }
                        }
                        db.close();
                        chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search City", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
                        chanels.showSpinerDialog();
                        chanels.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(IdentifiableObject item, int position) {
                                String getchannel = item.getTitle();
                                edt_genreName.setText(getchannel);
                            }
                        });
                    }
                });
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String channelname = edt_channelName.getText().toString();
                        String genrename = edt_genreName.getText().toString();
                        String lcn = edt_lcn.getText().toString();
                        String position = edt_position.getText().toString();
                        if ((channelname.equals("")) || (genrename.equals("")) || (lcn.equals("")) || (position.equals(""))) {
                            try {
                                AlertDialogModel.generateAlertDialog(getContext(), "Alert", "All Fields are Mandatory!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            dialogBuilder.dismiss();
                            lst_channel.add(new Channel(channelname, lcn, position, genrename, Network_ID, createdDate));
                            if (db.insert_newChannel_tbl_network_channel_mappedFromPlacement(Network_ID, networkName, channelname, lcn, genrename, position, "false", "false", "false", 0, Calendar.getInstance().get(Calendar.WEEK_OF_YEAR), Calendar.getInstance().get(Calendar.YEAR), "On Call", "10:10:10", "", createdDate, "", "", "", "", "", "", "", "")) {
                                try {
                                    AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Channel Inserted Successfully");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Duplicate LCN Found!");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
                dialogBuilder.setCancelable(false);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.network_sumbit, null);
                txt_comment = dialogView.findViewById(R.id.txt_comment);
                Button btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);
                ImageView btn_cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);
                TextView channelname_tv = (TextView) dialogView.findViewById(R.id.channelname_tv);
                TextView lcn_tv = (TextView) dialogView.findViewById(R.id.lcn_tv);
                Cursor res = db.getLandingPageFromPlacement(networkId, createdDate);
                if (res.getCount() == 1) {
                    if (res.moveToFirst()) {
                        Channel_name = res.getString(res.getColumnIndex("Channel_Name"));
                        LCN_No = res.getString(res.getColumnIndex("LCN_No"));
//                        Log.d("LCN_No: ", LCN_No);
                        res.moveToNext();
                        channelname_tv.setText(Channel_name);
                        if (LCN_No.equals(null)) {
                            lcn_tv.setText("");
                        } else {
                            lcn_tv.setText("(" + LCN_No + ")");
//                            Log.d("LCN_No: ", LCN_No);
                        }
                    }
                }
                TableLayout tableLayout = (TableLayout) dialogView.findViewById(R.id.tableLayout);
                cursor = db.getChannelsFromNetworkFromPlacement(networkId, createdDate);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.table_item, null, false);
                        TextView channelname = (TextView) tableRow.findViewById(R.id.channelname);
                        TextView lcnno = (TextView) tableRow.findViewById(R.id.lcnno);
                        TextView position = (TextView) tableRow.findViewById(R.id.position);
                        TextView genre = (TextView) tableRow.findViewById(R.id.genre);
                        channelname.setText(cursor.getString(cursor.getColumnIndex("Channel_Name")));
                        lcnno.setText(cursor.getString(cursor.getColumnIndex("LCN_No")));
                        position.setText(cursor.getString(cursor.getColumnIndex("Position")));
                        genre.setText(cursor.getString(cursor.getColumnIndex("Genre")));
                        tableLayout.addView(tableRow);
                        cursor.moveToNext();
                    }
                }
                db.close();
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.GONE);
                        dialogBuilder.dismiss();
                    }
                });
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.GONE);
                        radio_goup = (RadioGroup) dialogView.findViewById(R.id.radio_group);
                        int selectedId = radio_goup.getCheckedRadioButtonId();
                        radio_Button = (RadioButton) dialogView.findViewById(selectedId);
                        String comment = txt_comment.getText().toString();
                        if (selectedId != -1) {
                            String radio = radio_Button.getText().toString();
                            if (db.submitNetworkFromPlacement(networkId, comment, radio, createdDate)) {
                                Cursor res = db.tbl_network_channel_placement(networkId, createdDate);
                                tbl_network_channel_placement.clear();
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
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    postRequest(tbl_network_channel_placement.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                dialogBuilder.dismiss();
                            }
                        } else {
                            try {
                                AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Select On Call or On Site!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
                animateFab();
            }
        });
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDraw(c, parent, state);
                dividerItemDecoration.setOrientation(0);
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        scrollBar = v.findViewById(R.id.bubbleScrollBar);
        scrollBar.attachToRecyclerView(recyclerView);
        scrollBar.setBubbleTextProvider(new BubbleTextProvider() {
            @Override
            public String provideBubbleText(int i) {
                return new StringBuilder(lst_channel.get(i).getChannelName().substring(0, 1)).toString();
            }
        });
        edt_search = v.findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
//                Log.d( "onTextChanged: ",s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
//                Log.d( "onTextChanged: ",s.toString());
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String text = s.toString();
                cursor = db.getChannelsFromNetworkFromPlacementBySearch(networkId, text, createdDate);
                lst_channel.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                        String Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                        String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                        String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                        String Position = cursor.getString(cursor.getColumnIndex("Position"));
                        lst_channel.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, createdDate));
                        cursor.moveToNext();
                    }
                }
                db.close();
                adapter.notifyDataSetChanged();
            }
        });

        android.net.ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        setupRecyclerView();
        return v;
    }

    private void setupRecyclerView() {
        swipeControllerChannel = new SwipeControllerChannel(new SwipeControllerActions() {
            @Override
            public void onRightClicked(final int position) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                String deletelcn = lst_channel.get(position).getLCN();
                                if (db.deleteChannelFromNetworkFromPlacement(networkId, deletelcn, createdDate)) {
                                    lst_channel.remove(position);
                                }
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeControllerChannel);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeControllerChannel.onDraw(c);
            }
        });
    }

    private void animateFab() {
        if (isOpen) {
            fab_more.startAnimation(rotateForward);
            fab_add.startAnimation(fabClose);
            fab_send.startAnimation(fabClose);
            fab_add.setClickable(false);
            fab_send.setClickable(false);
            isOpen = false;
        } else {
            fab_more.startAnimation(rotateBackward);
            fab_add.startAnimation(fabOpen);
            fab_send.startAnimation(fabOpen);
            fab_add.setClickable(true);
            fab_send.setClickable(true);
            isOpen = true;
        }
    }

//    void postRequest(String postBody) throws IOException {
//
//        ConnectivityManager cm =
//                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
//        if (isConnected) {
//            String postUrl = getString(R.string.api) + "/api/setlist/placement";
//
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//            OkHttpClient client = new OkHttpClient();
//            RequestBody body = RequestBody.create(JSON, postBody);
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(postUrl)
//                    .addHeader("content-type", "application/json")
//                    .put(body)
//                    .build();
//            Log.d("postRequest: ", body + "");
//            Log.d("postRequest: ", postBody);
//
//            client.newCall(request).enqueue(new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    try {
//                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Server connection lost!");
//                    } catch (Exception a) {
//                        a.printStackTrace();
//                    }
//                    call.cancel();
//                }
//
//                @Override
//                public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                    Log.d("onResponse: ", response.body().string());
//                    String name = null;
//                    JSONArray jsonArray = null;
//                    try {
//                        jsonArray = new JSONArray(response);
//                        JSONObject Name = jsonArray.getJSONObject(0);
//                        name = Name.getString("Name");
//                        if (name.equals("Data Updated Successfully")) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                public void run() {
//                                    try {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle("Alert!");
//                                        builder.setMessage("History Submitted");
//                                        builder.setCancelable(false);
//                                        builder.show();
////                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Submitted");
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        } else {
//                            getActivity().runOnUiThread(new Runnable() {
//                                public void run() {
//                                    try {
//                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Error occured during submission!");
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//            try {
////            progress.dismiss();
//                AlertDialogModel.generateAlertDialog(getContext(), "Alert", "No internet connection!!!");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

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
                                        builder.setTitle("Success");
                                        builder.setMessage("History Mapping Submitted");
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
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
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
                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
