package com.dot.makeyourtrip.views.activity.main;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.utils.MYTComponent;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainViewModel extends BaseObservable {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String image){
        Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 8)).into(view);
    }
}
