package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LodgeModel {
    @SerializedName("totals_days")
    public Integer TotalDays;

    @SerializedName("trip_id")
    public String TripID;

    @SerializedName("name")
    public String Name;

    @SerializedName("location")
    public String Location;

    @SerializedName("start_date")
    public String StartDate;

    @SerializedName("end_date")
    public String EndDate;

    @SerializedName("_id")
    public String ID;

    @SerializedName("longitude")
    public Double Longitude;

    @SerializedName("latitude")
    public Double Latitude;

    @SerializedName("provider_images")
    public List<String> Images;

    @SerializedName("provider_name")
    public String ProviderName;

    @SerializedName("provider_url")
    public String ProviderUrl;

    @SerializedName("provider_id")
    public String ProviderID;

    @SerializedName("position")
    public Integer Position;

    @SerializedName("type")
    public String Type;
}
