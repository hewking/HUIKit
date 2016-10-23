package com.example.administrator.customviewtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/22.
 */
public class SearchView extends View {

    private Paint mPaint;
    private int width;
    private int height;

    private Path circle_path;
    private Path search_path;

    public static final int STATUS_INIT = 0;
    public static final int STATUS_PRE_SEARCH = 1;
    public static final int STATUS_SEARCHING = 2;
    public static final int STATUS_END_SEARCH = 3;

    private int currentStatus = 0;

    private float circle_start_distance;
    private float circle_end_distance;

    private ValueAnimator.AnimatorUpdateListener circleTranslationsListener;


    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void startAnim(){
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        initPath();
        initListener();
    }

    private void initListener() {
        circleTranslationsListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                circle_start_distance = value * 1f;
                circle_end_distance = value * 2f;
            }
        };
        ValueAnimator circleTranslatonAnimator = new ValueAnimator();
        circleTranslatonAnimator.ofFloat(0,1);
        circleTranslatonAnimator.setDuration(2000).addUpdateListener(circleTranslationsListener);
        circleTranslatonAnimator.setRepeatCount(3);
        circleTranslatonAnimator.start();
    }

    private void initPath() {
        circle_path = new Path();
        circle_path.addCircle(0,0,200, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2,height / 2);

        switch (currentStatus){
            case STATUS_INIT:
                break;
            case STATUS_PRE_SEARCH:

                break;
            case STATUS_SEARCHING:
                android.graphics.PathMeasure pathMeasure = new android.graphics.PathMeasure();
                pathMeasure.setPath(circle_path,false);
                Path arc = new Path();
                pathMeasure.getSegment(circle_start_distance * pathMeasure.getLength(),circle_end_distance * pathMeasure.getLength(),arc,true);
                canvas.drawPath(arc,mPaint);
                break;
            case STATUS_END_SEARCH:
                break;
        }

    }
}
