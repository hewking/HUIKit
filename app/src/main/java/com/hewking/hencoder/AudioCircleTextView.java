package com.hewking.hencoder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * Created by test on 2017/10/8.
 * <p>
 * 需求分析
 * 1. 首先textview 需要能够正常显示文字 并且附带圆形 背景
 * <p>
 * 2. 可以切换到动画模式 并且能够持续间隔播放动画
 * <p>
 * 3. 分析动画 七个 矩形柱 初始状态
 * <p>
 * 4. 矩形一般宽度为  view 宽度 去掉 padding 的 十六分之一
 * <p>
 * 然后x轴正向 一次加 两个 十六分之一 值  为 另一个矩形出师坐标
 * <p>
 * 高度递减 十六分支一
 */

public class AudioCircleTextView extends android.support.v7.widget.AppCompatTextView {

    private int mHeight;
    private int mWidth;

    private int currentAnim = 2;

    public AudioCircleTextView(Context context) {
        this(context, null);
    }

    public AudioCircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioCircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    private ValueAnimator breathAnimator;

    private void stopAnim(){
        if (breathAnimator != null) {
            breathAnimator.cancel();
        }
    }

    private void startAnim(){
        if (breathAnimator == null) {
            breathAnimator = ValueAnimator.ofFloat(0,1).setDuration(500);
            breathAnimator.setInterpolator(new LinearInterpolator());
            breathAnimator.setRepeatCount(ValueAnimator.INFINITE);
            breathAnimator.setRepeatMode(ValueAnimator.RESTART);
            breathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    currentAnim = (int)(value * 4 + 1);
                    postInvalidate();
                }
            });

            breathAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    breathAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            breathAnimator.start();

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        if (wmode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
//            mWidth = UiUtil.dipToPx(getContext(),200);
            mWidth = getPaddingLeft() + getPaddingRight() + (int) getTextWidth("开始录制", getPaint());
        }

        if (hmode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = getPaddingTop() + getPaddingBottom() + (int) getTextWidth("开始录制", getPaint());
        }

        setMeasuredDimension(mWidth, mHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = getPaint();
        paint.setColor(Color.RED);
        canvas.drawCircle(mWidth / 2, mHeight / 2, Math.max(mWidth / 2, mHeight / 2), paint);

        drawAnimRect(canvas);

        super.onDraw(canvas);
    }

    private void drawAnimRect(Canvas canvas) {
        getPaint().setColor(Color.BLACK);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        int halfW = (mWidth - getPaddingLeft() - getPaddingRight()) / 16;
        int rectH = (mHeight - getPaddingTop() - getPaddingBottom()) / 2;

        for (int i = 1; i < currentAnim; i++) {
            int left = (i - 1) * 3 * halfW;
            int right = left + 2 * halfW;
            if (i == 1) {
                left = 0;
                right = left + halfW;
            }
            Rect rect = new Rect(left
                    , rectH - (i - 1) * halfW
                    , right
                    , -rectH + (i - 1) * halfW);
            canvas.drawRect(rect, getPaint());
        }

        for (int i = 1; i < currentAnim; i++) {
            int left = -(i - 1) * 3 * halfW;
            int right = left - 2 * halfW;
            if (i == 1) {
                left = 0;
                right = left - halfW;
            }
            Rect rect = new Rect(left
                    , rectH - (i - 1) * halfW
                    , right
                    , -rectH + (i - 1) * halfW);
            canvas.drawRect(rect, getPaint());
        }

        canvas.restore();


    }

    public float getTextWidth(String text, Paint paint) {
        float measureText = paint.measureText(text);
        return measureText;

    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height() / 1.1f;
    }


}
