package com.hewking.customviewtest.slidemenu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by hewking on 2016/11/23.
 */

public class MySliceMenu extends LinearLayout {

    private LeftContainter mleftContainer;
    private RightContainter mRightContainter;

    private int leftLength;

    public MySliceMenu(Context context) {
        this(context,null);
    }

    public MySliceMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySliceMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        leftLength = 150;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mleftContainer = (LeftContainter) getChildAt(0);
//        mRightContainter = (RightContainter) getChildAt(1);
//        mleftContainer.measure(MeasureSpec.makeMeasureSpec(leftLength,MeasureSpec.EXACTLY),heightMeasureSpec);
//        mRightContainter.measure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        getChildAt(0).layout(- leftLength,t,0,b);
//        getChildAt(1).layout(l,t,r,b);
        super.onLayout(changed,l,t,r,b);
    }

    private Point point = new Point();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                point.x = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - point.x);
                int disX = getScrollX() + -dx;
                point.x = (int) ev.getX();
                if(disX < - leftLength){

                    return false;
                }else{
                    scrollBy(disX,0);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                scrollTo(- getScrollX(),0);
                invalidate();
                break;
        }


        super.dispatchTouchEvent(ev);
        return true;
    }
}
