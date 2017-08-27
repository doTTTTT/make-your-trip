package com.dot.makeyourtrip.views.fragment.trip;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentTripBinding;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.android.BaseFragment;
import com.dot.makeyourtrip.views.activity.login.LoginActivity;
import com.dot.makeyourtrip.views.activity.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends BaseFragment<FragmentTripBinding> implements TripContract.View, ViewPager.OnPageChangeListener {
    private FragmentTripBinding binding;
    private TripAdapter tripAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_trip;
    }

    @Override
    public void initView(FragmentTripBinding binding) {
        this.binding = binding;
        TripViewModel viewModel = new TripViewModel(this, getComponent());
        binding.setViewModel(viewModel);
        List<TripModel> list = new ArrayList<>();

        tripAdapter = new TripAdapter((Activity) getActivity(), list, viewModel);

        binding.tripList.setClipToPadding(false);
        binding.tripList.addOnPageChangeListener(this);
        binding.tripList.setPadding(dpToPx(30), dpToPx(10), dpToPx(30), dpToPx(50));
        binding.tripList.setPageMargin(30);
        binding.tripList.setAdapter(tripAdapter);
        //binding.refresh.setOnRefreshListener(viewModel);
        binding.refresh.setEnabled(false);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void setList(List<TripModel> list) {
        Log.d("Size", "" + list.size());
        tripAdapter.setList(list);
    }

    @Override
    public void setRefreshing(Boolean refreshing) {
        binding.refresh.setRefreshing(refreshing);
    }

    @Override
    public void logOut(){
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        byte[] background = tripAdapter.getByteFromItem(position);

        if (background != null) {
            ((MainActivity) getActivity()).setBackground(background);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
