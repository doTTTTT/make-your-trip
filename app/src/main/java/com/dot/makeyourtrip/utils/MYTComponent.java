package com.dot.makeyourtrip.utils;

import com.dot.makeyourtrip.utils.module.GoogleImageModule;
import com.dot.makeyourtrip.utils.module.RetrofitModule;
import com.dot.makeyourtrip.utils.module.SharedPreferenceModule;
import com.dot.makeyourtrip.utils.type.Authentification;
import com.dot.makeyourtrip.utils.type.Lodge;
import com.dot.makeyourtrip.utils.type.Place;
import com.dot.makeyourtrip.utils.type.Transport;
import com.dot.makeyourtrip.utils.type.Trip;
import com.dot.makeyourtrip.utils.type.User;
import com.dot.makeyourtrip.views.activity.inscription.InscriptionViewModel;
import com.dot.makeyourtrip.views.activity.lodge.ListItemLodgeViewModel;
import com.dot.makeyourtrip.views.activity.lodge.LodgeViewModel;
import com.dot.makeyourtrip.views.activity.login.LoginViewModel;
import com.dot.makeyourtrip.views.activity.main.MainActivity;
import com.dot.makeyourtrip.views.activity.trip.TripActivity;
import com.dot.makeyourtrip.views.activity.trip.detail.DetailViewModel;
import com.dot.makeyourtrip.views.activity.trip.timeline.ListItemTimeLinePlaceViewModel;
import com.dot.makeyourtrip.views.activity.trip.timeline.TimelineAdapter;
import com.dot.makeyourtrip.views.activity.trip.timeline.TimelineViewModel;
import com.dot.makeyourtrip.views.dialog.transport.AddTransportDialog;
import com.dot.makeyourtrip.views.fragment.place.PlaceViewModel;
import com.dot.makeyourtrip.views.fragment.setting.SettingViewModel;
import com.dot.makeyourtrip.views.fragment.trip.AddTripDialog;
import com.dot.makeyourtrip.views.fragment.trip.ListItemTripEmptyViewModel;
import com.dot.makeyourtrip.views.fragment.trip.ListItemTripViewModel;
import com.dot.makeyourtrip.views.fragment.trip.TripViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MYTImage.class,
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

    void inject(AddTripDialog addTripDialog);

    void inject(ListItemTripEmptyViewModel listItemTripEmptyViewModel);

    void inject(PlaceViewModel placeViewModel);

    void inject(com.dot.makeyourtrip.views.activity.trip.TripViewModel tripViewModel);

    void inject(TimelineViewModel timelineViewModel);

    void inject(TripActivity tripActivity);

    void inject(SettingViewModel settingViewModel);

    void inject(ListItemTimeLinePlaceViewModel listItemTimeLinePlaceViewModel);

    void inject(LodgeViewModel lodgeViewModel);

    void inject(ListItemLodgeViewModel listItemLodgeViewModel);

    void inject(AddTransportDialog addTransportDialog);

    void inject(DetailViewModel detailViewModel);

    void inject(ListItemTripViewModel listItemTripViewModel);

    void inject(TimelineAdapter timelineAdapter);
}
