package com.example.administrator.customviewtest.drawerlayout;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/21.
 * <p>
 * 联系方式：。。。
 */
public class HDrawerLayout extends ViewGroup {

    private int count;

    public HDrawerLayout(Context context) {
        this(context, null);
    }

    public HDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        count = getChildCount();
        if (count > 2) {
            throw new IllegalArgumentException();
        }
        int left = -150;
        getChildAt(0).layout(left, t, 0, b);
        getChildAt(1).layout(l, t, r, b);
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private Point point = new Point();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point.x = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - point.x);
                int disX = getScrollX() + -dx;
                point.x = (int) ev.getX();
                if(disX < -150){

                    return false;
                }else{
                    scrollBy(-dx,0);
                }

                break;
            case MotionEvent.ACTION_UP:
                point.x = 0;
                scrollTo(0,0);

                break;
        }

        super.dispatchTouchEvent(ev);
        return true;
    }
}
