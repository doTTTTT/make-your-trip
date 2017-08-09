package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceModel {
    @SerializedName("totals_days")
    public Integer TotalDays;

    @SerializedName("name")
    public String Name;

    @SerializedName("trip_id")
    public Integer TripID;

    @SerializedName("location")
    public String Location;

    @SerializedName("start_date")
    public String StartDate;

    @SerializedName("end_date")
    public String EndDate;

    @SerializedName("longitude")
    public Double Longitude;

    @SerializedName("latitude")
    public Double Latitude;

    @SerializedName("_id")
    public String ID;

    @SerializedName("provider_images")
    public List<String> Images;

    @SerializedName("provider_name")
    public String ProviderName;

    @SerializedName("position")
    public Integer Position;

    @SerializedName("type")
    public String Type;
}
