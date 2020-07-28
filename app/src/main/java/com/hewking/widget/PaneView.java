package com.hewking.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/28.
 */
public class PaneView  extends View {

    private Paint mArcPaint;
    private Paint mSArcPaint;

    private int strokeWidth;

    private int height;
    private int width;

    private int mPrecent;

    public PaneView(Context context) {
        this(context,null);
    }

    public PaneView(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public PaneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(5);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setColor(Color.BLUE);

        mSArcPaint = new Paint();
        mSArcPaint.setAntiAlias(true);
        mSArcPaint.setStyle(Paint.Style.STROKE);
        mSArcPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if(wMode == MeasureSpec.EXACTLY){
            width = mWidth;
        }

        if(hMode == MeasureSpec.EXACTLY){
            height = hSize;
        }
        setMeasuredDimension(mWidth, hSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(new RectF(5,5 ,width - 5 ,height -5),150,240,false,mArcPaint);

        strokeWidth = 50;

        if(mPrecent == 0){
            mSArcPaint.setColor(Color.WHITE);
        }

        RectF rectF = new RectF(strokeWidth + 5, strokeWidth + 5, width - strokeWidth - 5, height - strokeWidth - 5);
        mSArcPaint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF,120,300,false,mSArcPaint);

//        mArcPaint.setStrokeWidth(50);
//        new RectF(50)
    }
}
