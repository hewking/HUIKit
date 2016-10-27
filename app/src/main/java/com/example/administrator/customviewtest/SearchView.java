package com.example.administrator.customviewtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hewking on 2016/10/22.
 */
public class SearchView extends View {

    private Paint mPaint;
    private int width;
    private int height;

    private Path path_search;
    private Path path_circle;
    android.graphics.PathMeasure pathMeasure;

    public static final int STATUS_INIT = 0;
    public static final int STATUS_PRE_SEARCH = 1;
    public static final int STATUS_SEARCHING = 2;
    public static final int STATUS_END_SEARCH = 3;

    private int currentStatus = 0;

    private float animator_update_value;

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;
    private ValueAnimator searchTranslatonAnimator;
    private ValueAnimator circleTranslationAnimator;

    private Handler mHandler;

    private boolean isOver;


    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
                startAnim();
//            }
//        },3000);
    }

    public void startAnim(){
        currentStatus = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void init() {
        initPaint();
        initPath();
        initListener();
        initAnimator();
        initHandler();
    }

    private void initAnimator() {
        searchTranslatonAnimator = ValueAnimator.ofFloat(0,1);
        searchTranslatonAnimator.setDuration(2000).addUpdateListener(animatorUpdateListener);
        searchTranslatonAnimator.addListener(animatorListener);
//        searchTranslatonAnimator.start();

        circleTranslationAnimator = ValueAnimator.ofFloat(0,1);
        circleTranslationAnimator.setDuration(2000).addUpdateListener(animatorUpdateListener);
        circleTranslationAnimator.addListener(animatorListener);
//        circleTranslationAnimator.start();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
    }

    private int count = 0;

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (currentStatus){
                    case STATUS_INIT:
                        searchTranslatonAnimator.start();
                        currentStatus = 1;
                        break;
                    case STATUS_PRE_SEARCH:
                        circleTranslationAnimator.start();
                        currentStatus = 2;
                        break;
                    case STATUS_SEARCHING:
                        if(!isOver){
                            count ++;
                            if(count > 3){
                                isOver = true;
                                currentStatus = 3;
                            }else{
                                circleTranslationAnimator.start();
                            }
                        }else{
                            searchTranslatonAnimator.start();
                        }
                        break;
                    case STATUS_END_SEARCH:
                        break;
                }
            }
        };
    }

    private void initListener() {
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animator_update_value = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initPath() {
        path_search = new Path();
        RectF oval1 = new RectF(-50,-50,50,50);
        path_search.addArc(oval1,45,359.9f);
        RectF oval2 = new RectF(-100,-100,100,100);
        path_circle = new Path();
        path_circle.addArc(oval2,45,359.f);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(path_circle,false);
        float[] pos = new float[2];
        pathMeasure.getPosTan(0,pos,null);
        path_search.lineTo(pos[0],pos[1]);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2,height / 2);

        switch (currentStatus){
            case STATUS_INIT:
                canvas.drawPath(path_search,mPaint);
                break;
            case STATUS_PRE_SEARCH:
                pathMeasure.setPath(path_search,false);
                Path step = new Path();
                pathMeasure.getSegment(pathMeasure.getLength() * animator_update_value,pathMeasure.getLength(),step,true);
                canvas.drawPath(step,mPaint);
                Log.e("SearchView",animator_update_value + "shaqingk");
                break;
            case STATUS_SEARCHING:
                pathMeasure.setPath(path_circle,false);
                Path arc = new Path();
                float circle_start_distance = animator_update_value;
                float circle_end_distance = animator_update_value * 2f;
                pathMeasure.getSegment(circle_start_distance * pathMeasure.getLength(),circle_end_distance * pathMeasure.getLength(),arc,true);
                canvas.drawPath(arc,mPaint);
                Log.e("SearchView",animator_update_value + "STATUS_SEARCHING");
                break;
            case STATUS_END_SEARCH:
                pathMeasure.setPath(path_search,false);
                Path step_end = new Path();
                pathMeasure.getSegment(pathMeasure.getLength() * (1- animator_update_value),pathMeasure.getLength(),step_end,true);
                canvas.drawPath(step_end,mPaint);
                break;
        }

    }
}
