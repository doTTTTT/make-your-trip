package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentTimelineBinding;
import com.dot.makeyourtrip.model.BaseTripModel;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.trip.BaseTripFragment;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;

import java.util.ArrayList;

public class TimelineFragment extends BaseTripFragment<FragmentTimelineBinding> implements TimelineContract.View {
    private TimelineAdapter adapter;
    private TimelineViewModel viewModel;
    private FragmentTimelineBinding binding;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_timeline;
    }

    @Override
    public void initView(FragmentTimelineBinding binding) {
        this.binding = binding;

        adapter = new TimelineAdapter((TripActivity) getActivity());
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        viewModel = new TimelineViewModel((TripActivity) getActivity(), adapter, this);

        binding.tripList.setAdapter(adapter);
        binding.setViewModel(viewModel);
        //binding.refresh.setOnRefreshListener(viewModel);
        touchHelper.attachToRecyclerView(binding.tripList);
    }

    @Override
    public void onTripUpdate(TripModel tripModel) {
        viewModel.setModel(tripModel);
    }

    @Override
    public void closeFabMenu() {
        binding.fabMenu.collapse();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        //binding.refresh.setRefreshing(refreshing);
    }
}
