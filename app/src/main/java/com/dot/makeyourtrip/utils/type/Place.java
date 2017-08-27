package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.PlaceModel;

import java.util.List;

import javax.inject.Singleton;

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
public class Place {
    public interface PlaceRequest {
        @GET("/places/trip/{id}")
        Call<List<PlaceModel>> getTripPlaces(@Path("id") String ID);

        @GET("/places/{id}")
        Call<PlaceModel> getPlace(@Path("id") String ID);

        @FormUrlEncoded
        @POST("/places")
        Call<PlaceModel> createPlace(@Header("Authorization") String auth,
                                     @Field("user_id") String userId,
                                     @Field("trip_id") String tripId,
                                     @Field("provider_id") String providerId,
                                     @Field("name") String name,
                                     @Field("latitude") Double latitude,
                                     @Field("longitude") Double longitude);

        @FormUrlEncoded
        @PUT("/places/{id}")
        Call<ResponseBody> modifyPlace(@Header("Authorization") String token,
                                       @Path("id") String id,
                                       @Field("position") Integer position);

        @DELETE("/places/{id}")
        Call<ResponseBody> deletePlace(@Header("Authorization") String token,
                                       @Path("id") String id);
    }

    @Singleton
    @Provides
    public PlaceRequest providesPlaceRequest(Retrofit retrofit){
        return retrofit.create(PlaceRequest.class);
    }
}
