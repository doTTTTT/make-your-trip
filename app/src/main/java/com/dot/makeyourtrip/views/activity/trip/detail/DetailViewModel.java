package com.dot.makeyourtrip.views.activity.trip.detail;

import android.databinding.BaseObservable;
import android.view.View;

import com.dot.makeyourtrip.views.activity.trip.TripActivity;

public class DetailViewModel extends BaseObservable {
    private TripActivity activity;

    public DetailViewModel(TripActivity activity){
        this.activity = activity;
    }

    public void onClickRefresh(View view){
        activity.getViewModel().refresh();
    }

    public void onClickDateStart(View view){

    }

    public void onClickDateEnd(View view){

    }

    public void onClickDescription(View view){

    }

    public String getDateStart(){
        return "Date Start";
    }

    public String getDateEnd(){
        return "Date End";
    }

    public String getDescription(){
        return "Description";
    }
}
