package com.hewking.widget.drawerlayout;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/11/21.
 * <p>
 * 联系方式：。。。
 */
public class HDrawerLayout extends ViewGroup {

    private int count;
    private Scroller  mScroller;

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
        mScroller = new Scroller(getContext(),new AccelerateDecelerateInterpolator());
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
        int left = -250;
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
                if(disX > 0){
                    return false;
                }
                if(disX < -250){

                    return false;
                }else{
                    scrollBy(-dx,0);
                }

//                getChildAt(1).setScaleX(1 - Math.abs(disX) / 300f);
                getChildAt(1).setScaleY(1 - Math.abs(disX) / 500f);
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollX() < -125){
                    smoothTo(-250,0);
                    getChildAt(1).setScaleY(0.5f);
                }else if(getScrollX() >-125){
                    smoothTo(0,0);
                    getChildAt(1).setScaleY(1);
                }
                point.x = 0;

                break;
        }

        super.dispatchTouchEvent(ev);
        return true;
    }

    private void smoothTo(int dstX ,int dstY){
        int deletaX = dstX - getScrollX();
        mScroller.startScroll(getScrollX(),0,deletaX,0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
