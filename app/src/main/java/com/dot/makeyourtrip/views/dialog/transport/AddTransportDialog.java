package com.dot.makeyourtrip.views.dialog.transport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.DialogAddTransportBinding;
import com.dot.makeyourtrip.model.TransportModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Transport;
import com.dot.makeyourtrip.views.activity.trip.TripViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransportDialog extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, Callback<TransportModel> {
    private static final String TAG = AddTransportDialog.class.getSimpleName();

    private static final int PLACE_PICKER_REQUEST = 1;

    @Inject Transport.TransportRequest request;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private MapView departureMap;
    private MapView returnMap;
    private GoogleMap departureGoogleMap;
    private GoogleMap returnGoogleMap;
    private Marker departureMarker;
    private Marker returnMarker;
    private Type currentType;
    private DialogAddTransportBinding binding;
    private Calendar dateDeparture = Calendar.getInstance();
    private Calendar dateReturn = Calendar.getInstance();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private boolean isDepartureDateEnter = false;
    private boolean isReturnDateEnter = false;
    private String tripID;
    private TripViewModel viewModel;

    public AddTransportDialog(TripViewModel viewModel) {
        this.viewModel = viewModel;
    }

    enum Type {
        DEPARTURE,
        RETURN
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Activity) getActivity()).getComponent().inject(this);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_add_transport, null, false);

        if (getArguments() != null) {
            tripID = getArguments().getString("TRIP_ID");
            departureMap = binding.transportMapDeparture;
            returnMap = binding.transportMapReturn;

            departureMap.onCreate(savedInstanceState);
            returnMap.onCreate(savedInstanceState);
            departureMap.getMapAsync(departureMapReady);
            returnMap.getMapAsync(returnMapReady);

            binding.transportDateDeparture.setOnClickListener(this);
            binding.transportDateReturn.setOnClickListener(this);
            binding.transportLocationDeparture.setOnClickListener(this);
            binding.transportLocationReturn.setOnClickListener(this);
            binding.ok.setOnClickListener(this);
            binding.cancel.setOnClickListener(this);
        } else
            dismiss();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.transportDateDeparture:
                    currentType = Type.DEPARTURE;
                    new DatePickerDialog(v.getContext(), this, dateDeparture.get(Calendar.YEAR), dateDeparture.get(Calendar.MONTH), dateDeparture.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.transportDateReturn:
                    currentType = Type.RETURN;
                    new DatePickerDialog(v.getContext(), this, dateReturn.get(Calendar.YEAR), dateReturn.get(Calendar.MONTH), dateReturn.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.transportLocationDeparture:
                    currentType = Type.DEPARTURE;
                    PlacePicker.IntentBuilder builderD = new PlacePicker.IntentBuilder();
                    startActivityForResult(builderD.build(this.getActivity()), PLACE_PICKER_REQUEST, null);
                    break;
                case R.id.transportLocationReturn:
                    currentType = Type.RETURN;
                    PlacePicker.IntentBuilder builderR = new PlacePicker.IntentBuilder();
                    startActivityForResult(builderR.build(this.getActivity()), PLACE_PICKER_REQUEST, null);
                    break;
                case R.id.ok:
                    try {
                        if (!binding.transportName.getText().toString().isEmpty() && departureMarker != null && returnMarker != null && isDepartureDateEnter && isReturnDateEnter) {
                            Geocoder geocoder = new Geocoder(v.getContext(), Locale.getDefault());
                            Log.d("Depart: ", geocoder.getFromLocation(departureMarker.getPosition().latitude, departureMarker.getPosition().longitude, 1).get(0).getAddressLine(0));
                            Log.d("Return: ", geocoder.getFromLocation(returnMarker.getPosition().latitude, returnMarker.getPosition().longitude, 1).get(0).getAddressLine(0));
                            request.createTransport(manager.getToken(),
                                    tripID,
                                    binding.transportName.getText().toString(),
                                    geocoder.getFromLocation(departureMarker.getPosition().latitude, departureMarker.getPosition().longitude, 1).get(0).getAddressLine(0),
                                    geocoder.getFromLocation(returnMarker.getPosition().latitude, returnMarker.getPosition().longitude, 1).get(0).getAddressLine(0),
                                    format.format(dateDeparture.getTime()),
                                    format.format(dateReturn.getTime()),
                                    binding.transportType.getSelectedItem().toString().toLowerCase())
                                    .enqueue(this);
                        } else {
                            Toast.makeText(getContext(), "Please verify champ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.cancel:
                    dismiss();
                    break;
            }
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        switch (currentType) {
            case DEPARTURE:
                dateDeparture.set(Calendar.YEAR, year);
                dateDeparture.set(Calendar.MONTH, month);
                dateDeparture.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(view.getContext(), this, dateDeparture.get(Calendar.HOUR_OF_DAY), dateDeparture.get(Calendar.MINUTE), true).show();
                break;
            case RETURN:
                dateReturn.set(Calendar.YEAR, year);
                dateReturn.set(Calendar.MONTH, month);
                dateReturn.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(view.getContext(), this, dateReturn.get(Calendar.HOUR_OF_DAY), dateReturn.get(Calendar.MINUTE), true).show();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch (currentType) {
            case DEPARTURE:
                dateDeparture.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateDeparture.set(Calendar.MINUTE, minute);
                binding.transportDateDeparture.setText(format.format(dateDeparture.getTime()));
                isDepartureDateEnter = true;
                break;
            case RETURN:
                dateReturn.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateReturn.set(Calendar.MINUTE, minute);
                binding.transportDateReturn.setText(format.format(dateReturn.getTime()));
                isReturnDateEnter = true;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                final Place place = PlacePicker.getPlace(data, this.getContext());
                handleNewLocation(place.getLatLng());
                break;
        }
    }

    private void handleNewLocation(LatLng latLng) {
        switch (currentType) {
            case DEPARTURE:
                moveToNewPosition(departureGoogleMap, latLng);
                break;
            case RETURN:
                moveToNewPosition(returnGoogleMap, latLng);
                break;
        }

    }

    private void moveToNewPosition(GoogleMap map, LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 16);

        if (map != null) {
            map.animateCamera(yourLocation);
            switch (currentType) {
                case DEPARTURE:
                    if (departureMarker != null) departureMarker.remove();
                    departureMarker = map.addMarker(options);
                    break;
                case RETURN:
                    if (returnMarker != null) returnMarker.remove();
                    returnMarker = map.addMarker(options);
                    break;
            }
        }
    }

    @Override
    public void onResponse(Call<TransportModel> call, Response<TransportModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 201:
                viewModel.refresh();
                dismiss();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<TransportModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }

    private OnMapReadyCallback departureMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            AddTransportDialog.this.departureGoogleMap = googleMap;
        }
    };

    private OnMapReadyCallback returnMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            AddTransportDialog.this.returnGoogleMap = googleMap;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        departureMap.onResume();
        returnMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        departureMap.onPause();
        returnMap.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        departureMap.onStart();
        returnMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        departureMap.onStop();
        returnMap.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        departureMap.onLowMemory();
        returnMap.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        departureMap.onSaveInstanceState(outState);
        returnMap.onSaveInstanceState(outState);
    }
}
