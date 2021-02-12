package com.example.indiacastdemo;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private  final List<String> lstNetwork = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstNetwork.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstNetwork.get(position);
    }

    public void AddFragment(Fragment fragment,String network,String status){
        lstFragment.add(fragment);
        lstNetwork.add(network);
    }

}
