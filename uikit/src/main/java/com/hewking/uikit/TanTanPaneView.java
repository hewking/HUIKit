package com.hewking.uikit;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TanTanPaneView extends View {

    public static final String TAG = TanTanPaneView.class.getSimpleName();

    private int mHeight;
    private int mWidth;

    private Paint mPaint;

    private float mWaterRippleLineRate = 4;
    private int mWaterRippleLineAlpha = 255;

    public TanTanPaneView(Context context) {
        this(context,null);
    }

    public TanTanPaneView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TanTanPaneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFFCC202C);
        mPaint.setAntiAlias(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                startWaterRippleAnim();
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private AnimatorSet animatorSet;
    List<AnimatorSet> animatorSets = new ArrayList<>();

    private void startWaterRippleAnim() {
//        if(animatorSet != null && animatorSet.isRunning()){
//            return;
//        }
        animatorSet = new AnimatorSet();
        animatorSets.add(animatorSet);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(4, 2);
        valueAnimator.addUpdateListener(waterRippleListener);

        ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 0);
        alphaAnim.addUpdateListener(aplhaRippleListener);
        animatorSet.playTogether(valueAnimator,alphaAnim);
        animatorSet.setDuration(3000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mPaint.setAlpha(255);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG,"onAnimationEnd");
                animatorSets.remove(animatorSet);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG,"onAnimationCanel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        animatorSet.start();
        Log.e(TAG,animatorSets.size() + "");
        for (AnimatorSet animatorSet : animatorSets) {
            if(!animatorSet.isRunning()){
                Log.e(TAG, "isRunnint");
                animatorSet.start();
            }
        }
    }

    private ValueAnimator.AnimatorUpdateListener waterRippleListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mWaterRippleLineRate = (float) animation.getAnimatedValue();
//            Log.e(TAG,"waterripplelistener onAnimationUpdate "  + mWaterRippleLineRate);
            invalidate();
        }
    };
    private ValueAnimator.AnimatorUpdateListener aplhaRippleListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mWaterRippleLineAlpha = (int) animation.getAnimatedValue();
            mPaint.setAlpha(mWaterRippleLineAlpha);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        if(MeasureSpec.EXACTLY == wMode){
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if(MeasureSpec.EXACTLY == hMode){
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(mWidth / 2 ,mHeight / 2,mHeight / 2 , mPaint);
        canvas.drawCircle(mWidth / 2 ,mHeight / 2,mHeight / mWaterRippleLineRate , mPaint);
        super.onDraw(canvas);
    }
}
