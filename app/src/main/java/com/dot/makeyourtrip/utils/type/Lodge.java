package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.LodgeModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Module
public class Lodge {
    public interface LodgeRequest {
        @GET("/lodges/trip/{id}")
        Call<List<LodgeModel>> getTripLodge(@Path("id") String ID);

        @GET("/lodges/{id}")
        Call<LodgeModel> getLodge(@Path("id") String ID);

        @GET("/lodges/search")
        Call<List<LodgeModel>> searchLodge(@Header("Authorization") String token,
                                           @Query("location") String location,
                                           @Query("checkin") String checkin,
                                           @Query("checkout") String checkout,
                                           @Query("radius") int radius);

        @FormUrlEncoded
        @POST("/lodges")
        Call<LodgeModel> createLodge(@Header("Authorization") String token,
                                     @Field("trip_id") String tripID,
                                     @Field("name") String name,
                                     @Field("location") String location,
                                     @Field("start_date") String startDate,
                                     @Field("end_date") String endDate,
                                     @Field("longitude") Double longitude,
                                     @Field("latitude") Double latitude,
                                     @Field("provider_images") String images,
                                     @Field("provider_name") String providerName,
                                     @Field("type") String type);

        @PUT("/lodges/{id}")
        Call<LodgeModel> modifyTrip(@Path("id") String ID,
                                    @Body ResponseBody body);

        @DELETE("/lodges/{id}")
        Call<LodgeModel> deleteTrip(@Path("id") String ID);
    }

    @Provides
    public LodgeRequest providesLodgeRequest(Retrofit retrofit) {
        return retrofit.create(LodgeRequest.class);
    }
}
