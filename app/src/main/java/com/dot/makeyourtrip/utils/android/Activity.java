package com.dot.makeyourtrip.utils.android;

import android.support.v7.app.AppCompatActivity;

import com.dot.makeyourtrip.utils.MYTApplication;
import com.dot.makeyourtrip.utils.MYTComponent;

public abstract class Activity extends AppCompatActivity {
    public MYTComponent getComponent(){
        return ((MYTApplication) getApplication()).getComponent();
    }
}
