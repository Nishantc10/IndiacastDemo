package com.example.indiacastdemo;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.History_Network;

import java.util.ArrayList;

public class HistoryNetworkAdapter extends RecyclerView.Adapter<HistoryNetworkAdapter.MyViewHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<History_Network> al;
    Cursor cursor;

    public HistoryNetworkAdapter(Context mContext, ArrayList<History_Network> al) {
        this.mContext = mContext;
        this.al = al;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_history_network, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String networkId = viewHolder.history_network_id.getText().toString();
                String networkName = viewHolder.history_network_name.getText().toString();
                String createdDate = viewHolder.history_network_date2.getText().toString();
                String status = viewHolder.history_network_status2.getText().toString();
//                if(status.equals("Submitted")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setMessage("You have already submitted this Network!");
//                    builder.setCancelable(false);
//                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.show();
////                    Toast.makeText(mContext, "Network already submitted!", Toast.LENGTH_SHORT).show();
//                } else if(status.equals("Approved")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setMessage("Network is approved!");
//                    builder.setCancelable(true);
//                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.show();
////                    Toast.makeText(mContext, "Network already approved!", Toast.LENGTH_SHORT).show();
//                }else {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new HistoryMappingFragment();
                Bundle args = new Bundle();
                args.putString("networkId", networkId);
                args.putString("networkName", networkName);
                args.putString("createdDate", createdDate);
                args.putString("status", status);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
//                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        History_Network history_network = al.get(position);
        myViewHolder.history_network_id.setText(history_network.getNetwork_ID());
        myViewHolder.history_network_name.setText(history_network.getNetwork_Name());
        myViewHolder.img_network.setImageDrawable(history_network.getPhoto());
        myViewHolder.history_network_status2.setText(history_network.getStatus());
        myViewHolder.history_network_date2.setText(history_network.getCreated_Date());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView history_network_name, history_network_date2, history_network_status2, history_network_id;
        private ImageView img_network;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            history_network_id = (TextView) itemView.findViewById(R.id.history_network_id);
            history_network_name = (TextView) itemView.findViewById(R.id.history_network_name);
            img_network = (ImageView) itemView.findViewById(R.id.img_network);
            history_network_status2 = (TextView) itemView.findViewById(R.id.history_network_status2);
            history_network_date2 = (TextView) itemView.findViewById(R.id.history_network_date2);
        }
    }
}
