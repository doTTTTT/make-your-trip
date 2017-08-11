package com.dot.makeyourtrip.views.activity.login;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.AuthModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.Authentification;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseObservable implements Callback<AuthModel> {
    private static final String TAG = LoginViewModel.class.getSimpleName();

    @Inject Authentification.AuthentificationInterface authentification;
    @Inject ApiUtils apiUtils;
    @Inject MYTManager manager;

    private LoginContract.View loginView;

    public LoginViewModel(LoginContract.View view){
        this.loginView = view;
    }

    public void onClickLoggin(final View view, AppCompatEditText login, AppCompatEditText password) {
        boolean error = false;

        final CircularProgressButton button = (CircularProgressButton) view;
        button.setProgress(1);
        button.setIndeterminateProgressMode(true);

        if (login.getText().toString().isEmpty()) {
            loginView.setLoginError("Cannot be empty");
            button.setProgress(-1);
            error = true;
        } else {
            loginView.setLoginError(null);
        }

        if (password.getText().toString().isEmpty()) {
            loginView.setPasswordError("Cannot be empty");
            button.setProgress(-1);
            error = true;
        } else {
            loginView.setPasswordError(null);
        }

        if (!error) {
            button.setProgress(1);
            loginView.setLoginError(null);
            loginView.setPasswordError(null);
            authentification.authentification(login.getText().toString(), password.getText().toString()).enqueue(this);
        }
    }

    public void onClickSignUp(View view) {
        loginView.startSignUp();
    }

    @BindingAdapter("loadImageBackground")
    public static void loadImage(ImageView view, String image){
        Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 16)).into(view);
    }

    @Override
    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 200:
                manager.logIn(response.body().Token, response.body().ID);
                loginView.onLoginComplete();
                break;
            default:
                apiUtils.parseErrorAndShow(TAG, response);
                loginView.onLoginError();
                break;
        }
    }

    @Override
    public void onFailure(Call<AuthModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
        loginView.onLoginError();
    }
}
