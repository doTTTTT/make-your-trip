package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.module.GoogleImageModule;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListItemTimeLinePlaceViewModel extends BaseObservable {
    private static final String TAG = ListItemTimeLinePlaceViewModel.class.getSimpleName();

    @Inject GoogleImageModule googleImageModule;

    private RoadMap.Place model;

    public ListItemTimeLinePlaceViewModel(RoadMap.Place model, MYTComponent component) {
        Log.d(TAG, "Image: " + model.Events.Images.size());
        this.model = model;

        component.inject(this);
    }

    public void setModel(RoadMap.Place model) {
        this.model = model;
        Log.d(TAG, "Image: " + model.Events.Images.size());
        notifyChange();
    }

    public String getTitle(){
        Log.d("Name", "" + model.Events.Name);
        return (model != null && model.Events.Name != null) ? model.Events.Name : "Name Place";
    }

    public String getPlaceID(){
        return model.Events.ProviderId;
    }

    public GoogleImageModule getGoogle(){
        return googleImageModule;
    }

    public void onClickCard(final View view) {
        if (model.Events.ProviderId != null) {
//            googleImageModule.getImage(model.Events.ProviderId)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.computation())
//                    .subscribe(new Consumer<Bitmap>() {
//                        @Override
//                        public void accept(Bitmap bitmap) throws Exception {
//                            Log.d(TAG, "onNext");
//                            //Glide.with(view.getContext()).load(bitmap).into(view);
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            Log.e(TAG, "" + throwable.getMessage());
//                        }
//                    });
        }
    }

    @BindingAdapter({"loadImagePlace", "google"})
    public static final void loadImage(final ImageView view, String url, GoogleImageModule module){
        if (url != null) {
            module.getImage(url, view.getWidth(), view.getHeight())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<byte[]>() {
                        @Override
                        public void accept(byte[] bitmap) throws Exception {
                            Glide.with(view.getContext()).load(bitmap).into(view);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, "" + throwable.getMessage());
                        }
                    });
        }
    }
}
