package com.dot.makeyourtrip.views.activity.lodge;

import android.app.DatePickerDialog;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.LodgeModel;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Lodge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LodgeViewModel extends BaseObservable implements Callback<List<LodgeModel>>, DatePickerDialog.OnDateSetListener {
    private static final String TAG = LodgeViewModel.class.getSimpleName();

    @Inject Lodge.LodgeRequest lodgeRequest;
    @Inject MYTManager manager;
    @Inject ApiUtils apiUtils;

    private enum Type {
        CHECKIN,
        CHECKOUT
    }

    private Calendar checkIn = Calendar.getInstance();
    private Calendar checkOut = Calendar.getInstance();
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private Type currentType;
    private LodgeContract.View view;
    private LodgeAdapter adapter;

    public LodgeViewModel(MYTComponent component, LodgeContract.View view, LodgeAdapter adapter) {
        this.view = view;
        this.adapter = adapter;

        component.inject(this);
    }

    public void onClickSearch(View view, AppCompatEditText search) {
        if (!search.getText().toString().isEmpty() && checkIn.getTimeInMillis() < checkOut.getTimeInMillis()) {
            lodgeRequest.searchLodge(manager.getToken(),
                    search.getText().toString(),
                    format.format(checkIn.getTime()),
                    format.format(checkOut.getTime()),
                    5000).enqueue(this);
        } else {
            Toast.makeText(view.getContext(), "Please verify champ", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCheckin(View view) {
        currentType = Type.CHECKIN;
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), this,
                checkIn.get(Calendar.YEAR),
                checkIn.get(Calendar.MONTH),
                checkIn.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void onClickCheckout(View view) {
        currentType = Type.CHECKOUT;
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), this,
                checkOut.get(Calendar.YEAR),
                checkOut.get(Calendar.MONTH),
                checkOut.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onResponse(Call<List<LodgeModel>> call, Response<List<LodgeModel>> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                Log.d(TAG, "Size: " + response.body().size());
                adapter.setList(response.body());
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                break;
        }
    }

    @Override
    public void onFailure(Call<List<LodgeModel>> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        switch (currentType) {
            case CHECKIN:
                checkIn.set(Calendar.YEAR, year);
                checkIn.set(Calendar.MONTH, month);
                checkIn.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                this.view.setCheckIn(format.format(checkIn.getTime()));
                break;
            case CHECKOUT:
                checkOut.set(Calendar.YEAR, year);
                checkOut.set(Calendar.MONTH, month);
                checkOut.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                this.view.setCheckOut(format.format(checkOut.getTime()));
                break;
        }
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String image){
        Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 8)).into(view);
    }
}
