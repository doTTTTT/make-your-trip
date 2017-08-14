package com.dot.makeyourtrip.views.activity.trip.detail;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentDetailBinding;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.BaseFragment;
import com.dot.makeyourtrip.views.activity.trip.BaseTripFragment;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;

public class DetailFragment extends BaseTripFragment<FragmentDetailBinding> {
    @Override
    public int getLayoutID() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initView(FragmentDetailBinding binding) {
        binding.setViewModel(new DetailViewModel((TripActivity) getActivity()));
    }

    @Override
    public void onTripUpdate(TripModel tripModel) {

    }
}
