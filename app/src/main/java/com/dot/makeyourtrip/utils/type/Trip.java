package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.TripModel;

import java.util.List;

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
public class Trip {
    public interface TripRequest {
        @GET("/trips/user/{id}")
        Call<List<TripModel>> getTripUser(@Path("id") String ID);

        @GET("/trips/{id}")
        Call<TripModel> getTrip(@Path("id") String ID);

        @FormUrlEncoded
        @POST("/trips")
        Call<TripModel> createTrip();

        @PUT("/trips/{id}")
        Call<TripModel> modifyTrip(@Path("id") String ID);

        @DELETE("/trips/{id}")
        Call<TripModel> deleteTrip(@Path("id") String ID);
    }

    @Provides
    public TripRequest provideTripRequest(Retrofit retrofit) {
        return retrofit.create(TripRequest.class);
    }
}
