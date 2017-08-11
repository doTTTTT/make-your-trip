package com.dot.makeyourtrip.views.fragment.trip;

import com.dot.makeyourtrip.model.TripModel;

import java.util.List;

public interface TripContract {
    interface View {
        public void setList(List<TripModel> list);

        public void setRefreshing(Boolean refreshing);
    }
}
