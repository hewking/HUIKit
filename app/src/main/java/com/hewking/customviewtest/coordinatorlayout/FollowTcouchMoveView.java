package com.hewking.customviewtest.coordinatorlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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

    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveFollowFinger(this,event);
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

        return true;
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
        Log.e("movefollowfinger",l + ":" + t + ":" + r + ":" + b);
        view.layout((int)l,(int)t,(int)r,(int)b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2,getHeight() / 2);
        canvas.drawCircle(0,0,100,paint);
    }
}
