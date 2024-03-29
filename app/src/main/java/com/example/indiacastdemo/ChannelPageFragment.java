package com.example.indiacastdemo;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.Channel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChannelPageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Channel> lst_channel;
    private ArrayList<JSONObject> tbl_network_channel_placement = new ArrayList<>();
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private ProgressDialog progress;
    FloatingActionButton fab_add, fab_more, fab_next;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    DatabaseHelper db;
    Cursor cursor;
    SpinnerDialog chanels;
    ArrayList<IdentifiableObject> channelist = new ArrayList<>();
    SwipeControllerChannel swipeControllerChannel = null;
    BubbleScrollBar scrollBar;
    EditText edt_search, edt_position, edt_lcn;
    TextView edt_channelName, edt_genreName, network_name;
    //    RadioGroup radio_goup;
//    RadioButton radio_Button;
//    String LCN_No = null;
    //    private ProgressDialog progress;
    //    boolean flag = false;
    ProgressBar progressBar;
    String networkId, networkName, Login_ID, Token, User_ID, Network_ID;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel_page, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        network_name = v.findViewById(R.id.network_name);
        bundle = new Bundle();
        bundle.putString("networkId", networkId);
        progressBar = v.findViewById(R.id.progress_bar);
        lst_channel = new ArrayList<>();
        db = new DatabaseHelper(getContext());
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
        getChannelsFromNetwork();
        recyclerView = (RecyclerView) v.findViewById(R.id.channel_fragment_recy);
        fab_add = v.findViewById(R.id.fab_add);
        fab_more = v.findViewById(R.id.fab_more);
        fab_next = v.findViewById(R.id.fab_next);
        fab_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        //region FAB Add
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
                        chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search Channel", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
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
                        chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search Genre", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
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
//                        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S");
//                        Date date = Calendar.getInstance().getTime();
//                        if ((channelname.equals("")) || (genrename.equals("")) || (lcn.equals("")) || (position.equals(""))) {

                       boolean isChannelAvailable = db.isCheckIndiaCastChannel(channelname);


                        if ((channelname.equals("")) || (lcn.equals(""))) {
                            try {
                                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "All Fields are Mandatory!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if (isChannelAvailable && (genrename.equals("")))
                        {
                            try {
                                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Genre is Mandatory for IndiaCast Channels!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            if (db.insert_newChannel_tbl_network_channel_mapped(Network_ID, networkName, channelname, lcn, genrename, position, "false", "false", "false", 0, Calendar.getInstance().get(Calendar.YEAR), "On Call", "10:10:10", "", "", "", "", "", "", "")) {
                                lst_channel.clear();
                                getChannelsFromNetwork();
                                try {
                                    AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Channel Inserted Successfully");
                                    dialogBuilder.dismiss();
                                    adapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "Duplicate LCN Found...!");
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
        //endregion
        //region FAB next

        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(getActivity());
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                if (getChannelsFromIndiaCastPlacement(networkId)) {
                    progress.dismiss();
                    Fragment fragment = new IndiaCastChannelFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragment, "ICCF");
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    if (getChannelsFromIndiaCastPlacement(networkId)) {
                        Fragment fragment = new IndiaCastChannelFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    Log.d("ICCD saving done", "done");
                }
//                Fragment fragment = new IndiaCastChannelFragment();
//                fragment.setArguments(bundle);
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_container, fragment, "FragmentTag");
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();
//                progressBar.setVisibility(View.VISIBLE);
//                final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
//                dialogBuilder.setCancelable(false);
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.network_sumbit, null);
//                txt_comment = dialogView.findViewById(R.id.txt_comment);
//                Button btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);
//                ImageView btn_cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);
//                TextView channelname_tv = (TextView) dialogView.findViewById(R.id.channelname_tv);
//                TextView lcn_tv = (TextView) dialogView.findViewById(R.id.lcn_tv);
//                Cursor res = db.getLandingPage(networkId);
//                if (res.getCount() == 1) {
//                    if (res.moveToFirst()) {
//                        Channel_name = res.getString(res.getColumnIndex("Channel_Name")).trim();
//                        LCN_No = res.getString(res.getColumnIndex("LCN_No"));
//                        res.moveToNext();
//                        channelname_tv.setText(Channel_name);
//                        if (LCN_No.equals(null)) {
//                            lcn_tv.setText("");
//                        } else {
//                            lcn_tv.setText("(" + LCN_No + ")");
//                        }
//                    }
//                }
//                TableLayout tableLayout = (TableLayout) dialogView.findViewById(R.id.tableLayout);
//                cursor = db.getChannelsFromNetwork(networkId);
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.table_item, null, false);
//                        TextView channelname = (TextView) tableRow.findViewById(R.id.channelname);
//                        TextView lcnno = (TextView) tableRow.findViewById(R.id.lcnno);
//                        TextView position = (TextView) tableRow.findViewById(R.id.position);
//                        TextView genre = (TextView) tableRow.findViewById(R.id.genre);
//                        channelname.setText(cursor.getString(cursor.getColumnIndex("Channel_Name")));
//                        lcnno.setText(cursor.getString(cursor.getColumnIndex("LCN_No")));
//                        position.setText(cursor.getString(cursor.getColumnIndex("Position")));
//                        genre.setText(cursor.getString(cursor.getColumnIndex("Genre")));
//                        tableLayout.addView(tableRow);
//                        cursor.moveToNext();
//                    }
//                }
//                db.close();
//                btn_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        progressBar.setVisibility(View.GONE);
//                        dialogBuilder.dismiss();
//                    }
//                });
//                btn_submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ConnectivityManager cm =
//                                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//                        boolean isConnected = activeNetwork != null &&
//                                activeNetwork.isConnectedOrConnecting();
//                        progressBar.setVisibility(View.GONE);
//                        radio_goup = (RadioGroup) dialogView.findViewById(R.id.radio_goup);
//                        int selectedId = radio_goup.getCheckedRadioButtonId();
//                        radio_Button = (RadioButton) dialogView.findViewById(selectedId);
//                        String comment = txt_comment.getText().toString();
//                        if (selectedId != -1) {
//                            String radio = radio_Button.getText().toString();
//                            if (isConnected) {
//                                if (db.submitNetwork(networkId, comment, radio)) {
//                                    Cursor res = db.tbl_network_channel_mapping();
//                                    tbl_network_channel_placement.clear();
//                                    res.moveToFirst();
//                                    while (!res.isAfterLast()) {
//                                        try {
//                                            JSONObject postData = new JSONObject();
//                                            postData.put("Id", res.getString(res.getColumnIndex("ID")));
//                                            postData.put("Network_ID", res.getString(res.getColumnIndex("Network_ID")));
//                                            postData.put("Network_Name", res.getString(res.getColumnIndex("Network_Name")));
//                                            postData.put("Channel_Name", res.getString(res.getColumnIndex("Channel_Name")));
//                                            postData.put("LCN_No", res.getString(res.getColumnIndex("LCN_No")));
//                                            postData.put("Genre", res.getString(res.getColumnIndex("Genre")));
//                                            postData.put("Position", res.getString(res.getColumnIndex("Position")));
//                                            postData.put("Landing_Page_Flag", res.getString(res.getColumnIndex("Landing_Page_Flag")));
//                                            postData.put("Dual_LCN_Flag", res.getString(res.getColumnIndex("Dual_LCN_Flag")));
//                                            postData.put("Triple_LCN_Flag", res.getString(res.getColumnIndex("Triple_LCN_Flag")));
//                                            postData.put("Multiple_LCN", res.getString(res.getColumnIndex("Multiple_LCN")));
//                                            postData.put("Week_no", res.getString(res.getColumnIndex("Week_no")));
//                                            postData.put("Year", res.getString(res.getColumnIndex("Year")));
//                                            postData.put("On_Call_Site", res.getString(res.getColumnIndex("On_Call_Site")));
//                                            postData.put("Location", res.getString(res.getColumnIndex("Location")));
//                                            postData.put("Entered_By", res.getString(res.getColumnIndex("Entered_By")));
//                                            postData.put("Status_ID", res.getString(res.getColumnIndex("Status_ID")));
//                                            postData.put("Created_Date", res.getString(res.getColumnIndex("Created_Date")));
//                                            postData.put("Updated_Date", res.getString(res.getColumnIndex("Updated_Date")));
//                                            postData.put("Comments", res.getString(res.getColumnIndex("Comments")));
//
//                                            tbl_network_channel_placement.add(postData);
//                                            res.moveToNext();
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    try {
//                                        postRequest(tbl_network_channel_placement.toString());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    dialogBuilder.dismiss();
//                                } else {
//                                    try {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle("Alert!");
//                                        builder.setMessage("Network can not be submitted twice in a day!");
//                                        builder.setCancelable(false);
//                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                            }
//                                        });
//                                        builder.show();
//                                        dialogBuilder.dismiss();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            } else {
//                                try {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                    builder.setTitle("Alert!");
//                                    builder.setMessage("No Internet Connection! Network can not be submitted");
//                                    builder.setCancelable(false);
//                                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                        }
//                                    });
//                                    builder.show();
//                                    dialogBuilder.dismiss();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        } else {
//                            try {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                builder.setTitle("Alert!");
//                                builder.setMessage("Select On Call or On Site!");
//                                builder.setCancelable(false);
//                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                });
//                                builder.show();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.show();
//                animateFab();
            }
        });
        //endregion
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
        adapter = new ChannelViewAdapter(getContext(), lst_channel);
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
//                if(text.equals(""))
                cursor = db.getChannelsFromNetworkBySearch(networkId, text);
                lst_channel.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                        String Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                        String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                        String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                        String Position = cursor.getString(cursor.getColumnIndex("Position"));
                        lst_channel.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
                        cursor.moveToNext();
                    }
                }
                db.close();
                adapter.notifyDataSetChanged();
            }
        });
        setupRecyclerView();
        return v;
    }

    private boolean getChannelsFromIndiaCastPlacement(String NetworkID) {

//        Cursor cursor = db.getChannelsFromIndiaCastPlacement(NetworkID);
////        db.deleteIndiaCastChannelsByNetworkID(NetworkID);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
//                String ChannelID = cursor.getString(cursor.getColumnIndex("IndiaCast"));
//                String LCN = cursor.getString(cursor.getColumnIndex("LCN_No"));
//                String Status_ID = cursor.getString(cursor.getColumnIndex("Status_ID"));
//                String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
//                String Created_date = cursor.getString(cursor.getColumnIndex("Created_date"));
//                String Position = cursor.getString(cursor.getColumnIndex("Position"));
//                String CPosition = cursor.getString(cursor.getColumnIndex("CPosition"));
//                db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        db.close();

//        Cursor curAllreadyEICC = db.getAllreadyExistsIndiaCastChannels(NetworkID);
//        if (curAllreadyEICC.moveToFirst()) {
//            while (!curAllreadyEICC.isAfterLast()) {
////                Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
////                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
////                String ChannelID = cursor.getString(cursor.getColumnIndex("IndiaCast"));
//                if (isNotNull(curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("LCN_No")))){
//                    LCN = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("LCN_No"));
//                    break;
//                }
////                String Status_ID = cursor.getString(cursor.getColumnIndex("Status_ID"));
////                String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
////                String Created_date = cursor.getString(cursor.getColumnIndex("Created_date"));
////                String Position = cursor.getString(cursor.getColumnIndex("Position"));
////                String CPosition = cursor.getString(cursor.getColumnIndex("CPosition"));
////                db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                curAllreadyEICC.moveToNext();
//            }
//            if (isNotNull(LCN)) {
//                curAllreadyEICC = db.getAllreadyExistsIndiaCastChannelsLCN(NetworkID);
//                if (curAllreadyEICC.moveToFirst()) {
//                    db.deleteIndiaCastChannelsByNetworkID(NetworkID);
//                    while (!curAllreadyEICC.isAfterLast()) {
//                        Network_ID = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("Network_ID"));
//                        String Channel_Name = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("Channel_Name"));
//                        String ChannelID = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("IndiaCast"));
//                        LCN = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("LCN_No"));
//                        if(!isNotNull(LCN))
//                            LCN = null;
//                        String Status_ID = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("Status_ID"));
//                        String IStatusID = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("IStatusID"));
//                        String Created_date = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("Created_date"));
//                        String Position = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("Position"));
//                        String CPosition = curAllreadyEICC.getString(curAllreadyEICC.getColumnIndex("CPosition"));
//                        db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                        curAllreadyEICC.moveToNext();
//                    }
//                }
//            } else {
//                Cursor cursor = db.getChannelsFromIndiaCastPlacement(NetworkID);
//                if (cursor.moveToFirst()) {
//                    db.deleteIndiaCastChannelsByNetworkID(NetworkID);
//                    while (!cursor.isAfterLast()) {
//                        Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
//                        String ChannelID = cursor.getString(cursor.getColumnIndex("IndiaCast"));
//                        LCN = cursor.getString(cursor.getColumnIndex("LCN_No"));
//                        if(!isNotNull(LCN))
//                            LCN = null;
//                        String Status_ID = cursor.getString(cursor.getColumnIndex("Status_ID"));
//                        String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
//                        String Created_date = cursor.getString(cursor.getColumnIndex("Created_date"));
//                        String Position = cursor.getString(cursor.getColumnIndex("Position"));
//                        String CPosition = cursor.getString(cursor.getColumnIndex("CPosition"));
//                        db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                        cursor.moveToNext();
//                    }
//                }
//                cursor.close();
//                curAllreadyEICC.close();
//                Cursor curAllreadyEICC1 = db.getAllreadyExistsIndiaCastChannels(NetworkID);
////                db.deleteIndiaCastChannelsByNetworkID(NetworkID);
//                if (curAllreadyEICC1.moveToFirst()) {
//                    db.deleteIndiaCastChannelsByNetworkID(NetworkID);
//                    while (!curAllreadyEICC1.isAfterLast()) {
//                        Network_ID = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("Network_ID"));
//                        String Channel_Name = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("Channel_Name"));
//                        String ChannelID = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("IndiaCast"));
//                        LCN = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("LCN_No"));
//                        if(!isNotNull(LCN))
//                            LCN = null;
//                        String Status_ID = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("Status_ID"));
//                        String IStatusID = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("IStatusID"));
//                        String Created_date = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("Created_date"));
//                        String Position = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("Position"));
//                        String CPosition = curAllreadyEICC1.getString(curAllreadyEICC1.getColumnIndex("CPosition"));
//                        db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                        //db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
//                        curAllreadyEICC1.moveToNext();
//                    }
//                }
//                curAllreadyEICC1.close();
//                db.close();
////                adapter.notifyDataSetChanged();
//            }
//        }
        String LCN = null;
        Cursor cursor = db.getChannelsFromIndiaCastPlacement(NetworkID);
        if (cursor.moveToFirst()) {
            db.deleteIndiaCastChannelsByNetworkID(NetworkID);
            while (!cursor.isAfterLast()) {
                Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                String ChannelID = cursor.getString(cursor.getColumnIndex("IndiaCast"));
                LCN = cursor.getString(cursor.getColumnIndex("LCN_No"));
                if (!isNotNull(LCN))
                    LCN = null;
                String Status_ID = cursor.getString(cursor.getColumnIndex("Status_ID"));
                String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
                String Created_date = cursor.getString(cursor.getColumnIndex("Created_date"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                String CPosition = cursor.getString(cursor.getColumnIndex("CPosition"));
                db.AddplacementIndiacastChannelsDetails(Channel_Name, ChannelID, LCN, Position, CPosition, IStatusID, Network_ID, "Others", Created_date, Status_ID);
                cursor.moveToNext();
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged();
        return true;
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
                                if (db.deleteChannelFromNetwork(networkId, deletelcn)) {
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
            fab_next.startAnimation(fabClose);
            fab_add.setClickable(false);
            fab_next.setClickable(false);
            isOpen = false;
        } else {
            fab_more.startAnimation(rotateBackward);
            fab_add.startAnimation(fabOpen);
            fab_next.startAnimation(fabOpen);
            fab_add.setClickable(true);
            fab_next.setClickable(true);
            isOpen = true;
        }
    }

    public void getChannelsFromNetwork() {
        cursor = db.getChannelsFromNetwork(networkId);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                lst_channel.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
                cursor.moveToNext();
            }
        }
        db.close();
    }

    public boolean isNotNull(String str) {
        if (str != null && !str.isEmpty() && !str.equals("null"))
            return true;
        return false;
    }
}
