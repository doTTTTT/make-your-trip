package com.dot.makeyourtrip.views.fragment.setting;

import android.databinding.BaseObservable;
import android.view.View;

import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.utils.MYTManager;

import javax.inject.Inject;

public class SettingViewModel extends BaseObservable {
    @Inject MYTManager manager;

    private SettingContract.View view;

    public SettingViewModel(MYTComponent component, SettingContract.View view) {
        this.view = view;

        component.inject(this);
    }

    public void onClickLogOut(View view) {
        manager.logOut();
        this.view.logOut();
    }
}
