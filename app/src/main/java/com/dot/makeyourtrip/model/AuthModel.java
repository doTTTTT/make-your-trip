package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

public class AuthModel {
    @SerializedName("token")
    public String Token;

    @SerializedName("user_id")
    public String ID;
}
