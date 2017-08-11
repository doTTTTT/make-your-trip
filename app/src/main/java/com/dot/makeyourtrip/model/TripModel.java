package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripModel {
    public Boolean isFake = false;

    @SerializedName("_id")
    public String ID;

    @SerializedName("updatedAt")
    public String UpdateAt;

    @SerializedName("createdAt")
    public String CreatedAt;

    @SerializedName("user_id")
    public String UserID;

    @SerializedName("name")
    public String Name;

    @SerializedName("roadmap")
    public List<RoadMap> RoadMaps;

    public class RoadMap {
        @SerializedName("event_type")
        public String EventType;

        @SerializedName("event_id")
        public List<Object> Events;

        @SerializedName("_id")
        public String ID;
    }
}
