package com.ultraviolet.delieve.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class slidingUpPanelLayoutCustom extends SlidingUpPanelLayout {
    public slidingUpPanelLayoutCustom(Context context) {
        super(context);
    }

    public slidingUpPanelLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public slidingUpPanelLayoutCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


}
