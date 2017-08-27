package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TransportModel;

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
public class Transport {
    public interface TransportRequest {
        @GET("/transports/trip/{id}")
        Call<List<TransportModel>> getTripTransport(@Path("id") String ID);

        @GET("/transports/{id}")
        Call<TransportModel> getTransport(@Path("id") String ID);

        @FormUrlEncoded
        @POST("/transports")
        Call<TransportModel> createTransport(@Header("Authorization") String token,
                                             @Field("trip_id") String tripID,
                                             @Field("name") String name,
                                             @Field("departure_location") String departLocation,
                                             @Field("return_location") String returnLocation,
                                             @Field("departure_date") String departureDate,
                                             @Field("return_date") String returnDate,
                                             @Field("type") String type);

        @FormUrlEncoded
        @PUT("/transports/{id}")
        Call<ResponseBody> modifyTransport(@Header("Authorization") String token,
                                           @Path("id") String id,
                                           @Field("position") Integer position);

        @DELETE("/transports/{id}")
        Call<ResponseBody> deleteTransport(@Header("Authorization") String token,
                                             @Path("id") String id);
    }

    @Singleton
    @Provides
    public TransportRequest provicesTransportRequest(Retrofit retrofit){
        return retrofit.create(TransportRequest.class);
    }
}
