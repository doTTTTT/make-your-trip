package com.dot.makeyourtrip.utils;

import com.dot.makeyourtrip.utils.module.RetrofitModule;
import com.dot.makeyourtrip.utils.module.SharedPreferenceModule;
import com.dot.makeyourtrip.utils.type.Authentification;
import com.dot.makeyourtrip.utils.type.Lodge;
import com.dot.makeyourtrip.utils.type.Place;
import com.dot.makeyourtrip.utils.type.Transport;
import com.dot.makeyourtrip.utils.type.Trip;
import com.dot.makeyourtrip.utils.type.User;
import com.dot.makeyourtrip.views.activity.inscription.InscriptionViewModel;
import com.dot.makeyourtrip.views.activity.login.LoginViewModel;
import com.dot.makeyourtrip.views.activity.main.MainActivity;
import com.dot.makeyourtrip.views.fragment.trip.TripViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RetrofitModule.class,
        SharedPreferenceModule.class,
        Authentification.class,
        Lodge.class,
        User.class,
        Trip.class,
        Transport.class,
        Place.class
})
public interface MYTComponent {
    void inject(MainActivity activity);

    void inject(LoginViewModel viewModel);

    void inject(InscriptionViewModel viewModel);

    void inject(TripViewModel viewModel);
}
