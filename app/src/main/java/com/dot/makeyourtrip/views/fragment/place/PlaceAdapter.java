package com.dot.makeyourtrip.views.fragment.place;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ListItemPlaceBinding;
import com.dot.makeyourtrip.model.PlaceModel;

import java.util.Collections;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements PlaceItemTouchHelper.PlaceItemTouchHelperAdapter {
    private static final String TAG = PlaceAdapter.class.getSimpleName();

    private List<PlaceModel> placeModels;

    public PlaceAdapter(List<PlaceModel> placeModels){
        this.placeModels = placeModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ListItemPlaceBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(placeModels.get(position));
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    @Override
    public void onItemDismiss(int position) {
        final PlaceModel model = placeModels.get(position);
        placeModels.remove(model);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(placeModels, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(placeModels, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemPlaceBinding binding;

        public ViewHolder(ListItemPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PlaceModel model){
            if (binding.getViewModel() == null){
                binding.setViewModel(new ListItemPlaceViewModel(model));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }
}