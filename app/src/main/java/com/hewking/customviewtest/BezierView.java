package com.hewking.customviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class BezierView extends View {

    private Paint mPaint;
    private PointF start,end,control;
    private int width,height;

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        start.x = width / 2 - 200;
        start.y = height / 2;
        end.x = width / 2 + 200;
        end.y = height /2 ;
        control.x = width / 2 ;
        control.y = height + 100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        start = new PointF();
        control = new PointF();
        end = new PointF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(4);
        canvas.drawPoints(new float[]{
                start.x,start.y,
                control.x,control.y,
                end.x,end.y
        },mPaint);

        //花辅助线
        Path contronPath  = new Path();
        contronPath.moveTo(start.x,start.y);
        contronPath.lineTo(control.x,control.y);
        contronPath.lineTo(end.x,end.y);
        canvas.drawPath(contronPath,mPaint);

        mPaint.setColor(Color.RED);

        Path bezierPath = new Path();
        bezierPath.moveTo(start.x,start.y);
        bezierPath.quadTo(control.x,control.y,end.x,end.y);
        canvas.drawPath(bezierPath,mPaint);
    }
}
