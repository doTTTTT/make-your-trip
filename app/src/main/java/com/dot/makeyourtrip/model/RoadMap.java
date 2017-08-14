package com.dot.makeyourtrip.model;

import com.google.gson.annotations.SerializedName;

public class RoadMap {
    @SerializedName("event_type")
    public String EventType;

    public class Place extends RoadMap {
        public static final int VIEW_TYPE = 0;

        @SerializedName("event_id")
        public PlaceModel Events;

        @SerializedName("_id")
        public String ID;
    }

    public class Lodge extends RoadMap {
        public static final int VIEW_TYPE = 1;

        @SerializedName("event_id")
        public LodgeModel Events;

        @SerializedName("_id")
        public String ID;
    }

    public class Transport extends RoadMap {
        public static final int VIEW_TYPE = 2;

        @SerializedName("event_id")
        public TransportModel Events;

        @SerializedName("_id")
        public String ID;
    }
}