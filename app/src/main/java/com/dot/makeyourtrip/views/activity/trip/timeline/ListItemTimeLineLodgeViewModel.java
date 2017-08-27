package com.dot.makeyourtrip.views.activity.trip.timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.views.dialog.transport.AddTransportDialog;

public class ListItemTimeLineLodgeViewModel extends BaseObservable {
    private static final String TAG = ListItemTimeLineLodgeViewModel.class.getSimpleName();

    private RoadMap.Lodge model;
    private Animation animation;

    public ListItemTimeLineLodgeViewModel(RoadMap.Lodge model){
        this.model = model;

        this.animation = new Animation();
    }

    public void setModel(RoadMap.Lodge model) {
        this.model = model;
        notifyChange();
    }

    public void onClickIcon(View view) {
        Log.d(TAG, "onClickIcon");
        animation.animate();
    }

    public String getTitle(){
        return (model != null) ? model.Events.Name : "Title";
    }

    public String getProviderName() {
        return (model != null && model.Events != null) ? model.Events.ProviderName : "";
    }

    public String getType() {
        return (model != null && model.Events != null) ? model.Events.Type : "";
    }

    public String getImage(){
        return (model != null  && model.Events != null && model.Events.Images.size() > 0) ? model.Events.Images.get(0) : "";
    }

    public Animation getAnimation() {
        return animation;
    }

    @BindingAdapter("loadImageLodge")
    public static final void loadImage(final ImageView view, String url){
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("registerCard")
    public static final void registerCard(RelativeLayout card, Animation animation) {
        animation.registerCard(card);
    }

    @BindingAdapter("registerTitle")
    public static final void registerTitle(TextView title, Animation animation) {
        animation.registerTitle(title);
    }

    public static class Animation {
        private boolean isExpanded = true;
        private boolean isAnimationRunning = false;
        private View card = null;
        private View title = null;

        public void registerCard(RelativeLayout card) {
            this.card = card;
        }

        public void registerTitle(TextView title) {
            this.title = title;
        }

        public void animate(){
            if (card != null && title != null)
                if (!isAnimationRunning) {
                    if (isExpanded) {
                        collaspe();
                    } else {
                        expand();
                    }
                }
        }

        private void expand() {
            AnimatorSet set = new AnimatorSet();

            set.setDuration(500);
            set.play(card(100, 200))
                    .with(title(1f, 0.30f));

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimationRunning = false;
                    isExpanded = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimationRunning = true; }
            });

            set.start();
        }

        private void collaspe() {
            AnimatorSet set = new AnimatorSet();

            set.setDuration(500);
            set.play(card(200, 100))
                    .with(title(0.3f, 1f));

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimationRunning = false;
                    isExpanded = false;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimationRunning = true; }
            });

            set.start();
        }

        private ValueAnimator card(int from, int to) {
            ValueAnimator height = ValueAnimator.ofInt(convertDpToPixel(from, card.getContext()), convertDpToPixel(to, card.getContext()));
            height.setInterpolator(new DecelerateInterpolator());
            height.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d(TAG, "Collaspe" + animation.getAnimatedValue());
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) card.getLayoutParams();
                    params.height = (int) animation.getAnimatedValue();
                    card.setLayoutParams(params);
                }
            });

            return height;
        }

        private ValueAnimator title(float from, float to) {
            ValueAnimator heightPercent = ValueAnimator.ofFloat(from, to);
            heightPercent.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //Log.d(TAG, "Percent: " + )
                    PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) title.getLayoutParams();
                    layoutParams.getPercentLayoutInfo().heightPercent = (float) animation.getAnimatedValue();
                    title.setLayoutParams(layoutParams);
                    title.requestLayout();
                }
            });

            return heightPercent;
        }

        public static float convertPixelsToDp(float px, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return dp;
        }

        public static int convertDpToPixel(float dp, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return (int) px;
        }
    }
}
