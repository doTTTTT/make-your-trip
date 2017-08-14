package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.BaseObservable;

import com.dot.makeyourtrip.model.BaseTripModel;
import com.dot.makeyourtrip.model.RoadMap;

public class ListItemTimeLineViewModel extends BaseObservable {
    private RoadMap model;

    public ListItemTimeLineViewModel(RoadMap model) {
        this.model = model;
    }

    public void setModel(RoadMap model){
        this.model = model;
        notifyChange();
    }

    public String getTitle(){
        return "Title";
    }
}
