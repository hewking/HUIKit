package com.hewking.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/18.
 */
public class QQHealthView extends View {
    public QQHealthView(Context context) {
        this(context,null);
    }

    public QQHealthView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

    }
}
