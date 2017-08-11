package com.dot.makeyourtrip.views.activity.inscription;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.model.UserModel;
import com.dot.makeyourtrip.utils.ApiUtils;
import com.dot.makeyourtrip.utils.MYTManager;
import com.dot.makeyourtrip.utils.type.User;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionViewModel extends BaseObservable implements Callback<UserModel.UserPostModel> {
    private static final String TAG = InscriptionViewModel.class.getSimpleName();

    @Inject User.UserRequest userRequest;
    @Inject ApiUtils apiUtils;
    @Inject MYTManager manager;

    private InscriptionContract.View inscriptionView;

    public InscriptionViewModel(InscriptionContract.View view) {
        this.inscriptionView = view;
    }

    private boolean verifyChamp(EditText champ, InscriptionActivity.Type type, boolean error){
        if (champ.getText().toString().isEmpty()) {
            inscriptionView.setError("Cannot be empty", type);
            return true;
        }
        inscriptionView.setError(null, type);
        return error ? true : false;
    }

    public void onClickSignUp(View view, AppCompatEditText name, AppCompatEditText email, AppCompatEditText password, AppCompatEditText passwordComf){
        boolean error = false;

        final CircularProgressButton button = (CircularProgressButton) view;
        button.setProgress(1);
        button.setIndeterminateProgressMode(true);

        error = verifyChamp(name, InscriptionActivity.Type.NAME, error);
        error = verifyChamp(email, InscriptionActivity.Type.EMAIL, error);
        error = verifyChamp(password, InscriptionActivity.Type.PASSWORD, error);
        error = verifyChamp(passwordComf, InscriptionActivity.Type.PASSWORD_COMF, error);

        if (!error) {
            button.setProgress(1);
            userRequest.createUser(email.getText().toString(),
                    password.getText().toString(),
                    name.getText().toString()).enqueue(this);
        } else {
            button.setProgress(-1);
        }
    }

    @BindingAdapter("loadImageBackground")
    public static void loadImage(ImageView view, String image){
        Glide.with(view.getContext()).load(R.drawable.background).bitmapTransform(new BlurTransformation(view.getContext(), 16)).into(view);
    }

    @Override
    public void onResponse(Call<UserModel.UserPostModel> call, Response<UserModel.UserPostModel> response) {
        Log.d(TAG, "Code: " + response.code());

        switch (response.code()) {
            case 201:
                manager.logIn(response.body().Token, response.body().User.ID);
                inscriptionView.onSignUpComplete();
                break;
            default:
                String error = apiUtils.parseErrorAndShow(TAG, response);
                inscriptionView.onSignUpError(error);
                break;
        }
    }

    @Override
    public void onFailure(Call<UserModel.UserPostModel> call, Throwable t) {
        Log.e(TAG, "" + t.getMessage());
        inscriptionView.onSignUpError("Request Failed");
    }
}
