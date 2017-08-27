package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.TripModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Module
public class Trip {
    public interface TripRequest {
        @GET("/trips/user/{id}")
        Call<List<TripModel>> getTripUser(@Header("Authorization") String token,
                                          @Path("id") String ID);

        @GET("/trips/{id}")
        Call<TripModel> getTrip(@Header("Authorization") String token,
                                @Path("id") String ID);

        @FormUrlEncoded
        @POST("/trips")
        Call<TripModel> createTrip(@Header("Authorization") String token,
                                   @Field("user_id") String userID,
                                   @Field("name") String name);

        @FormUrlEncoded
        @PUT("/trips/{id}")
        Call<TripModel> modifyTrip(@Header("Authorization") String token,
                                   @Path("id") String ID,
                                   @Field("description") String description);

        @DELETE("/trips/{id}")
        Call<ResponseBody> deleteTrip(@Header("Authorization") String token,
                                      @Path("id") String ID);
    }

    @Provides
    public TripRequest provideTripRequest(Retrofit retrofit) {
        return retrofit.create(TripRequest.class);
    }
}
