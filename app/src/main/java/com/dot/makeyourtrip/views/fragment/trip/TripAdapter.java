package com.dot.makeyourtrip.views.fragment.trip;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.BR;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.type.Trip;

import java.util.List;

public class TripAdapter extends PagerAdapter {
    private Context context;
    private List<TripModel> list;
    private TripModel fake;

    public TripAdapter(Context context, List<TripModel> list) {
        this.context = context;
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
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_trip_empty, container, false);
        } else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_trip, container, false);
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

    public void setList(List<TripModel> list) {
        list.clear();
        list.addAll(list);
        list.add(list.size(), fake);
        notifyDataSetChanged();
    }
}
