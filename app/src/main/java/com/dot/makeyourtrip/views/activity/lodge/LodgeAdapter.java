package com.dot.makeyourtrip.views.activity.lodge;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ListItemSearchLodgeBinding;
import com.dot.makeyourtrip.model.LodgeModel;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;

import java.util.ArrayList;
import java.util.List;

public class LodgeAdapter extends RecyclerView.Adapter<LodgeAdapter.ViewHolder> {
    private List<LodgeModel> list;
    private String tripID;
    private MYTComponent component;
    private LodgeContract.View view;

    public LodgeAdapter(String trip_id, MYTComponent component, LodgeContract.View view) {
        this.tripID = trip_id;
        this.component = component;
        this.view = view;

        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ListItemSearchLodgeBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_search_lodge, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position), tripID, component, view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<LodgeModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemSearchLodgeBinding binding;

        public ViewHolder(ListItemSearchLodgeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LodgeModel model, String tripID, MYTComponent component, LodgeContract.View view) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemLodgeViewModel(model, tripID, component, view));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }
}
