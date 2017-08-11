package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("name")
    public String Name;

    @SerializedName("role")
    public String Role;

    @SerializedName("_id")
    public String ID;

    public class UserPostModel {
        @SerializedName("token")
        public String Token;

        @SerializedName("user")
        public UserModel User;
    }
}
