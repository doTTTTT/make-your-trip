package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.TransportModel;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Module
public class Transport {
    interface TransportRequest {
        @GET("/transports/trip/{id}")
        Call<List<TransportModel>> getTripTransport(@Path("id") String ID);

        @GET("/transports/{id}")
        Call<TransportModel> getTransport(@Path("id") String ID);

        @POST("/transports")
        Call<TransportModel> createTransport();

        @PUT("/transports")
        Call<TransportModel> modifyTransport();

        @DELETE("/transports")
        Call<TransportModel> deleteTransport();
    }

    @Singleton
    @Provides
    public TransportRequest provicesTransportRequest(Retrofit retrofit){
        return retrofit.create(TransportRequest.class);
    }
}
