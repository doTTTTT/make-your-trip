package com.dot.makeyourtrip.views.activity.trip;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ActivityTripBinding;
import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.trip.map.MapViewModel;
import com.dot.makeyourtrip.views.activity.trip.timeline.TimelineViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripActivity extends Activity<ActivityTripBinding> implements Callback<PlaceModel> {
    private static final String TAG = TripActivity.class.getSimpleName();

    public static final String EXTRA_ID = "extra_id";

    @Inject com.dot.makeyourtrip.utils.type.Place.PlaceRequest placeRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private ActivityTripBinding binding;
    private TripAdapter adapter;
    private TripViewModel viewModel;
    private String tripID;

    @Override
    protected void initView(ActivityTripBinding binding) {
        Log.d("Trip", "" + getIntent());

        if (getIntent() != null) {
            getComponent().inject(this);
            tripID = getIntent().getStringExtra(EXTRA_ID);
            binding = DataBindingUtil.setContentView(this, R.layout.activity_trip);
            adapter = new TripAdapter(getSupportFragmentManager());
            viewModel = new TripViewModel(getComponent(), tripID, adapter);

            binding.setViewModel(viewModel);
            binding.viewPager.setAdapter(adapter);
            binding.viewPager.setOffscreenPageLimit(3);
            binding.tabHost.setupWithViewPager(binding.viewPager);
        } else {
            Toast.makeText(getApplicationContext(), "No trip id", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_trip;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TimelineViewModel.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                final Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Log.d(TAG, "Lat: " + place.getLatLng().latitude);
                Log.d(TAG, "Lon: " + place.getLatLng().longitude);
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                placeRequest.createPlace(manager.getToken(),
                        manager.getUserID(),
                        tripID, place.getId(),
                        place.getName().toString(),
                        place.getLatLng().latitude,
                        place.getLatLng().longitude).enqueue(this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MapViewModel.REQUEST_MAP:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults.length > 1
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    sendBroadcast(new Intent(MapViewModel.INIT_MAP));
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<PlaceModel> call, Response<PlaceModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 201:
                viewModel.refresh();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<PlaceModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }

    public TripViewModel getViewModel() {
        return viewModel;
    }
}
