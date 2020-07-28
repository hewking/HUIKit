package com.hewking.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/28.
 * <p>
 * 联系方式：。。。
 */
public class MyLinearLayout extends ViewGroup {

    private int width;
    private  int height;

    private View currentView;

    public MyLinearLayout(Context context) {
        this(context,null);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                currentView = getCurrentView(event);
                currentView.layout((int)event.getX(),(int)event.getY(),currentView.getWidth() + (int)event.getX(),(int)event.getY() + currentView.getHeight());
                requestLayout();
                break;
        }
        return true;
    }

    private View getCurrentView(MotionEvent event) {

        int childCount = getChildCount();
        for(int i = 0 ; i < childCount ; i ++){
            View view = getChildAt(i);
            Rect rect = new Rect();
            view.getDrawingRect(rect);
            boolean contains = rect.contains((int) event.getX(), (int) event.getY());
            if(contains){
                return view;
            }

        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int totalWidth = 0;
        int totalHeight = 0;
        for(int i = 0 ; i < childCount ; i ++){
            View child = getChildAt(i);
            if(child == currentView){
                continue;
            }
            int childMeasuredHeight = child.getMeasuredHeight();
            int childMeasuredWidth = child.getMeasuredWidth();
            child.layout(l + totalWidth,t + totalHeight ,l + childMeasuredWidth + totalWidth,t + totalHeight +  childMeasuredHeight);
            if(totalWidth > 3 * childMeasuredWidth){
                totalWidth = 0;
                totalHeight += childMeasuredHeight;
                continue;
            }
            totalWidth += childMeasuredWidth;
        }
    }
}
