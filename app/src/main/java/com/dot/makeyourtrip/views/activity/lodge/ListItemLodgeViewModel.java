package com.dot.makeyourtrip.views.activity.lodge;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.model.LodgeModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Lodge;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemLodgeViewModel extends BaseObservable implements Callback<LodgeModel> {
    private static final String TAG = ListItemLodgeViewModel.class.getSimpleName();

    @Inject Lodge.LodgeRequest lodgeRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private LodgeModel model;
    private String tripID;
    private LodgeContract.View view;

    public ListItemLodgeViewModel(LodgeModel model, String tripID, MYTComponent component, LodgeContract.View view) {
        component.inject(this);

        this.model = model;
        this.tripID = tripID;
        this.view = view;
    }

    public void setModel(LodgeModel model) {
        this.model = model;
        notifyChange();
    }

    public String getTitle(){
        return model.Name;
    }

    public String getImage(){
        return (model.Images.size() > 0) ? model.Images.get(0) : "";
    }

    public void onClickCard(View view) {
        lodgeRequest.createLodge(manager.getToken(),
                tripID,
                model.Name,
                model.Location,
                model.StartDate,
                model.EndDate,
                model.Longitude,
                model.Latitude,
                getImageFromList(),
                model.ProviderName,
                model.Type).enqueue(this);
    }

    @BindingAdapter("loadImageLodge")
    public static final void loadImage(ImageView view, String url){
        Glide.with(view.getContext()).load(url).into(view);
    }

    @Override
    public void onResponse(Call<LodgeModel> call, Response<LodgeModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 201:
                view.finishActivity();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<LodgeModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }

    private String getImageFromList() {
        String image = "";

        for (String tmp : model.Images) {
            image += tmp;
            image += ",";
        }

        return image;
    }
}
