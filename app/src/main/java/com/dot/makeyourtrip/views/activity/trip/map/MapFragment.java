package com.dot.makeyourtrip.views.activity.trip.map;

import android.content.IntentFilter;
import android.os.Bundle;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentMapBinding;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.trip.BaseTripFragment;
import com.google.android.gms.maps.MapView;

public class MapFragment extends BaseTripFragment<FragmentMapBinding> {
    private MapView mapView;
    private MapViewModel viewModel;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_map;
    }

    @Override
    public void initView(FragmentMapBinding binding) {
        viewModel = new MapViewModel((Activity) getActivity());

        mapView = binding.maps;

        mapView.onCreate(getSaveBundle());
        mapView.getMapAsync(viewModel);

        getActivity().registerReceiver(viewModel.getReceiver(), new IntentFilter(MapViewModel.INIT_MAP));

        binding.setViewModel(viewModel);
    }

    @Override
    public void onTripUpdate(TripModel tripModel) {
        viewModel.setModel(tripModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        viewModel.getApiClient().connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (viewModel.getApiClient().isConnected()){
            viewModel.getApiClient().disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        getActivity().unregisterReceiver(viewModel.getReceiver());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // TODO REFRESH ?
    }
}
