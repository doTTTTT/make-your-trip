package com.dot.makeyourtrip.views.fragment.trip;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.module.GoogleImageModule;
import com.dot.makeyourtrip.utils.type.Trip;
import com.dot.makeyourtrip.views.activity.main.MainActivity;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemTripViewModel extends BaseObservable implements View.OnLongClickListener, Callback<ResponseBody> {
    private static final String TAG = ListItemTripViewModel.class.getSimpleName();

    @Inject GoogleImageModule module;
    @Inject Trip.TripRequest tripRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private TripModel model;
    private MainActivity activity;
    private TripViewModel viewModel;
    private byte[] background;

    public ListItemTripViewModel(MainActivity activity, TripModel model, TripViewModel viewModel) {
        this.model = model;
        this.activity = activity;
        this.viewModel = viewModel;

        activity.getComponent().inject(this);
    }

    public void setModel(TripModel model) {
        this.model = model;
    }

    public void onClickTrip(View view) {
        Intent intent = new Intent(view.getContext(), TripActivity.class);
        intent.putExtra(TripActivity.EXTRA_ID, model.ID);

        ActivityCompat.startActivity(view.getContext(), intent, null);
    }

    public String getName(){
        return model.Name;
    }

    public String getDescription() {
        return model.Description;
    }

    public String getNumberPlace() {
        return model.RoadMaps.size() + " Places";
    }

    public String getRandomPlace() {
        List<RoadMap.Place> places = new ArrayList<>();

        for (RoadMap tmp : model.RoadMaps) {
            Log.d(TAG, "Type: " + tmp.EventType);
            if (tmp.EventType.equals("Place")) {
                Log.d(TAG, "Added ?");
                places.add((RoadMap.Place) tmp);
            }
        }

        String id = null;
        Log.d(TAG, "Size Places: " + places.size());
        if (places.size() > 0) {
            int random = new Random().nextInt(places.size());
            Log.d(TAG, "Random: " + random);
            if (random >= 0 && places.get(random) != null && places.get(random).Events != null ) {
                id = places.get(random).Events.ProviderId;
            }
        }
        Log.d(TAG, "ID: " + id);

        return id;
    }

    public GoogleImageModule getModule(){
        return module;
    }

    public MainActivity getActivity() {
        return activity;
    }

    @BindingAdapter({"loadBackgroundTrip", "viewModel"})
    public static final void loadBackgroundTrip(final ImageView view, String place, final ListItemTripViewModel viewModel) {
        Log.d("Load Back", "1: " + place);
        if (place != null) {
            Log.d("Load Back", "2");
            viewModel.module.getImage(place, 1920, 1080)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<byte[]>() {
                        @Override
                        public void accept(byte[] bitmap) throws Exception {
                            Glide.with(view.getContext()).load(bitmap).bitmapTransform(new BlurTransformation(view.getContext(), 12)).into(view);
                            viewModel.background = bitmap;
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("Load Back", "" + throwable.getMessage());
                        }
                    });
        } else {
            Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 12)).into(view);
        }
    }

    public byte[] getBackground() {
        return background;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "LONG CLICK");
        new AlertDialog.Builder(v.getContext())
                .setTitle("Delete this trip ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tripRequest.deleteTrip(manager.getToken(), model.ID).enqueue(ListItemTripViewModel.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
        return true;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 204:
                viewModel.onRefresh();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }
}
