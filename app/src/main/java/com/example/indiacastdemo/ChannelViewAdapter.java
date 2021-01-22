package com.example.indiacastdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import androidx.annotation.NonNull;
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

public class ChannelViewAdapter extends RecyclerView.Adapter<ChannelViewAdapter.ChannelViewHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<Channel> channels;
    ArrayList<IdentifiableObject> channelist = new ArrayList<>();
    Cursor cursor = null;
    SpinnerDialog chanels;
    String Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid;

    public ChannelViewAdapter(Context mContext, ArrayList<Channel> channels) {
        this.mContext = mContext;
        this.channels = channels;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_channel, viewGroup, false);
        ChannelViewHolder channelViewHolder = new ChannelViewHolder(v);
        db = new DatabaseHelper(mContext);
        return channelViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChannelViewHolder channelViewHolder, int position) {
        Channel channel = channels.get(position);
        channelViewHolder.edt_channelName.setText(channel.getChannelName());
        channelViewHolder.edt_networkId.setText(channel.getNetworkId());
        channelViewHolder.edt_lcn.setText(channel.getLCN());
        channelViewHolder.edt_position.setText(channel.getPosition());
        channelViewHolder.edt_genreName.setText(channel.getGenre());
        networkid = channel.getNetworkId();
        channelViewHolder.setIsRecyclable(false);

        channelViewHolder.edt_channelName.setOnClickListener(new View.OnClickListener() {
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
                        db.updateByCnlPosGen(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid);
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
                    db.updateByCnlPosGen(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid);
                    updateOnDataChange();
//                    synchronized (channelViewHolder) {
//                        channelViewHolder.notify();
//                    }
//                    cursor = db.getChannelsFromNetwork(networkid);
//                    channels.clear();
//                    if (cursor.moveToFirst()) {
//                        while (!cursor.isAfterLast()) {
//                            String Network_ID = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                            String Network_Name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                            String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
////                String Channel_ID = cursor.getString(cursor.getColumnIndex("Channel_ID"));
//                            String LCN_No = cursor.getString(cursor.getColumnIndex("LCN_No"));
//                            String Genre = cursor.getString(cursor.getColumnIndex("Genre"));
//                            String Position = cursor.getString(cursor.getColumnIndex("Position"));
//                            channels.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
//                            cursor.moveToNext();
//                        }
//                    }
//                    db.close();
                }
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
                        db.updateByCnlPosGen(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid);
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
                    if ((Chgd_lcn.equals("")) || (Chgd_lcn.equals("0")) || (Chgd_lcn.equals("00")) || (Chgd_lcn.equals("000")) || (Chgd_lcn.equals("0000"))) {
                        try {
                            AlertDialogModel.generateAlertDialog(mContext, "Alert!", "LCN can not be empty!");
                            channelViewHolder.edt_lcn.setText(prv_lcn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (prv_lcn.equals(Chgd_lcn)) {

                        } else {
                            if (db.updateByLcn(Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid)) {
                                updateOnDataChange();
                            } else {
                                try {
                                    AlertDialogModel.generateAlertDialog(mContext, "Duplicate Entry...!", "Trying to change LCN " + prv_lcn + " with LCN " + Chgd_lcn);
                                    channelViewHolder.edt_lcn.setText(prv_lcn);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {

        private TextView edt_channelName, edt_networkId, edt_genreName, network_name;
        private EditText edt_lcn, edt_position;
        String networkId;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            network_name = itemView.findViewById(R.id.network_name);
            edt_channelName = (TextView) itemView.findViewById(R.id.edt_channelName);
            edt_networkId = (TextView) itemView.findViewById(R.id.edt_networkId);
            edt_lcn = (EditText) itemView.findViewById(R.id.edt_lcn);
            edt_position = (EditText) itemView.findViewById(R.id.edt_position);
            edt_genreName = itemView.findViewById(R.id.edt_genreName);
        }
    }

    public void updateOnDataChange() {
        cursor = db.getChannelsFromNetwork(networkid);
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
                channels.add(new Channel(Channel_Name, LCN_No, Position, Genre, Network_ID, ""));
                cursor.moveToNext();
            }
        }
        db.close();
    }
}
