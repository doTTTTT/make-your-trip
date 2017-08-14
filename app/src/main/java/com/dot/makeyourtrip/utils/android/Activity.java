package com.dot.makeyourtrip.utils.android;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.utils.MYTApplication;
import com.dot.makeyourtrip.utils.MYTComponent;

public abstract class Activity<T extends ViewDataBinding> extends AppCompatActivity {
    protected T binding;

    protected abstract void initView(T binding);
    protected abstract int getLayoutID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutID());

        initView(binding);
    }

    public MYTComponent getComponent(){
        return ((MYTApplication) getApplication()).getComponent();
    }
}
