package com.dot.makeyourtrip.views.fragment.trip;

import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.view.View;

import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Trip;

import javax.inject.Inject;

public class ListItemTripEmptyViewModel extends BaseObservable implements DialogInterface.OnDismissListener {
    @Inject Trip.TripRequest tripRequest;

    private Activity activity;
    private TripViewModel viewModel;

    public ListItemTripEmptyViewModel(Activity activity, TripViewModel viewModel){
        this.activity = activity;
        this.viewModel = viewModel;

        activity.getComponent().inject(this);
    }

    public void onClickAdd(View view) {
        AddTripDialog dialog = new AddTripDialog(activity);
        dialog.setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        viewModel.onRefresh();
    }
}
