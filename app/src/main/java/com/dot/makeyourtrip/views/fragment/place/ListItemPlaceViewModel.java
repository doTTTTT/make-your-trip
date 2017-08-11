package com.dot.makeyourtrip.views.fragment.place;

import android.databinding.BaseObservable;

import com.dot.makeyourtrip.model.PlaceModel;

public class ListItemPlaceViewModel extends BaseObservable {
    private PlaceModel model;

    public ListItemPlaceViewModel(PlaceModel model) {
        this.model = model;
    }

    public void setModel(PlaceModel model) {
        this.model = model;
    }
}
