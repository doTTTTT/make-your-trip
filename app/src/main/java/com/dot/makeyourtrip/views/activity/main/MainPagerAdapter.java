package com.dot.makeyourtrip.views.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dot.makeyourtrip.views.fragment.place.PlaceFragment;
import com.dot.makeyourtrip.views.fragment.setting.SettingFragment;
import com.dot.makeyourtrip.views.fragment.trip.TripFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TripFragment();
            case 1: return new PlaceFragment();
            case 2: return new SettingFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
