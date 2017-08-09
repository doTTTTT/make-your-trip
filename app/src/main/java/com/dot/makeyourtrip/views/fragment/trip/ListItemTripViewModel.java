package com.dot.makeyourtrip.views.fragment.trip;

import android.databinding.BaseObservable;

import com.dot.makeyourtrip.model.TripModel;

public class ListItemTripViewModel extends BaseObservable {
    private TripModel model;

    public ListItemTripViewModel(TripModel model) {
        this.model = model;
    }

    public void setModel(TripModel model) {
        this.model = model;
    }
}
