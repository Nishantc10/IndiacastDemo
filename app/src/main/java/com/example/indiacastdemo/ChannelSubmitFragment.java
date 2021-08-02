package com.example.indiacastdemo;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ChannelSubmitFragment extends Fragment {
    private TabLayout tabLayout;
    String networkId;
    Bundle bundle;
    public ChannelSubmitFragment() {
        // Required empty public constructor
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_channel_submit, container, false);
        networkId = getArguments().getString("networkId");
        bundle = new Bundle();
        bundle.putString("networkId", networkId);

        tabLayout = (TabLayout) v.findViewById(R.id.tabslayout);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("All Channels"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("IndiaCast Channels"); // set the Text for the first Tab
        tabLayout.addTab(secondTab); // add  the tab at in the TabLayout

        SubmitChannelListFragment submitChannelListFragment = new SubmitChannelListFragment();
        submitChannelListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.viewPager2, submitChannelListFragment).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        SubmitChannelListFragment submitChannelListFragment = new SubmitChannelListFragment();
                        submitChannelListFragment.setArguments(bundle);
                        fragment = submitChannelListFragment;
                        break;
                    case 1:
                        IndiaCastChannelFragment indiaCastChannelFragment = new IndiaCastChannelFragment();
                        indiaCastChannelFragment.setArguments(bundle);
                        fragment = indiaCastChannelFragment;
                        break;
                    default:
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.viewPager2, fragment, "FragmentTag");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
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