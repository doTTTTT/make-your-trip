package com.dot.makeyourtrip.views.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.dd.CircularProgressButton;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ActivityMainBinding;
import com.dot.makeyourtrip.utils.BottomNavigationViewHelper;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.utils.type.Authentification;
import com.dot.makeyourtrip.views.activity.login.LoginActivity;

import javax.inject.Inject;

public class MainActivity extends Activity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    @Inject Authentification.AuthentificationInterface authentification;
    @Inject MYTManager manager;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        if (manager.isLoggedIn()) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            binding.setViewModel(new MainViewModel());
            BottomNavigationViewHelper.removeShiftMode(binding.bottomMenu);

            binding.bottomMenu.setOnNavigationItemSelectedListener(this);
            binding.container.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(final View v) {
        ((CircularProgressButton) v).setProgress(1);
        ((CircularProgressButton) v).setIndeterminateProgressMode(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((CircularProgressButton) v).setProgress(100);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((CircularProgressButton) v).setProgress(0);
                    }
                }, 3000);
            }
        }, 3000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_trips:
                binding.container.setCurrentItem(0);
                break;
            case R.id.action_place:
                binding.container.setCurrentItem(1);
                break;
            case R.id.action_lodge:
                binding.container.setCurrentItem(2);
                break;
        }
        return true;
    }
}
