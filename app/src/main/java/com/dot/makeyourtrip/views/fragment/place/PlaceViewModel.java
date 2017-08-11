package com.dot.makeyourtrip.views.fragment.place;

import android.databinding.BaseObservable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Place;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceViewModel extends BaseObservable implements SwipeRefreshLayout.OnRefreshListener, Callback<PlaceModel> {
    private static final String TAG = PlaceViewModel.class.getSimpleName();

    @Inject Place.PlaceRequest placeRequest;
    @Inject MYTManager manager;

    public PlaceViewModel(MYTComponent component, PlaceAdapter adapter) {
        component.inject(this);

        //placeRequest.getPlace(manager.getUserID()).enqueue(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onResponse(Call<PlaceModel> call, Response<PlaceModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                break;
        }
    }

    @Override
    public void onFailure(Call<PlaceModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }
}
