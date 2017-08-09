package com.dot.makeyourtrip.views.fragment.trip;

import android.util.DisplayMetrics;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentTripBinding;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends BaseFragment<FragmentTripBinding> implements TripContract.View {
    private FragmentTripBinding binding;
    private TripAdapter tripAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_trip;
    }

    @Override
    public void initView(FragmentTripBinding binding) {
        this.binding = binding;
        TripViewModel viewModel = new TripViewModel(this);
        getComponent().inject(viewModel);
        binding.setViewModel(viewModel);
        List<TripModel> list = new ArrayList<>();

        tripAdapter = new TripAdapter(getContext(), list);

        binding.tripList.setClipToPadding(false);
        binding.tripList.setPadding(dpToPx(30), dpToPx(50), dpToPx(30), dpToPx(50));
        binding.tripList.setPageMargin(30);
        binding.tripList.setAdapter(tripAdapter);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void setList(List<TripModel> list) {
        tripAdapter.notifyDataSetChanged();
    }
}
