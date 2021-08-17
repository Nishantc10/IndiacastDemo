package com.example.indiacastdemo;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.He_Network;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    DatabaseHelper db;
    ArrayList<He_Network> al;
    Cursor cursor;
    public  RecyclerViewAdapter(Context mContext, ArrayList<He_Network> al) {
        this.mContext = mContext;
        this.al = al;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_he_network,viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String networkId = viewHolder.he_network_id.getText().toString();
                String networkName = viewHolder.he_network_name.getText().toString();
                Log.d("netkid: ",networkId);
                Log.d("networkName: ",networkName);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new MappingFragment();
                Bundle args = new Bundle();
                args.putString("networkId", networkId);
                args.putString("networkName", networkName);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        He_Network he_network = al.get(position);
        myViewHolder.he_network_id.setText(he_network.getNetwork_ID());
        myViewHolder.he_network_location.setText(he_network.getLocation());
        myViewHolder.he_network_name.setText(he_network.getNetwork_Name());
        myViewHolder.img_networklogo.setImageDrawable(he_network.getPhoto());
        myViewHolder.he_networek_status2.setText(he_network.getStatus());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView he_network_name;
        private TextView he_network_location;
        private TextView he_network_id;
        private ImageView img_networklogo;
        private TextView he_networek_status2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            he_network_id = (TextView) itemView.findViewById(R.id.he_network_id);
            he_network_location = (TextView) itemView.findViewById(R.id.he_network_location);
            he_network_name = (TextView) itemView.findViewById(R.id.he_network_name);
            img_networklogo = (ImageView) itemView.findViewById(R.id.img_network);
            he_networek_status2 = (TextView) itemView.findViewById(R.id.he_networek_status2);
        }
    }
}
