package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.model.RoadMap;

public class ListItemTimeLineLodgeViewModel extends BaseObservable {
    private RoadMap.Lodge model;

    public ListItemTimeLineLodgeViewModel(RoadMap.Lodge model){
        this.model = model;
    }

    public void setModel(RoadMap.Lodge model) {
        this.model = model;
        notifyChange();
    }

    public String getTitle(){
        return (model != null) ? model.Events.Name : "Title";
    }

    public String getImage(){
        return (model != null && model.Events.Images.size() > 0) ? model.Events.Images.get(0) : "";
    }

    @BindingAdapter("loadImageLodge")
    public static final void loadImage(final ImageView view, String url){
        Glide.with(view.getContext()).load(url).into(view);
    }
}
