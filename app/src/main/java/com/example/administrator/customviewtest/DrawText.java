package com.example.administrator.customviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class DrawText extends View{

    public String text = "ABCDEFGHIJK";

    private Paint mPaint;

    public DrawText(Context context) {
        this(context,null);
    }

    public DrawText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text,100.0f,200.0f,mPaint);

        canvas.drawText(text,2,4,100f,200f,mPaint);

        canvas.drawPosText(text,new float[]{
                0,0,
                20,20,
                40,40,
                60,60
        },mPaint);

    }
}
