package com.dot.makeyourtrip.views.activity.trip;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Trip;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripViewModel extends BaseObservable implements Callback<TripModel> {
    private static final String TAG = TripViewModel.class.getSimpleName();

    @Inject Trip.TripRequest tripRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private String id;
    private TripModel tripModel;
    private TripAdapter adapter;

    public TripViewModel(MYTComponent component, String id, TripAdapter adapter){
        component.inject(this);

        this.id = id;
        this.adapter = adapter;

        tripRequest.getTrip(manager.getToken(), id).enqueue(this);
    }

    @Override
    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                Log.d(TAG, "Size RoadMap: " + response.body().RoadMaps.size());
                tripModel = response.body();
                adapter.notifyListener(tripModel);
                notifyChange();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    public void refresh(){
        tripRequest.getTrip(manager.getToken(), id).enqueue(this);
    }

    @Override
    public void onFailure(Call<TripModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String image){
        Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 16)).into(view);
    }
}
