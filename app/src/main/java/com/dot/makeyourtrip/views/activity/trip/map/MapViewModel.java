package com.dot.makeyourtrip.views.activity.trip.map;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.BaseTripModel;
import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewModel extends BaseObservable implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback,
        LocationListener {
    private static final String TAG = MapViewModel.class.getSimpleName();

    public static final String INIT_MAP = "init_map";

    public static final int REQUEST_MAP = 1;

    private GoogleMap map;
    private TripActivity activity;
    private GoogleApiClient apiClient;
    private List<LatLng> markers;
    private TripModel model;

    public MapViewModel(TripActivity activity) {
        this.activity = activity;
        this.apiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        markers = new ArrayList<>();
    }

    public void initiateMap() {
        try {
            boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.style));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.clear();
        for (RoadMap tmp : model.RoadMaps){
            createMarker(tmp);
        }
    }

    public void onClickRefresh(View view){
        activity.getViewModel().refresh();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        if (location != null) {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        map.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection Failed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_MAP);
        } else {
            initiateMap();
        }
    }

    public static boolean isAlreadyInIt(LatLng latLng, List<LatLng> markers){
        for (LatLng tmp : markers){
            if (tmp.latitude == latLng.latitude && tmp.longitude == latLng.longitude){
                return true;
            }
        }

        return false;
    }

    public void createMarker(RoadMap model){
        LatLng latLng = null;

        switch (model.EventType) {
            case "Place":
                if (model != null && ((RoadMap.Place) model).Events != null && ((RoadMap.Place) model).Events.Latitude != null) {
                    latLng = new LatLng(((RoadMap.Place) model).Events.Latitude, ((RoadMap.Place) model).Events.Longitude);
                }
                break;
        }

        if (latLng != null) {
            Log.d(TAG, "Longitude :" + latLng.longitude);
            Log.d(TAG, "Latitude :" + latLng.latitude);

            //if (!isAlreadyInIt(latLng, markers)) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(((RoadMap.Place) model).Events.Name);


            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            if (map != null) {
                map.animateCamera(yourLocation);
                map.addMarker(options);

                markers.add(latLng);
            }
            //}
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "" + intent.getAction());
            if (intent.getAction().toString().equals(INIT_MAP)){
                initiateMap();
            }
        }
    };

    public BroadcastReceiver getReceiver() {
        return receiver;
    }

    public GoogleApiClient getApiClient() {
        return apiClient;
    }

    public void setModel(TripModel model) {
        this.model = model;

        if (map != null) {
            map.clear();
            for (RoadMap tmp : model.RoadMaps){
                createMarker(tmp);
            }
        }

        notifyChange();
    }
}
