package com.ultraviolet.delieve.view;

import android.content.Context;
import android.view.MotionEvent;

public class LocationSearchBox extends android.support.v7.widget.AppCompatTextView {
    public LocationSearchBox(Context context) {
        super(context);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;//super.onTouchEvent(event);
    }
}
