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

public class HistoryMappingFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout viewPager;
    ConnectionCheck connectionCheck;
    Bundle bundle = new Bundle();
    String ABCD = null;
    DatabaseHelper db;
    TextView network_name, txt_townDistrictState;

    public HistoryMappingFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_mapping, container, false);
        viewPager = (FrameLayout) v.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabslayout);
        network_name = v.findViewById(R.id.network_name);
        txt_townDistrictState = v.findViewById(R.id.txt_townDistrictState);
        final String networkId = getArguments().getString("networkId");
        final String networkName = getArguments().getString("networkName");
        final String createdDate = getArguments().getString("createdDate");
        final String status = getArguments().getString("status");
        try {
            ABCD= getArguments().getString("ABCD");
            bundle.putString("ABCD", ABCD);
        }catch (Exception e){
            ABCD = null;
        }

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
        bundle.putString("networkId", networkId);
        bundle.putString("createdDate", createdDate);
        bundle.putString("status", status);
        network_name.setText(networkName);
        // add  the tab at in the TabLayout

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Status"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Channels"); // set the Text for the first Tab
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Landing Page"); // set the Text for the first Tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

        TabLayout.Tab forthTab = tabLayout.newTab();
        forthTab.setText("Monitoring Points"); // set the Text for the first Tab
        tabLayout.addTab(forthTab); // add  the tab at in the TabLayout

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        StatusFragment statusFragment = new StatusFragment();
        statusFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.viewPager, statusFragment).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        StatusFragment statusFragment = new StatusFragment();
                        statusFragment.setArguments(bundle);
                        fragment = statusFragment;
                        break;
                    case 1:
                        HistoryChannelPageFragment historyChannelPageFragment = new HistoryChannelPageFragment();
                        historyChannelPageFragment.setArguments(bundle);
                        fragment = historyChannelPageFragment;
                        break;
                    case 2:
                        HistoryLandingPageFragment historyLandingPageFragment = new HistoryLandingPageFragment();
                        historyLandingPageFragment.setArguments(bundle);
                        fragment = historyLandingPageFragment;
                        break;
                    case 3:
                        HistoryMonitoringPointsFragment historyMonitoringPointsFragment = new HistoryMonitoringPointsFragment();
                        historyMonitoringPointsFragment.setArguments(bundle);
                        fragment = historyMonitoringPointsFragment;
                        break;
                    default:
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.viewPager, fragment, "FragmentTag");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
//                try {
//                    String createdDate = getArguments().getString("createdDate");
//                    String networkId = getArguments().getString("networkId");
//                    String networkName = getArguments().getString("networkName");
//                    String status = getArguments().getString("status");
//                    network_name.setText(networkName);
//                    Cursor cursor = db.getTownName(networkId);
//                    if (cursor.getCount() == 0) {
//                        txt_townDistrictState.setText("");
//                    } else {
//                        cursor.moveToFirst();
//                        while (!cursor.isAfterLast()) {
//                            txt_townDistrictState.setText(cursor.getString(0));
//                            cursor.moveToNext();
//                        }
//                    }
//                    db.close();
//                    String ABCD = getArguments().getString("ABCD");
//                    bundle.putString("ABCD", ABCD);
//                    if (ABCD.equals("abcd")) {
//                        //asasasa
//                        bundle.putString("networkId", networkId);
//                        bundle.putString("createdDate", createdDate);
//                        bundle.putString("networkName", networkName);
//                        bundle.putString("status", status);
//                        bundle.putString("ABCD", "asdfghjk");
////                        StatusFragment statusFragment = new StatusFragment();
////                        statusFragment.setArguments(bundle);
////                        fragment = statusFragment;
////                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
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
