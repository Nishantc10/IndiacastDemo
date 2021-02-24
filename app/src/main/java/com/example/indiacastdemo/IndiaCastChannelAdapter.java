package com.example.indiacastdemo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.Channel;
import com.example.indiacastdemo.Model.IndiaCastChannel;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class IndiaCastChannelAdapter extends RecyclerView.Adapter<IndiaCastChannelAdapter.IndiaCastChannelHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<IndiaCastChannel> indiaCastChannels;
    ArrayList<IdentifiableObject> indiaCastchannelist = new ArrayList<>();
    Cursor cursor = null;
    SpinnerDialog chanels;
    String indiacastChannelName, IStatus, Lcn, Position, networkid;

    public IndiaCastChannelAdapter(Context context, ArrayList<IndiaCastChannel> indiaCastChannels) {
        this.mContext = context;
        this.indiaCastChannels = indiaCastChannels;
    }

    @NonNull
    @Override
    public IndiaCastChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_indiacast_channel, viewGroup, false);
        IndiaCastChannelHolder indiaCastChannelHolder = new IndiaCastChannelHolder(v);
        db = new DatabaseHelper(mContext);
        return indiaCastChannelHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IndiaCastChannelHolder indiaCastChannelHolder, int position) {
        IndiaCastChannel indiaCastChannel = indiaCastChannels.get(position);
        indiaCastChannelHolder.edt_indiacastChannelName.setText(indiaCastChannel.getIndiaCastChannelName());
        indiaCastChannelHolder.edt_networkId.setText(indiaCastChannel.getNetworkId());
        indiaCastChannelHolder.edt_indiacastcposition.setText(indiaCastChannel.getCPosition());
        indiaCastChannelHolder.edt_lcn.setText(indiaCastChannel.getLCN());
        indiaCastChannelHolder.edt_position.setText(indiaCastChannel.getPosition());
        indiaCastChannelHolder.edt_indiacastcposition.setText(indiaCastChannel.getPosition());
        indiaCastChannelHolder.edt_indiacast_status.setText(indiaCastChannel.getIndiacast_status());
        networkid = indiaCastChannel.getNetworkId();
        indiaCastChannelHolder.setIsRecyclable(false);

        indiaCastChannelHolder.edt_indiacast_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = db.getAllIndiaCastStatus();
                indiaCastchannelist.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String name = cursor.getString(cursor.getColumnIndex("IStatus"));
                        indiaCastchannelist.add(new IdentifiableObjectImpl(name, null, 0, R.drawable.ic_checkbox_marked_circle_outline_white_18dp));
                        cursor.moveToNext();
                    }
                }
                db.close();
                chanels = new SpinnerDialog((Activity) mContext, indiaCastchannelist, "Select or Search Status", R.drawable.ic_edit_location_black_24dp, R.drawable.ic_my_location_black_2dp);
                chanels.showSpinerDialog();
                chanels.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(IdentifiableObject item, int position) {
                        String getGenre = item.getTitle();
                        indiaCastChannelHolder.edt_indiacast_status.setText(getGenre);
                        Lcn = indiaCastChannelHolder.edt_lcn.getText().toString();
                        indiacastChannelName = indiaCastChannelHolder.edt_indiacastChannelName.getText().toString();
                        IStatus = indiaCastChannelHolder.edt_indiacast_status.getText().toString();
                        Position = indiaCastChannelHolder.edt_position.getText().toString();
                        if (db.updateByIStatus(indiacastChannelName, IStatus, Lcn, Position, networkid)) {
                            updateOnDataChange();
                        } else {

                        }
                    }
                });
            }
        });
        indiaCastChannelHolder.edt_indiacast_status.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return indiaCastChannels.size();
    }

    public static class IndiaCastChannelHolder extends RecyclerView.ViewHolder {

        private TextView edt_indiacastChannelName, edt_networkId, edt_indiacast_status, network_name, edt_lcn, edt_position;
        EditText edt_indiacastcposition;
        String networkId;

        public IndiaCastChannelHolder(@NonNull View itemView) {
            super(itemView);
            network_name = itemView.findViewById(R.id.network_name);
            edt_indiacastChannelName = (TextView) itemView.findViewById(R.id.edt_indiacastChannelName);
            edt_networkId = (TextView) itemView.findViewById(R.id.edt_indiacastNetworkId);
            edt_lcn = (TextView) itemView.findViewById(R.id.edt_indiaCastlcn);
            edt_indiacastcposition = (EditText) itemView.findViewById(R.id.edt_indiacastcposition);
            edt_position = (TextView) itemView.findViewById(R.id.edt_indiacastposition);
            edt_indiacast_status = itemView.findViewById(R.id.edt_indiacast_status);
        }
    }

    public void updateOnDataChange() {
        cursor = db.getIndiaCastChannels(networkid);
        indiaCastChannels.clear();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String Network_ID = cursor.getString(cursor.getColumnIndex("NetworkID"));
                String Channel_Name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
                String LCN_No = cursor.getString(cursor.getColumnIndex("LCN"));
                String IStatusID = cursor.getString(cursor.getColumnIndex("IStatusID"));
                String Position = cursor.getString(cursor.getColumnIndex("Position"));
                Cursor IStatusIdcrs = db.getIndiaCastChannelStatusById(IStatusID);
                if (IStatusIdcrs.moveToFirst()) {
                    IStatusID = IStatusIdcrs.getString(IStatusIdcrs.getColumnIndex("IStatus"));
                } else {
                    IStatusID = null;
                }
                indiaCastChannels.add(new IndiaCastChannel(Channel_Name, LCN_No, Position, IStatusID, Network_ID, ""));
                cursor.moveToNext();
            }
        }
        db.close();
    }
}
