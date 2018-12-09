package com.hewking.custom.tinderstack;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hewking.custom.util.ViewExKt;

/**
 * Created by hewking on 2017/3/30.
 */

public class TinderStackLayout extends ViewGroup {

    public static final float DEFAULT_SCALE = 0.1f;
    public static final int DEFAULT_OFFSET = 5;//dp
    public static final int DEFAULT_MARGIN = 10;//dp

    private ViewDragHelper mDragHelper;

    private Point mPoint = new Point();

    public TinderStackLayout(Context context) {
        this(context,null);
    }

    public TinderStackLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TinderStackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this,1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return indexOfChild(child) == getChildCount() -1;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (indexOfChild(releasedChild) == getChildCount() -1) {
                    mDragHelper.settleCapturedViewAt(mPoint.x,mPoint.y);
                    invalidate();
                }

            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {

            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return super.getViewVerticalDragRange(child);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float scale = 1f;
        for (int i = getChildCount() -1 ; i > 0 ; i --) {
            View child = getChildAt(i);
            float scaleValue = scale - DEFAULT_SCALE * (getChildCount() - 1) * i;
            child.setScaleX(scaleValue);
            child.setScaleY(scaleValue);

            int margin = ViewExKt.dp2px(this,DEFAULT_MARGIN);
            int offset= ViewExKt.dp2px(this,DEFAULT_OFFSET);
            int scaledWidth = (int) (child.getMeasuredWidth() * scaleValue);
            int scaledHeight = (int) (child.getMeasuredHeight() * scaleValue);
            int childPadding = (getMeasuredWidth() - scaledWidth) / 2;
            child.layout(childPadding
                    ,t + margin + offset
                    ,childPadding + scaledWidth
                    ,t + margin + scaledHeight + offset);
            if (i == getChildCount() -1) {
                mPoint.x = childPadding;
                mPoint.y = childPadding + scaledWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }
}
