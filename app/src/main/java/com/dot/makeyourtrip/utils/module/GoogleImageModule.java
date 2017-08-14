package com.dot.makeyourtrip.utils.module;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

import java.io.ByteArrayOutputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class GoogleImageModule implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = GoogleImageModule.class.getSimpleName();

    private GoogleApiClient apiClient;

    public GoogleImageModule(GoogleApiClient apiClient){
        apiClient.connect();
        apiClient.registerConnectionFailedListener(this);
        this.apiClient = apiClient;
    }

    public Observable<byte[]> getImage(final String id, final int width, final int height){
        return Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<byte[]> e) throws Exception {
                PlacePhotoMetadataResult result = Places.GeoDataApi.getPlacePhotos(apiClient, id).await();

                Log.d(TAG, "" + result.getStatus().getStatusMessage());
                if (result.getStatus().isSuccess()) {
                    PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
                    if (photoMetadataBuffer.getCount() > 0) {
                        Log.d(TAG, "" + photoMetadataBuffer.getCount());
                        PlacePhotoMetadata photo = photoMetadataBuffer.get(0);

                        Bitmap image = photo.getScaledPhoto(apiClient, width, height).await().getBitmap();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        e.onNext(stream.toByteArray());
                    }

                    photoMetadataBuffer.release();
                }

                e.onComplete();
            }
        });
    }

    @Override
    public void onConnectionFailed(@android.support.annotation.NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "" + connectionResult.getErrorMessage());
    }
}
