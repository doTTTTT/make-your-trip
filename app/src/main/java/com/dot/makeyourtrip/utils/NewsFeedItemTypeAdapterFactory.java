package com.dot.makeyourtrip.utils;

import android.util.Log;

import com.dot.makeyourtrip.model.LodgeModel;
import com.dot.makeyourtrip.model.PlaceModel;
import com.dot.makeyourtrip.model.RoadMap;
import com.dot.makeyourtrip.model.TransportModel;
import com.dot.makeyourtrip.model.TripModel;
import com.dot.makeyourtrip.utils.type.Lodge;
import com.dot.makeyourtrip.utils.type.Transport;
import com.dot.makeyourtrip.views.fragment.place.PlaceAdapter;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NewsFeedItemTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!RoadMap.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);
        TypeAdapter<RoadMap.Transport> transportAdapter = gson.getDelegateAdapter(this, TypeToken.get(RoadMap.Transport.class));
        TypeAdapter<RoadMap.Lodge> lodgeAdapter = gson.getDelegateAdapter(this, TypeToken.get(RoadMap.Lodge.class));
        TypeAdapter<RoadMap.Place> placeAdapter = gson.getDelegateAdapter(this, TypeToken.get(RoadMap.Place.class));

        return (TypeAdapter<T>) new NewsFeedItemTypeAdapter(jsonElementAdapter, transportAdapter, lodgeAdapter, placeAdapter).nullSafe();
    }

    private static class NewsFeedItemTypeAdapter extends TypeAdapter<RoadMap> {
        private final TypeAdapter<JsonElement> jsonElementAdapter;
        private final TypeAdapter<RoadMap.Transport> transportAdapter;
        private final TypeAdapter<RoadMap.Lodge> lodgeAdapter;
        private final TypeAdapter<RoadMap.Place> placeAdapter;

        NewsFeedItemTypeAdapter(TypeAdapter<JsonElement> jsonElementAdapter,
                                TypeAdapter<RoadMap.Transport> transportAdapter,
                                TypeAdapter<RoadMap.Lodge> lodgeAdapter,
                                TypeAdapter<RoadMap.Place> placeAdapter) {
            this.jsonElementAdapter = jsonElementAdapter;
            this.transportAdapter = transportAdapter;
            this.lodgeAdapter = lodgeAdapter;
            this.placeAdapter = placeAdapter;
        }

        @Override
        public void write(JsonWriter out, RoadMap value) throws IOException {
            if (value.getClass().isAssignableFrom(TransportModel.class)) {
                //newsFeedArticleAdapter.write(out, (TransportModel) value);
            } else if (value.getClass().isAssignableFrom(LodgeModel.class)) {
                //newsFeedAdAdapter.write(out, (LodgeModel) value);
            }
        }

        @Override
        public RoadMap read(JsonReader in) throws IOException {
            JsonObject objectJson = jsonElementAdapter.read(in).getAsJsonObject();

            Log.d("GSONADAPTER", "Json: " + objectJson.toString());
            Log.d("GSONADAPTER", "Type: " + objectJson.get("event_type"));
            Log.d("GSONADAPTER", "TypeP: " + objectJson.get("event_type").toString().replace("\"", ""));

            switch (objectJson.get("event_type").toString().replace("\"", "")) {
                case "Place": return placeAdapter.fromJsonTree(objectJson);
                case "Transport": return transportAdapter.fromJsonTree(objectJson);
                case "Lodge": return lodgeAdapter.fromJsonTree(objectJson);
            }

            return null;
        }
    }
}