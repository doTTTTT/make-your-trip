package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.BaseObservable;

import com.dot.makeyourtrip.model.RoadMap;

public class ListItemTimeLineTransportViewModel extends BaseObservable {
    private RoadMap.Transport model;

    public ListItemTimeLineTransportViewModel(RoadMap.Transport model) {
        this.model = model;
    }

    public void setModel(RoadMap.Transport model) {
        this.model = model;
        notifyChange();
    }

    public String getTitle(){
        return "Title";
    }
}
