package com.example.indiacastdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.He_Network;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HEFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<He_Network> lst_HE_Network = null;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    DatabaseHelper db;
    Cursor cursor;
    String Token, Login_ID, User_ID;
    TextDrawable drawable;
    ConnectionCheck connectionCheck;
    BubbleScrollBar scrollBar;
    EditText edt_search;
    Bundle bundle;
    private ProgressDialog progress;

    public HEFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_he, container, false);
        ((Main2Activity) getActivity()).customtitle.setText(R.string.mapping);

//        try {
//            Cursor cursor = db.getUserDetails();
//            if (cursor.moveToFirst()) {
//                    Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
//                    Token = cursor.getString(cursor.getColumnIndex("Token"));
//                    User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
////            Login_ID = bundle.getString("Login_ID");
////            User_ID = bundle.getString("User_ID");
////            Token = bundle.getString("Token");
//
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        User_ID = getArguments().getString("User_ID");
//        Login_ID = getArguments().getString("Login_ID");
//        Token = getArguments().getString("Token");

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
        bundle = new Bundle();
        bundle.putString("User_ID", User_ID);
        bundle.putString("Login_ID", Login_ID);
        bundle.putString("Token", Token);
        db = new DatabaseHelper(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.he_fragment_recy);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                dividerItemDecoration.setOrientation(0);
            }
        });
        try {
            String ABCD = getArguments().getString("ABCD");
            bundle.putString("ABCD", ABCD);

            if (ABCD.equals("abcd")) {
                String createdDate = getArguments().getString("createdDate");
                String networkId = getArguments().getString("networkId");
                String networkName = getArguments().getString("networkName");
                String status = getArguments().getString("status");
                bundle.putString("networkId", networkId);
                bundle.putString("createdDate", createdDate);
                bundle.putString("networkName", networkName);
                bundle.putString("status", status);
                HistoryMappingFragment historyMappingFragment = new HistoryMappingFragment();
                historyMappingFragment.setArguments(bundle);
                Fragment fragment = historyMappingFragment;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lst_HE_Network = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(), lst_HE_Network);
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
                return new StringBuilder(lst_HE_Network.get(i).getNetwork_Name().substring(0, 1)).toString();
            }
        });
        edt_search = v.findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String text = s.toString();
                cursor = db.getNetworksBySearch(text);
                lst_HE_Network.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                        String status = cursor.getString(cursor.getColumnIndex("Status"));
                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
                        if (createdDate.equals("null")) {
                            createdDate = "No Submission Yet!";
                        }
                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
                        String a = network_name.substring(0, 1);
                        TextDrawable drawable = TextDrawable.builder()
                                .buildRoundRect(a, Color.BLACK, 60);
                        lst_HE_Network.add(new He_Network(network_id, network_name, createdDate, number_of_channels, drawable));
                        cursor.moveToNext();
                    }
                }
                db.close();
                adapter.notifyDataSetChanged();
            }
        });
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            getStaticData();
        } else {
            getStaticData();
        }
        return v;
    }

    public void getStaticData() {
        Cursor cursor = db.getAllNetworks();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                String created_date = cursor.getString(cursor.getColumnIndex("Created_Date"));
                if (created_date.equals("null")) {
                    created_date = "No Submission Yet!";
                }
                try {
                    String a = network_name.substring(0, 1);
                    drawable = TextDrawable.builder()
                            .buildRoundRect(a, Color.BLACK, 60);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lst_HE_Network.add(new He_Network(network_id, network_name, created_date, "", drawable));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                Fragment fragment = null;
                HistoryFragment historyFragment = new HistoryFragment();
                fragment = historyFragment;
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "HistoryFragment").addToBackStack(null).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void postRequest(String postBody) throws IOException {
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
                                        AlertDialogModel.generateAlertDialog(getActivity(), "Alert", "Server connection lost!");
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
//                Log.d("onResponse: ", response.body().string());
                    try {
                        if (db.getPlacement(response.body().string())) {
//
                            Fragment fragment = null;
                            HistoryFragment historyFragment = new HistoryFragment();
                            fragment = historyFragment;
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "HistoryFragment").addToBackStack(null).commit();
                            progress.dismiss();
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(getActivity(), "Please Relogin !!!", "You have already logged in to another device.!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
//                        Log.d( "Into else: ","Into else");
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progress.dismiss();
                    }
                }
            });
        } else {
            try {
                progress.dismiss();
                AlertDialogModel.generateAlertDialog(getActivity(), "Alert", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}