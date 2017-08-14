package com.dot.makeyourtrip.views.fragment.trip;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;

public class ListItemTripViewModel extends BaseObservable {
    private TripModel model;

    public ListItemTripViewModel(TripModel model) {
        this.model = model;
    }

    public void setModel(TripModel model) {
        this.model = model;
    }

    public void onClickTrip(View view) {
        Intent intent = new Intent(view.getContext(), TripActivity.class);
        intent.putExtra(TripActivity.EXTRA_ID, model.ID);

        ActivityCompat.startActivity(view.getContext(), intent, null);
    }

    public String getName(){
        return model.Name;
    }

    public String getDescription() {
        return "Description";
    }

    public String getNumberPlace() {
        return model.RoadMaps.size() + " Places";
    }
}
