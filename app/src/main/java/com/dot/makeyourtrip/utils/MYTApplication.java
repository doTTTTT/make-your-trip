package com.dot.makeyourtrip.utils;

import android.app.Application;

import com.dot.makeyourtrip.utils.module.RetrofitModule;
import com.dot.makeyourtrip.utils.module.SharedPreferenceModule;
import com.google.android.gms.common.api.GoogleApiClient;

public class MYTApplication extends Application {
    private MYTComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMYTComponent.builder()
                .sharedPreferenceModule(new SharedPreferenceModule(this))
                .build();
    }

    public MYTComponent getComponent() {
        return component;
    }
}
