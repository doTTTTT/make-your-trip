package com.dot.makeyourtrip.views.fragment.trip;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.BR;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ListItemTripBinding;
import com.dot.makeyourtrip.databinding.ListItemTripEmptyBinding;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private Activity activity;
    private List<TripModel> list;
    private List<ViewDataBinding> bindings;
    private TripModel fake;
    private TripViewModel viewModel;

    public TripAdapter(Activity activity, List<TripModel> list, TripViewModel viewModel) {
        this.activity = activity;
        this.list = list;
        this.viewModel = viewModel;

        fake = new TripModel();
        fake.isFake = true;

        list.add(list.size(), fake);

        bindings = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TripModel model = list.get(position);
        ViewDataBinding binding = null;
        if (model.isFake) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.list_item_trip_empty, container, false);
            binding.setVariable(BR.viewModel, new ListItemTripEmptyViewModel(activity, this.viewModel));
        } else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.list_item_trip, container, false);
            ListItemTripViewModel viewModel = new ListItemTripViewModel((MainActivity) activity, model, this.viewModel);
            binding.setVariable(BR.viewModel, viewModel);
            ((ListItemTripBinding) binding).root.setOnLongClickListener(viewModel);
        }
        bindings.add(binding);
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
        this.bindings.clear();
        this.list.addAll(list);
        this.list.add(this.list.size(), fake);
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public byte[] getByteFromItem(int position) {
         ViewDataBinding binding = bindings.get(position);
        if (binding instanceof ListItemTripBinding) {
            return ((ListItemTripBinding) binding).getViewModel().getBackground();
        } else if (binding instanceof ListItemTripEmptyBinding) {
            return null;
        }

        return null;
    }
}
