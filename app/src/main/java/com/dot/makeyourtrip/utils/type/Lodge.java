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
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Module
public class Lodge {
    public interface LodgeInterface {
        @GET("/lodges/trip/{id}")
        Call<List<LodgeModel>> getTripLodge(@Path("id") String ID);

        @GET("/lodges/{id}")
        Call<LodgeModel> getLodge(@Path("id") String ID);

        @FormUrlEncoded
        @POST("/lodges")
        Call<LodgeModel> createLodge(@Body ResponseBody body);

        @PUT("/lodges/{id}")
        Call<LodgeModel> modifyTrip(@Path("id") String ID,
                                    @Body ResponseBody body);

        @DELETE("/lodges/{id}")
        Call<LodgeModel> deleteTrip(@Path("id") String ID);
    }

    @Provides
    public LodgeInterface providesLodgeInterface(Retrofit retrofit) {
        return retrofit.create(LodgeInterface.class);
    }
}
