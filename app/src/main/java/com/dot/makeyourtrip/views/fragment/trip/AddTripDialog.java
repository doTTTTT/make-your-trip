package com.dot.makeyourtrip.views.fragment.trip;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Trip;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTripDialog implements DialogInterface.OnClickListener, Callback<TripModel> {
    private static final String TAG = AddTripDialog.class.getSimpleName();

    @Inject Trip.TripRequest tripRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private EditText editText;
    private Dialog dialog;

    public AddTripDialog(Activity activity){
        activity.getComponent().inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.dialog_add_trip);
        builder.setTitle("Trip Name");
        builder.setPositiveButton("Create", this);
        builder.setNegativeButton("Cancel", this);
        dialog = builder.show();

        editText = (EditText) dialog.findViewById(R.id.tripName);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (editText != null && !editText.getText().toString().isEmpty()) {
                    tripRequest.createTrip(manager.getToken(),
                            manager.getUserID(),
                            editText.getText().toString())
                            .enqueue(this);
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    @Override
    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
        Log.d(TAG, "" + response.code());

        switch (response.code()) {
            case 201:
                dialog.cancel();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<TripModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }
}
