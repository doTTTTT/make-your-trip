package com.dot.makeyourtrip.views.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ActivityLoginBinding;
import com.dot.makeyourtrip.utils.android.Activity;
import com.dot.makeyourtrip.views.activity.inscription.InscriptionActivity;
import com.dot.makeyourtrip.views.activity.main.MainActivity;

public class LoginActivity extends Activity implements LoginContract.View {

    private static final int SIGN_UP_REQUEST = 1;

    private ActivityLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel viewModel = new LoginViewModel(this);
        getComponent().inject(viewModel);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onLoginComplete() {
        binding.loginIn.setProgress(100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    public void onLoginError() {
        binding.loginIn.setProgress(-1);
    }

    @Override
    public void setLoginError(String error) {
        binding.userLogin.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        binding.userPassword.setError(error);
    }

    @Override
    public void startSignUp() {
        startActivityForResult(new Intent(this, InscriptionActivity.class),SIGN_UP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SIGN_UP_REQUEST:
                if (resultCode == android.app.Activity.RESULT_OK) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }
}
