package com.example.administrator.customviewtest;

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
public class Bezier2View extends View {

    private Paint mPaint;
    private PointF start,end,control1,control2;
    private int width,height;

    public Bezier2View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Bezier2View(Context context) {
        this(context,null);
    }

    public Bezier2View(Context context, AttributeSet attrs, int defStyleAttr) {
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
        control1.x = width / 2 - 100;
        control1.y = height + 100;
        control2.x = width /2 + 100;
        control2.y = height / 2 + 100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control1.x = event.getX();
        control2.y = event.getY();
        invalidate();
        return true;

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        start = new PointF();
        control1 = new PointF();
        control2 = new PointF();
        end = new PointF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoints(new float[]{
                start.x,start.y,
                control1.x,control1.y,
                control2.x,control2.y,
                end.x,end.y
        },mPaint);

        //花辅助线
        mPaint.setStrokeWidth(4);
        Path contronPath  = new Path();
        contronPath.moveTo(start.x,start.y);
        contronPath.lineTo(control1.x,control1.y);
        contronPath.lineTo(control2.x,control2.y);
        contronPath.lineTo(end.x,end.y);
        canvas.drawPath(contronPath,mPaint);

        mPaint.setColor(Color.RED);

        Path bezierPath = new Path();
        bezierPath.moveTo(start.x,start.y);
        bezierPath.cubicTo(control1.x,control1.y,control2.x,control2.y,end.x,end.y);
        canvas.drawPath(bezierPath,mPaint);
    }
}
