package com.dot.makeyourtrip.utils.module;

import android.content.Context;

import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.NewsFeedItemTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    private static final String BASE_URL = "https://api.myt.patate.io";

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder()
                .registerTypeAdapterFactory(new NewsFeedItemTypeAdapterFactory())
                .create();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public ApiUtils providesApiUtils(Context context){
        return new ApiUtils(context);
    }
}
