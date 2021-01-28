package com.example.indiacastdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indiacastdemo.Model.Channel;

import java.util.ArrayList;

public class IndiaCastChannelAdapter extends RecyclerView.Adapter<IndiaCastChannelAdapter.IndiaCastChannelHolder> {


    public IndiaCastChannelAdapter(Context context, ArrayList<Channel> lst_channel) {

    }

    @NonNull
    @Override
    public IndiaCastChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaCastChannelHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class IndiaCastChannelHolder extends RecyclerView.ViewHolder {
        public IndiaCastChannelHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
