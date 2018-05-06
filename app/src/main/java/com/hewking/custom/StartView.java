package com.hewking.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/11.
 */
public class StartView extends View {

    private int mHeight;
    private int mWidth;

    private Paint mPaint;

    public StartView(Context context) {
        this(context,null);
    }

    public StartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        Path path = new Path();
        canvas.drawRect(rectF,mPaint);
        path.moveTo(rectF.centerX(),rectF.centerY());
        for (int i =0 ; i < 5 ; i ++){
            canvas.rotate(72,rectF.centerX(),rectF.centerY());
            canvas.drawCircle(rectF.centerX() + 100,rectF.centerY() + 100,1,mPaint);
            canvas.drawLine(rectF.centerX() + 30,rectF.centerY(),rectF.centerX() + 100,rectF.centerY() + 100,mPaint);
            canvas.drawLine(rectF.centerX(),rectF.centerY()+ 30,rectF.centerX() + 100,rectF.centerY() + 100,mPaint);
        }
        canvas.rotate(- 72 * 5,rectF.centerX(),rectF.centerY());
        super.onDraw(canvas);
    }
}
