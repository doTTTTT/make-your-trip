package com.dot.makeyourtrip.views.activity.place;

import android.databinding.BaseObservable;

import com.dot.makeyourtrip.model.PlaceModel;

public class ListItemSearchPlaceViewModel extends BaseObservable {
    private PlaceModel model;

    public ListItemSearchPlaceViewModel(PlaceModel model) {
        this.model = model;
    }

    public void setModel(PlaceModel model) {
        this.model = model;
        notifyChange();
    }
}
