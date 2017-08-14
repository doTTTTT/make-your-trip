package com.dot.makeyourtrip.views.activity.lodge;

import java.util.Calendar;

public interface LodgeContract {
    public interface View {
        void setCheckIn(String checkIn);

        void setCheckOut(String checkOut);

        void finishActivity();
    }
}
