package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.PlaceModel;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Module
public class Place {
    public interface PlaceRequest {
        @GET("/places/trip/{id}")
        Call<List<PlaceModel>> getTripPlaces(@Path("id") String ID);

        @GET("/places/{id}")
        Call<PlaceModel> getPlace(@Path("id") String ID);

        @FormUrlEncoded
        @POST("/places")
        Call<PlaceModel> createPlace();

        @PUT("/places/{id}")
        Call<PlaceModel> modifyPlace(@Path("id") String id);

        @DELETE("/places/{id}")
        Call<PlaceModel> deletePlace(@Path("id") String id);
    }

    @Singleton
    @Provides
    public PlaceRequest providesPlaceRequest(Retrofit retrofit){
        return retrofit.create(PlaceRequest.class);
    }
}
