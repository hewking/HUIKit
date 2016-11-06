package com.example.administrator.customviewtest.coordinatorlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hewking on 2016/11/6.
 */

public class FollowTcouchMoveView extends View {
    public FollowTcouchMoveView(Context context) {
        this(context,null);
    }

    public FollowTcouchMoveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FollowTcouchMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            break;
            case MotionEvent.ACTION_MOVE:
                moveFollowFinger(this,event);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }

        return false;
    }

    private void moveFollowFinger(View view,MotionEvent event) {

        int width = view.getWidth();
        int height = view.getHeight();

        float rx = event.getRawX();
        float ry = event.getRawY();

        float l = rx - width / 2;
        float t = ry - height / 2;
        float r = rx + width / 2;
        float b = ry + height / 2;

        view.layout((int)l,(int)t,(int)r,(int)b);
    }
}
