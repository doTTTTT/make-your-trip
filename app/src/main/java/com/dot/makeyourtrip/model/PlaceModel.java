package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceModel {
    @SerializedName("longitude")
    public Double Longitude;

    @SerializedName("latitude")
    public Double Latitude;

    @SerializedName("name")
    public String Name;

    @SerializedName("totals_days")
    public Integer TotalDays;

    @SerializedName("trip_id")
    public String TripID;

    @SerializedName("location")
    public String Location;

    @SerializedName("start_date")
    public String StartDate;

    @SerializedName("end_date")
    public String EndDate;

    @SerializedName("_id")
    public String ID;

    @SerializedName("provider_images")
    public List<String> Images;

    @SerializedName("provider_name")
    public String ProviderName;

    @SerializedName("provider_id")
    public String ProviderId;

    @SerializedName("position")
    public Integer Position;

    @SerializedName("type")
    public String Type;
}
