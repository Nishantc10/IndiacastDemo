package com.example.indiacastdemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.Channel;

import java.util.ArrayList;

public class HistoryChannelViewAdapter extends RecyclerView.Adapter<HistoryChannelViewAdapter.HistoryChannelViewHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<Channel> channels;
    ArrayList<IdentifiableObject> channelist = new ArrayList<>();
    Cursor cursor = null;
    SpinnerDialog chanels;
    String Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid, createdDate;

    public HistoryChannelViewAdapter(Context mContext, ArrayList<Channel> channels) {
        this.mContext = mContext;
        this.channels = channels;
    }

    @NonNull
    @Override
    public HistoryChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_channel, viewGroup, false);
        HistoryChannelViewHolder historyChannelViewHolder = new HistoryChannelViewHolder(v);
        db = new DatabaseHelper(mContext);
        return historyChannelViewHolder;
    }

    @Override
    public void onBindViewHolder(final HistoryChannelViewHolder channelViewHolder, int position) {
        Channel channel = channels.get(position);
        channelViewHolder.edt_channelName.setText(channel.getChannelName());
        channelViewHolder.edt_lcn.setText(channel.getLCN());
        channelViewHolder.edt_position.setText(channel.getPosition());
        channelViewHolder.edt_genreName.setText(channel.getGenre());
        networkid = channel.getNetworkId();
        createdDate = channel.getCreatedDate();
        channelViewHolder.edt_channelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (status.equals("Approved")) {
//                    fab_more.setVisibility(View.GONE);
//                } else if (status.equals("Submitted")) {
//                    fab_more.setVisibility(View.GONE);
//                } else if (status.equals("Completed")) {
//                    fab_more.setVisibility(View.GONE);
//                } else {
//                    fab_more.setVisibility(View.VISIBLE);
//                }
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
                chanels = new SpinnerDialog((Activity) mContext, channelist, "Select or Search City", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
                chanels.showSpinerDialog();
                chanels.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(IdentifiableObject item, int position) {
                        String getchannel = item.getTitle();
                        channelViewHolder.edt_channelName.setText(getchannel);
                        Chgd_lcn = channelViewHolder.edt_lcn.getText().toString();
                        Channel_name = channelViewHolder.edt_channelName.getText().toString();
                        Genre_name = channelViewHolder.edt_genreName.getText().toString();
                        Position = channelViewHolder.edt_position.getText().toString();
//                        Log.d("onclickgen: ", Channel_name + "," + Genre_name + "," + Chgd_lcn + "," + Position + "," + networkid);
                        db.updateByCnlPosGenFromPlacement(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid, createdDate);
                        updateOnDataChange();
                    }
                });
            }
        });
        channelViewHolder.edt_position.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    prv_lcn = channelViewHolder.edt_lcn.getText().toString();
                } else {
                    Chgd_lcn = channelViewHolder.edt_lcn.getText().toString();
                    Channel_name = channelViewHolder.edt_channelName.getText().toString();
                    Genre_name = channelViewHolder.edt_genreName.getText().toString();
                    Position = channelViewHolder.edt_position.getText().toString();
                    db.updateByCnlPosGenFromPlacement(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid, createdDate);
                    updateOnDataChange();
                }
//                Log.d("onFocusChange: ", hasFocus + "");
            }
        });
        channelViewHolder.edt_genreName.setOnClickListener(new View.OnClickListener() {
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

                chanels = new SpinnerDialog((Activity) mContext, channelist, "Select or Search City", R.drawable.ic_edit_location_black_24dp, R.drawable.ic_my_location_black_2dp);
                chanels.showSpinerDialog();
                chanels.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(IdentifiableObject item, int position) {
                        String getGenre = item.getTitle();
                        channelViewHolder.edt_genreName.setText(getGenre);
                        Chgd_lcn = channelViewHolder.edt_lcn.getText().toString();
                        Channel_name = channelViewHolder.edt_channelName.getText().toString();
                        Genre_name = channelViewHolder.edt_genreName.getText().toString();
                        Position = channelViewHolder.edt_position.getText().toString();
//                        Log.d("onclickgen: ", Channel_name + "," + Genre_name + "," + Chgd_lcn + "," + Position + "," + networkid);
                        db.updateByCnlPosGenFromPlacement(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid, createdDate);
                        updateOnDataChange();
                    }
                });
            }
        });
        channelViewHolder.edt_lcn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    prv_lcn = channelViewHolder.edt_lcn.getText().toString();
                } else {
                    Chgd_lcn = channelViewHolder.edt_lcn.getText().toString();
                    Channel_name = channelViewHolder.edt_channelName.getText().toString();
                    Genre_name = channelViewHolder.edt_genreName.getText().toString();
                    Position = channelViewHolder.edt_position.getText().toString();
                    if (prv_lcn.equals(Chgd_lcn)) {

                    } else {
                        if (db.updateByLcnFromPlacement(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid, createdDate)) {
                            updateOnDataChange();
                        } else {
                            try {
                                AlertDialogModel.generateAlertDialog(mContext, "Alert", "Duplicate Entry...! Trying to change LCN " + prv_lcn + " with LCN " + Chgd_lcn);
                                channelViewHolder.edt_lcn.setText(prv_lcn);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                notifyDataSetChanged();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public static class HistoryChannelViewHolder extends RecyclerView.ViewHolder {

        private TextView edt_channelName, network_name, edt_genreName;
        private EditText edt_lcn, edt_position;

        public HistoryChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            network_name = itemView.findViewById(R.id.network_name);
            edt_channelName = (TextView) itemView.findViewById(R.id.edt_channelName);
            edt_lcn = (EditText) itemView.findViewById(R.id.edt_lcn);
            edt_position = (EditText) itemView.findViewById(R.id.edt_position);
            edt_genreName = itemView.findViewById(R.id.edt_genreName);
        }
    }
    public void updateOnDataChange() {
        cursor = db.getChannelsFromNetworkFromPlacement(networkid, createdDate);
        channels.clear();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String Network_Name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
//                String Channel_ID = cursor.getString(cursor.getColumnIndex("Channel_ID"));
                String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
                String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                channels.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, createdDate));
                cursor.moveToNext();
            }
        }
        db.close();
    }
}
