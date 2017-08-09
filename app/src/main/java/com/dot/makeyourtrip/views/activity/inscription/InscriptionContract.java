package com.dot.makeyourtrip.views.activity.inscription;

public interface InscriptionContract {
    public interface View {
        void setError(String error, InscriptionActivity.Type type);

        void setResultAndFinish(int result);

        void onSignUpComplete();

        void onSignUpError(String Error);
    }
}
