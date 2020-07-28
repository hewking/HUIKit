package com.hewking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by hewking on 2016/10/29.
 */

public class HSwipeRefleshLayout extends FrameLayout {
    public HSwipeRefleshLayout(Context context) {
        this(context,null);
    }

    public HSwipeRefleshLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HSwipeRefleshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
