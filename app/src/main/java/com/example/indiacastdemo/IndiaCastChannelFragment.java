package com.example.indiacastdemo;

import android.database.Cursor;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.Channel;
import com.example.indiacastdemo.Model.IndiaCastChannel;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import java.util.ArrayList;

public class IndiaCastChannelFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<IndiaCastChannel> lst_indiacast_channel;
    BubbleScrollBar scrollBar;
    EditText edt_searcedt_search_indiacast_channel;
    SpinnerDialog statusSpinnerDialog;
    Bundle bundle;
    String networkId, networkName, Login_ID, Token, User_ID, Network_ID, Channel_name;
    DatabaseHelper db;
    Cursor cursor;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
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
        networkId = getArguments().getString("networkId");
        bundle = new Bundle();
        bundle.putString("networkId", networkId);
        db = new DatabaseHelper(getContext());
        lst_indiacast_channel = new ArrayList<>();
        getChannelsFromNetwork();
        adapter = new IndiaCastChannelAdapter(getContext(), lst_indiacast_channel);
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
                return new StringBuilder(lst_indiacast_channel.get(i).getIndiaCastChannelName().substring(0, 1)).toString();
            }
        });
        edt_searcedt_search_indiacast_channel = v.findViewById(R.id.edt_search_indiacast_channel);
        edt_searcedt_search_indiacast_channel.addTextChangedListener(new TextWatcher() {

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
                cursor = db.getChannelsFromNetworkBySearch(networkId, text);
                lst_indiacast_channel.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                        String Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                        String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                        String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                        String Position = cursor.getString(cursor.getColumnIndex("Position"));
                        lst_indiacast_channel.add(new IndiaCastChannel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
                        cursor.moveToNext();
                    }
                }
                db.close();
                adapter.notifyDataSetChanged();
            }
        });
        return v;
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
                lst_indiacast_channel.add(new IndiaCastChannel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
                cursor.moveToNext();
            }
        }
        db.close();
    }

}