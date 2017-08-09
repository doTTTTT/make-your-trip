package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.AuthModel;

import dagger.Module;
import dagger.Provides;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

@Module
public class Authentification {
    public interface AuthentificationInterface {
        @FormUrlEncoded
        @POST("/auth/basic")
        Call<AuthModel> authentification(@Field("email") String email,
                                         @Field("password") String password);
    }

    @Provides
    public AuthentificationInterface providesAuthentificationInterface(Retrofit retrofit) {
        return retrofit.create(AuthentificationInterface.class);
    }
}

