package com.dot.makeyourtrip.views.activity.trip.timeline;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}