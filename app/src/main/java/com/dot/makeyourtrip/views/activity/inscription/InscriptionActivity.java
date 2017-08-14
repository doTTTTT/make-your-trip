package com.dot.makeyourtrip.views.activity.inscription;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ActivityInscriptionBinding;
import com.dot.makeyourtrip.utils.android.Activity;

public class InscriptionActivity extends Activity<ActivityInscriptionBinding> implements InscriptionContract.View {
    enum Type {
        NAME,
        EMAIL,
        PASSWORD,
        PASSWORD_COMF
    }

    private InscriptionViewModel viewModel;

    @Override
    protected void initView(ActivityInscriptionBinding binding) {
        viewModel = new InscriptionViewModel(this);
        getComponent().inject(viewModel);
        binding.setViewModel(viewModel);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_inscription;
    }

    @Override
    public void setError(String error, Type type) {
        switch (type) {
            case NAME:
                binding.userName.setError(error);
                break;
            case EMAIL:
                binding.userEmail.setError(error);
                break;
            case PASSWORD:
                binding.userPassword.setError(error);
                break;
            case PASSWORD_COMF:
                binding.userPasswordComf.setError(error);
                break;
        }
    }

    @Override
    public void setResultAndFinish(int result) {
        setResult(result);
        finish();
    }

    @Override
    public void onSignUpComplete() {
        binding.signUp.setProgress(100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResultAndFinish(android.app.Activity.RESULT_OK);
            }
        }, 2000);
    }

    @Override
    public void onSignUpError(String error) {
        if (error != null && !error.isEmpty()){
            binding.signUp.setErrorText(error);
        }
        binding.signUp.setErrorText("Please verify champ");
        binding.signUp.setProgress(-1);
    }
}
