package com.example.indiacastdemo;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.indiacastdemo.Model.ConnectionCheck;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;

public class MappingFragment extends Fragment {

    private TabLayout tabLayout;
    private FrameLayout viewPager;
    ConnectionCheck connectionCheck;
    Bundle bundle;
    private DatabaseHelper db;
    private TextView network_name, txt_townDistrictState;
    String networkId;
    String networkName;

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
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.preview_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preview:
                final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
                dialogBuilder.setCancelable(false);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.channel_preview_layout, null);
                ImageView btn_cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);

                TableLayout tableLayout = dialogView.findViewById(R.id.ChannelsTableLayout);
                Cursor cursor = db.getChannelsFromNetwork(networkId);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.channel_preview_table_item, null, false);
                        TextView tbl_channelName = tableRow.findViewById(R.id.tbl_channelName);
                        TextView tbl_lcn = tableRow.findViewById(R.id.tbl_lcn);
                        TextView tbl_position = tableRow.findViewById(R.id.tbl_position);
                        TextView tbl_genreName = tableRow.findViewById(R.id.tbl_genreName);
                        tbl_channelName.setText(cursor.getString(cursor.getColumnIndex("Channel_Name")));
                        tbl_lcn.setText(cursor.getString(cursor.getColumnIndex("LCN_No")));
                        tbl_position.setText(cursor.getString(cursor.getColumnIndex("Position")));
                        tbl_genreName.setText(cursor.getString(cursor.getColumnIndex("Genre")));
                        tableLayout.addView(tableRow);
                        cursor.moveToNext();
                    }
                }
                db.close();
                btn_cancel.setOnClickListener(view -> {
                    dialogBuilder.dismiss();
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
