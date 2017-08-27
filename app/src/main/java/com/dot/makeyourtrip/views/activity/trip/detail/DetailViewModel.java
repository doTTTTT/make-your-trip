package com.dot.makeyourtrip.views.activity.trip.detail;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Trip;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;

import java.util.Calendar;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends BaseObservable implements DatePickerDialog.OnDateSetListener, Callback<TripModel> {
    private static final String TAG = DetailViewModel.class.getSimpleName();

    @Inject Trip.TripRequest tripRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private TripActivity activity;
    private Calendar dateStart;
    private Calendar dateEnd;
    private Type currentType;
    private TripModel model;

    public void setTrip(TripModel trip) {
        this.model = trip;
        notifyChange();
    }

    @Override
    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                setTrip(response.body());
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

    private enum Type {
        START,
        END
    }

    public DetailViewModel(TripActivity activity){
        this.activity = activity;

        activity.getComponent().inject(this);
    }

    public void onClickRefresh(View view){
        activity.getViewModel().refresh();
    }

    public void onClickDateStart(View view){
        currentType = Type.START;
        new DatePickerDialog(view.getContext(), this,
                dateStart.get(Calendar.YEAR),
                dateStart.get(Calendar.MONTH),
                dateStart.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickDateEnd(View view){
        currentType = Type.END;
        new DatePickerDialog(view.getContext(), this,
                dateEnd.get(Calendar.YEAR),
                dateEnd.get(Calendar.MONTH),
                dateEnd.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickDescription(View view){
        final EditText description = new EditText(view.getContext());
        description.setText(model.Description);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Description")
                .setView(description)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tripRequest.modifyTrip(manager.getToken(), model.ID, description.getText().toString()).enqueue(DetailViewModel.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public String getDateStart(){
        return "Date Start";
    }

    public String getDateEnd(){
        return "Date End";
    }

    public String getDescription(){
        return model != null ? model.Description : "";
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
