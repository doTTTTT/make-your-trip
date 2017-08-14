package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

public abstract class BaseTripModel {
    public Boolean isCurrentPosition = false;

    @SerializedName("longitude")
    public Double Longitude;

    @SerializedName("latitude")
    public Double Latitude;

    @SerializedName("name")
    public String Name;
}
