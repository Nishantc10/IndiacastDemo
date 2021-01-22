package com.example.indiacastdemo;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import android.util.Log;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String networkId;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs,String networkId) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.networkId = networkId;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("networkId",networkId);
        Log.d("getItem: ",networkId);
        switch (position) {
            case 0:
                ChannelPageFragment channelPageFragment = new ChannelPageFragment();
                channelPageFragment.setArguments(bundle);
                return channelPageFragment;
            case 1:
                LandingPageFragment landingPageFragment = new LandingPageFragment();
                return landingPageFragment;
            case 2:
                MonitoringPointsFragment monitoringPointsFragment = new MonitoringPointsFragment();
                return monitoringPointsFragment;
                case 3:
                StatusFragment statusFragment = new StatusFragment();
                return statusFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}