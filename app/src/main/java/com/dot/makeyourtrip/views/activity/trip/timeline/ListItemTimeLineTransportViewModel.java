package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.RoadMap;

import java.text.SimpleDateFormat;

public class ListItemTimeLineTransportViewModel extends BaseObservable {
    private RoadMap.Transport model;
    private SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

    public ListItemTimeLineTransportViewModel(RoadMap.Transport model) {
        this.model = model;
    }

    public void setModel(RoadMap.Transport model) {
        this.model = model;
        notifyChange();
    }

    public String getTitle(){
        return "Title";
    }

    public String getDepartureDate() {
        return (model != null && model.Events != null) ? date.format(model.Events.DepartureDate) : "";
    }

    public String getDepartureHour() {
        return (model != null && model.Events != null) ? hour.format(model.Events.DepartureDate) : "";
    }

    public String getReturnDate() {
        return (model != null && model.Events != null) ? date.format(model.Events.ReturnDate) : "";
    }

    public String getReturnHour() {
        return (model != null && model.Events != null) ? hour.format(model.Events.ReturnDate) : "";
    }

    public String getDepartureLocation() {
        return (model != null && model.Events != null) ? model.Events.DepartureLocation : "";
    }

    public String getReturnLocation() {
        return (model != null && model.Events != null) ? model.Events.ReturnLocation : "";
    }

    public String getType(){
        return (model != null && model.Events != null) ? model.Events.Type : "";
    }

    @BindingAdapter("loadTransportIcon")
    public static final void loadTransportIcon(ImageView view, String type){
        switch (type.toLowerCase()) {
            case "plane": Glide.with(view.getContext()).load(R.drawable.ic_flight_white_24dp).into(view); return;
            case "car": Glide.with(view.getContext()).load(R.drawable.ic_directions_car_white_24dp).into(view); return;
            case "taxi": Glide.with(view.getContext()).load(R.drawable.ic_local_taxi_white_24dp).into(view); return;
            case "uber": Glide.with(view.getContext()).load(R.drawable.ic_directions_car_white_24dp).into(view); return;
            case "bike": Glide.with(view.getContext()).load(R.drawable.ic_directions_bike_white_24dp).into(view); return;
            case "bus": Glide.with(view.getContext()).load(R.drawable.ic_directions_bus_white_24dp).into(view); return;
        }
    }
}
