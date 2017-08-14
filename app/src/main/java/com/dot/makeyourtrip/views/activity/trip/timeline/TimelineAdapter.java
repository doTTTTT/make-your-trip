package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ListItemPlaceBinding;
import com.dot.makeyourtrip.databinding.ListItemTimelineBinding;
import com.dot.makeyourtrip.databinding.ListItemTimelineLodgeBinding;
import com.dot.makeyourtrip.databinding.ListItemTimelinePlaceBinding;
import com.dot.makeyourtrip.databinding.ListItemTimelineTransportBinding;
import com.dot.makeyourtrip.model.BaseTripModel;
import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.views.fragment.place.ListItemPlaceViewModel;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.BaseViewHolder> {
    private static final String TAG = TimelineAdapter.class.getSimpleName();

    private List<RoadMap> list;
    private MYTComponent component;

    public TimelineAdapter(MYTComponent component, List<RoadMap> list){
        this.list = list;
        this.component = component;
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).EventType) {
            case "Place": return RoadMap.Place.VIEW_TYPE;
            case "Lodge": return RoadMap.Lodge.VIEW_TYPE;
            case "Transport": return RoadMap.Transport.VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case RoadMap.Place.VIEW_TYPE: return new ViewHolderPlace((ListItemTimelinePlaceBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_timeline_place, parent, false));
            case RoadMap.Lodge.VIEW_TYPE: return new ViewHolderLodge((ListItemTimelineLodgeBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_timeline_lodge, parent, false));
            case RoadMap.Transport.VIEW_TYPE: return new ViewHolderTransport((ListItemTimelineTransportBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_timeline_transport, parent, false));
            default: return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_timeline, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<RoadMap> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public abstract class BaseViewHolder<T extends ViewDataBinding, E> extends RecyclerView.ViewHolder {
        protected T binding;

        abstract void bind(E model);

        public BaseViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        public ViewHolder(ViewDataBinding binding) {
            super(binding);
        }

        @Override
        void bind(Object model) {

        }
    }

    public class ViewHolderPlace extends BaseViewHolder<ListItemTimelinePlaceBinding, RoadMap.Place> {
        public ViewHolderPlace(ListItemTimelinePlaceBinding binding) {
            super(binding);
        }

        void bind(RoadMap.Place model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemTimeLinePlaceViewModel(model, component));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }

    public class ViewHolderLodge extends BaseViewHolder<ListItemTimelineLodgeBinding, RoadMap.Lodge> {
        public ViewHolderLodge(ListItemTimelineLodgeBinding binding) {
            super(binding);
        }

        void bind(RoadMap.Lodge model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemTimeLineLodgeViewModel(model));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }

    public class ViewHolderTransport extends BaseViewHolder<ListItemTimelineTransportBinding, RoadMap.Transport> {
        public ViewHolderTransport(ListItemTimelineTransportBinding binding) {
            super(binding);
        }

        void bind(RoadMap.Transport model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemTimeLineTransportViewModel(model));
            } else {
                binding.getViewModel().setModel(model);
            }
        }
    }
}
