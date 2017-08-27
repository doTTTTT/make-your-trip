package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class TransportModel {
    @SerializedName("_id")
    public String ID;

    @SerializedName("trip_id")
    public String TripID;

    @SerializedName("departure_location")
    public String DepartureLocation;

    @SerializedName("departure_date")
    public Date DepartureDate;

    @SerializedName("return_location")
    public String ReturnLocation;

    @SerializedName("return_date")
    public Date ReturnDate;

    @SerializedName("departure_duration")
    public String DepartureDuration;

    @SerializedName("return_duration")
    public String ReturnDuration;

    @SerializedName("price")
    public Double Price;

    @SerializedName("departure_longitude")
    public String DepartureLongitude;

    @SerializedName("departure_latitude")
    public String DepartureLatitude;

    @SerializedName("return_longitude")
    public String ReturnLongtude;

    @SerializedName("return_latitude")
    public String ReturnLatitude;

    @SerializedName("provider_images")
    public List<String> Images;

    @SerializedName("provider_name")
    public String ProviderName;

    @SerializedName("position")
    public Integer Position;

    @SerializedName("type")
    public String Type;

    @SerializedName("layovers")
    public Integer Layovers;
}
