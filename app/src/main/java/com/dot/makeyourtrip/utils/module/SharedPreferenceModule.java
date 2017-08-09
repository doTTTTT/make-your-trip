package com.dot.makeyourtrip.utils.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dot.makeyourtrip.utils.MYTManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    private Application application;

    public SharedPreferenceModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context providesContext(){
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public MYTManager providesMYTManager(Context context){
        return new MYTManager(context);
    }
}
