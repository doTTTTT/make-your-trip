package com.dot.makeyourtrip.utils.android;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dot.makeyourtrip.utils.MYTComponent;
import com.dot.makeyourtrip.views.activity.main.MainActivity;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    public abstract int getLayoutID();
    public abstract void initView(T binding);

    private Bundle save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        T binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
        save = savedInstanceState;

        initView(binding);

        return binding.getRoot();

    }

    public MYTComponent getComponent(){
        return ((Activity) getActivity()).getComponent();
    }

    public Bundle getSaveBundle(){
        return save;
    }
}
