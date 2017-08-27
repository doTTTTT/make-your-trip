package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentActivity;
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
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Lodge;
import com.dot.makeyourtrip.utils.type.Place;
import com.dot.makeyourtrip.utils.type.Transport;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;
import com.dot.makeyourtrip.views.fragment.place.ListItemPlaceViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.BaseViewHolder> implements ItemTouchHelperAdapter, Callback<ResponseBody> {
    private static final String TAG = TimelineAdapter.class.getSimpleName();

    @Inject Place.PlaceRequest placeRequest;
    @Inject Lodge.LodgeRequest lodgeRequest;
    @Inject Transport.TransportRequest transportRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private List<RoadMap> list;
    private TripActivity activity;

    public TimelineAdapter(TripActivity activity) {
        this.activity = activity;

        this.list = new ArrayList<>();

        activity.getComponent().inject(this);
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

    @Override
    public void onItemDismiss(int position) {
        RoadMap item = list.get(position);
        switch (item.EventType) {
            case "Place": placeRequest.deletePlace(manager.getToken(), ((RoadMap.Place) item).Events.ID).enqueue(this); break;
            case "Lodge": lodgeRequest.deleteLodge(manager.getToken(), ((RoadMap.Lodge) item).Events.ID).enqueue(this); break;
            case "Transport": transportRequest.deleteTransport(manager.getToken(), ((RoadMap.Transport) item).Events.ID).enqueue(this); break;
        }
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        RoadMap item = list.get(fromPosition);
        switch (item.EventType) {
            case "Place":
                placeRequest.modifyPlace(manager.getToken(), ((RoadMap.Place) item).Events.ID, toPosition).enqueue(this);
                break;
            case "Lodge":
                lodgeRequest.modifyLodge(manager.getToken(), ((RoadMap.Lodge) item).Events.ID, toPosition).enqueue(this);
                break;
            case "Transport":
                transportRequest.modifyTransport(manager.getToken(), ((RoadMap.Transport) item).Events.ID, toPosition).enqueue(this);
                break;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(TAG, "Code: " + response.code());
        switch (response.code()) {
            case 200: break;
            default:
                activity.getViewModel().refresh();
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        activity.getViewModel().refresh();
        Log.e(TAG, "" + t.getMessage());
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
        void bind(Object model) {}
    }

    public class ViewHolderPlace extends BaseViewHolder<ListItemTimelinePlaceBinding, RoadMap.Place> {
        public ViewHolderPlace(ListItemTimelinePlaceBinding binding) {
            super(binding);
        }

        void bind(RoadMap.Place model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemTimeLinePlaceViewModel(model, activity.getComponent()));
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
