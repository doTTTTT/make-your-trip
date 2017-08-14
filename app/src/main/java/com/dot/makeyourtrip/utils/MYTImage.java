package com.dot.makeyourtrip.utils;

import android.content.Context;

import com.dot.makeyourtrip.utils.module.GoogleImageModule;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MYTImage {
    @Singleton
    @Provides
    public GoogleApiClient providesGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    @Singleton
    @Provides
    public GoogleImageModule providesGoogleImageModule(GoogleApiClient apiClient){
        return new GoogleImageModule(apiClient);
    }
}
