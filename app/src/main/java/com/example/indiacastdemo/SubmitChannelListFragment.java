package com.example.indiacastdemo;

import android.database.Cursor;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.indiacastdemo.Database.DatabaseHelper;

public class SubmitChannelListFragment extends Fragment {
    DatabaseHelper db;
    String networkId;

    public SubmitChannelListFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_submit_channel_list, container, false);
        db = new DatabaseHelper(getContext());
        networkId = getArguments().getString("networkId");

        TableLayout tableLayout = (TableLayout)v.findViewById(R.id.tableLayout);
               Cursor cursor = db.getChannelsFromNetwork(networkId);
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
        return v;
    }
}