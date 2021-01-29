package com.example.indiacastdemo;

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
import com.example.indiacastdemo.Model.IndiaCastChannel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IndiaCastChannelAdapter extends RecyclerView.Adapter<IndiaCastChannelAdapter.IndiaCastChannelHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<IndiaCastChannel> indiaCastChannels;
    ArrayList<IdentifiableObject> indiaCastchannelist = new ArrayList<>();
    Cursor cursor = null;
    SpinnerDialog   chanels;
    String Channel_name, Genre_name, prv_lcn, Chgd_lcn, Position, networkid;

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
        return indiaCastChannelHolder;    }

    @Override
    public void onBindViewHolder(@NonNull IndiaCastChannelHolder indiaCastChannelHolder, int position) {
        IndiaCastChannel indiaCastChannel = indiaCastChannels.get(position);
        indiaCastChannelHolder.edt_channelName.setText(indiaCastChannel.getIndiaCastChannelName()       );
        indiaCastChannelHolder.edt_networkId.setText(indiaCastChannel.getNetworkId());
        indiaCastChannelHolder.edt_lcn.setText(indiaCastChannel.getLCN());
        indiaCastChannelHolder.edt_position.setText(indiaCastChannel.getPosition());
        indiaCastChannelHolder.edt_genreName.setText(indiaCastChannel.getGenre());
        networkid = indiaCastChannel.getNetworkId();
        indiaCastChannelHolder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return indiaCastChannels.size();
    }

    public static class IndiaCastChannelHolder extends RecyclerView.ViewHolder {

        private TextView edt_channelName, edt_networkId, edt_genreName, network_name,edt_lcn, edt_position;
        String networkId;

        public IndiaCastChannelHolder(@NonNull View itemView) {
            super(itemView);
            network_name = itemView.findViewById(R.id.network_name);
            edt_channelName = (TextView) itemView.findViewById(R.id.edt_indiacastChannelName);
            edt_networkId = (TextView) itemView.findViewById(R.id.edt_indiacastNetworkId);
            edt_lcn = (TextView) itemView.findViewById(R.id.edt_indiaCastlcn);
            edt_position = (TextView) itemView.findViewById(R.id.edt_indiacastposition);
            edt_genreName = itemView.findViewById(R.id.edt_indiacastgenreName);
        }
    }
}
