package com.example.indiacastdemo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indiacastdemo.Model.StatusMaster;

import java.util.ArrayList;

public class StatusViewAdapter extends RecyclerView.Adapter<StatusViewAdapter.StatusViewHolder> {
    Context mContext;
    ArrayList<StatusMaster> statusMasters;

    public StatusViewAdapter(Context mContext, ArrayList<StatusMaster> statusMasters) {
        this.mContext = mContext;
        this.statusMasters = statusMasters;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.statuscard, viewGroup, false);
        StatusViewAdapter.StatusViewHolder statusViewHolder = new StatusViewAdapter.StatusViewHolder(v);
        return statusViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder statusViewHolder, int position) {
        StatusMaster statusMaster = statusMasters.get(position);
        statusViewHolder.img_status.setImageDrawable(statusMaster.getPhoto());
        statusViewHolder.txt_status.setText(statusMaster.getStatus());
        statusViewHolder.txt_by.setText(statusMaster.getBy());
        statusViewHolder.txt_at.setText(statusMaster.getAt());
        statusViewHolder.txt_date.setText(statusMaster.getCreated_Date());
    }

    @Override
    public int getItemCount() {
        return statusMasters.size();
    }

   public static class StatusViewHolder extends RecyclerView.ViewHolder {

    private ImageView img_status;
    private TextView txt_status;
    private TextView txt_by;
    private TextView txt_at;
    private TextView txt_date;

    public StatusViewHolder(@NonNull View itemView)  {
        super(itemView);
        img_status = (ImageView) itemView.findViewById(R.id.img_status);
        txt_status = (TextView) itemView.findViewById(R.id.txt_status);
        txt_by = (TextView) itemView.findViewById(R.id.txt_by);
        txt_at = (TextView) itemView.findViewById(R.id.txt_at);
        txt_date = itemView.findViewById(R.id.txt_date);
    }
}
}
