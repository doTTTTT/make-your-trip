package com.dot.makeyourtrip.views.fragment.trip;

import android.databinding.BaseObservable;
import android.view.View;

import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Trip;

import javax.inject.Inject;

public class ListItemTripEmptyViewModel extends BaseObservable {
    @Inject Trip.TripRequest tripRequest;

    private Activity activity;

    public ListItemTripEmptyViewModel(Activity activity){
        this.activity = activity;

        activity.getComponent().inject(this);
    }

    public void onClickAdd(View view) {
        AddTripDialog dialog = new AddTripDialog(activity);
    }
}
