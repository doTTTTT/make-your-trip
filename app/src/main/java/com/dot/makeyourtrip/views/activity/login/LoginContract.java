package com.dot.makeyourtrip.views.activity.login;

public interface LoginContract {
    public interface View {
        void onLoginComplete();

        void onLoginError();

        void setLoginError(String error);

        void setPasswordError(String error);

        void startSignUp();
    }
}
