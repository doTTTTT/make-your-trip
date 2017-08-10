package com.dot.makeyourtrip.views.fragment.trip;

import android.databinding.BaseObservable;
import android.util.Log;

import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Trip;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripViewModel extends BaseObservable implements Callback<List<TripModel>> {
    private static final String TAG = TripViewModel.class.getSimpleName();

    @Inject Trip.TripRequest tripRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private TripContract.View view;

    public TripViewModel(TripContract.View view, MYTComponent component){
        component.inject(this);
        this.view = view;

        tripRequest.getTripUser(manager.getUserID()).enqueue(this);
    }

    @Override
    public void onResponse(Call<List<TripModel>> call, Response<List<TripModel>> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                view.setList(response.body());
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<List<TripModel>> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }
}
