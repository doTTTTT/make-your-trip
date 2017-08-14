package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.lodge.LodgeActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import dagger.Provides;

public class TimelineViewModel extends BaseObservable implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TimelineViewModel.class.getSimpleName();

    public static final int PLACE_PICKER_REQUEST = 1;

    private Activity activity;
    private TimelineAdapter adapter;
    private TripModel model;
    private TimelineContract.View view;

    public TimelineViewModel(Activity activity, TimelineAdapter adapter, TimelineContract.View view) {
        this.activity = activity;
        this.adapter = adapter;
        this.view = view;
    }

    public void onClickAddPlace(View view) {
        this.view.closeFabMenu();
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            ActivityCompat.startActivityForResult(activity, builder.build(activity), PLACE_PICKER_REQUEST, null);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onClickAddLodge(View view) {
        this.view.closeFabMenu();
        Intent intent = new Intent(view.getContext(), LodgeActivity.class);
        intent.putExtra("TRIP_ID", model.ID);

        view.getContext().startActivity(intent);
    }

    public void onClickAddTransport(View view) {
        this.view.closeFabMenu();
    }

    public void setModel(TripModel model) {
        this.model = model;
        adapter.setList(model.RoadMaps);
        notifyChange();
    }

    @Override
    public void onRefresh() {
        view.setRefreshing(false);
    }
}
