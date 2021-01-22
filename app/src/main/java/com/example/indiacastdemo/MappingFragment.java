package com.example.indiacastdemo;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;

public class MappingFragment extends Fragment {

    private TabLayout tabLayout;
    private FrameLayout viewPager;
    ConnectionCheck connectionCheck;
    Bundle bundle;
    private DatabaseHelper db;
    private TextView network_name, txt_townDistrictState;

    public MappingFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapping2, container, false);
        viewPager = (FrameLayout) v.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabslayout);
        network_name = v.findViewById(R.id.network_name);
        txt_townDistrictState = v.findViewById(R.id.txt_townDistrictState);
        final String networkId = getArguments().getString("networkId");
        final String networkName = getArguments().getString("networkName");
        db = new DatabaseHelper(getContext());
        Cursor cursor = db.getTownName(networkId);
        if (cursor.getCount() == 0) {
            txt_townDistrictState.setText("");
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                txt_townDistrictState.setText(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        db.close();
//        Log.d( "networkId: ",networkId);
        bundle = new Bundle();
        bundle.putString("networkId", networkId);
        bundle.putString("networkName", networkName);
        network_name.setText(networkName);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Channels"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Landing Page"); // set the Text for the first Tab
        tabLayout.addTab(secondTab); // add  the tab at in the TabLayout

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Monitoring Points"); // set the Text for the first Tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

//        TabLayout.Tab forthTab = tabLayout.newTab();
//        forthTab.setText("Status"); // set the Text for the first Tab
//        tabLayout.addTab(forthTab); // add  the tab at in the TabLayout

//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        ChannelPageFragment channelPageFragment = new ChannelPageFragment();
        channelPageFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.viewPager, channelPageFragment).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        ChannelPageFragment channelPageFragment = new ChannelPageFragment();
                        channelPageFragment.setArguments(bundle);
                        fragment = channelPageFragment;
                        break;
                    case 1:
                        LandingPageFragment landingPageFragment = new LandingPageFragment();
                        landingPageFragment.setArguments(bundle);
                        fragment = landingPageFragment;
                        break;

                    case 2:
                        MonitoringPointsFragment monitoringPointsFragment = new MonitoringPointsFragment();
                        monitoringPointsFragment.setArguments(bundle);
                        fragment = monitoringPointsFragment;
                        break;

//                    case 3:
//                        fragment = new StatusFragment();
//                        break;

                    default:
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.viewPager, fragment, "FragmentTag");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return v;
    }
}
