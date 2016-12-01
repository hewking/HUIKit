package com.example.administrator.customviewtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/25.
 * <p>
 * 联系方式：。。。
 */
public class ShareButton extends View {

    private Paint roundBackgroudPaint;
    private Paint textPaint;

    private int width;
    private int height;


    public ShareButton(Context context) {
        this(context,null);
    }

    public ShareButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        roundBackgroudPaint = new Paint();
        roundBackgroudPaint.setAntiAlias(true);
        roundBackgroudPaint.setStyle(Paint.Style.FILL);
        roundBackgroudPaint.setColor(0xFF354FC7);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(0xFFC6B7BF);

        roundbackHeight = 50;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(width / 2,height / 2);
        drawInitRoundAndText(canvas);
    }

    private void drawInitRoundAndText(Canvas canvas) {
        RectF rectF = new RectF(-width / 4,-50,width / 4,roundbackHeight);
        canvas.drawRoundRect(rectF,40,40,roundBackgroudPaint);
        textPaint.setTextSize(40);
        String targetStr = "share button";
        canvas.drawText(targetStr,- getTextWidth(targetStr,textPaint) / 2, getTextHeight(targetStr,textPaint) /2 ,textPaint);
    }

    public float getTextWidth(String text,Paint paint){
        float measureText = paint.measureText(text);
        return measureText;

    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height() / 1.1f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float ex = event.getX() - width / 2;
                float ey = event.getY() - height / 2;
                RectF rectF = new RectF(-width / 4,-50,width / 4,50);
                if(rectF.contains(ex,ey)){
                    if(!isExpand){
                        isExpand =!isExpand;
                        startAnim();
                    }else{
                        isExpand =!isExpand;
                        exitAnim();
                    }
                }
                break;
        }

        super.onTouchEvent(event);
        return true;
    }

    private void exitAnim() {
        ValueAnimator animator = new ValueAnimator().ofFloat(4,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                roundbackHeight = (float)animation.getAnimatedValue() * 50;
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

    private float roundbackHeight;
    private boolean isExpand;

    public void startAnim(){
        ValueAnimator animator = new ValueAnimator().ofFloat(1,4);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                roundbackHeight = (float)animation.getAnimatedValue() * 50;
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }



}
