package com.dot.makeyourtrip.views.fragment.place;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentPlaceBinding;
import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.utils.android.BaseFragment;

import java.util.ArrayList;

public class PlaceFragment extends BaseFragment<FragmentPlaceBinding> {
    private PlaceViewModel viewModel;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_place;
    }

    @Override
    public void initView(FragmentPlaceBinding binding) {
        PlaceAdapter adapter = new PlaceAdapter(new ArrayList<PlaceModel>());
        PlaceItemTouchHelper helper = new PlaceItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helper);

        itemTouchHelper.attachToRecyclerView(binding.listPlace);
        binding.listPlace.setAdapter(adapter);
        viewModel = new PlaceViewModel(getComponent(), adapter);
        binding.setViewModel(viewModel);
    }
}
