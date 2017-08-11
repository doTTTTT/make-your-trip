package com.dot.makeyourtrip.views.fragment.trip;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.BR;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;

import java.util.List;

public class TripAdapter extends PagerAdapter {
    private Activity activity;
    private List<TripModel> list;
    private TripModel fake;

    public TripAdapter(Activity activity, List<TripModel> list) {
        this.activity = activity;
        this.list = list;

        fake = new TripModel();
        fake.isFake = true;

        list.add(list.size(), fake);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TripModel model = list.get(position);
        ViewDataBinding binding = null;
        if (model.isFake) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.list_item_trip_empty, container, false);
            binding.setVariable(BR.viewModel, new ListItemTripEmptyViewModel(activity));
        } else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.list_item_trip, container, false);
            binding.setVariable(BR.viewModel, new ListItemTripViewModel(model));
        }
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setList(List<TripModel> list) {
        this.list.clear();
        this.list.addAll(list);
        this.list.add(this.list.size(), fake);
        notifyDataSetChanged();
    }
}
