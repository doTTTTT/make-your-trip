package com.dot.makeyourtrip.views.activity.trip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.views.activity.trip.detail.DetailFragment;
import com.dot.makeyourtrip.views.activity.trip.map.MapFragment;
import com.dot.makeyourtrip.views.activity.trip.timeline.TimelineFragment;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends FragmentStatePagerAdapter {
    private List<OnTripUpdateListener> listeners;

    public TripAdapter(FragmentManager fm) {
        super(fm);

        listeners = new ArrayList<>();
    }

    @Override
    public BaseTripFragment getItem(int position) {
        BaseTripFragment fragment = null;
        Log.d("TripAdapter", "getItem");
        switch (position) {
            case 0: fragment =  new TimelineFragment(); break;
            case 1: fragment =  new MapFragment(); break;
            case 2: fragment =  new DetailFragment(); break;
        }

        listeners.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "TimeLine";
            case 1: return "Map";
            case 2: return "Description";
            default: return super.getPageTitle(position);
        }
    }

    public void notifyListener(TripModel model){
        for (OnTripUpdateListener item : listeners){
            item.onTripUpdate(model);
        }
    }
}
