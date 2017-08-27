package com.dot.makeyourtrip.views.activity.place;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ListItemSearchPlaceBinding;
import com.dot.makeyourtrip.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.ViewHolder> {
    private List<PlaceModel> list;

    public SearchPlaceAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ListItemSearchPlaceBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_search_place, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<PlaceModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemSearchPlaceBinding binding;

        public ViewHolder(ListItemSearchPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PlaceModel model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemSearchPlaceViewModel(model));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }
}
