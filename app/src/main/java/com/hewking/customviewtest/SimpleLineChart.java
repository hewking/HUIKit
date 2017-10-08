package com.hewking.customviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/28.
 */
public class SimpleLineChart extends View {

    private Paint mLinePaint;
    private Paint mPointPaint;
    private Paint mAxisPaint;

    private String[] xValues;
    private String[] yValues;

    private int width;
    private int height;
    int[][] xy;

    public SimpleLineChart(Context context) {
        this(context,null);
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setTextSize(50);
        mAxisPaint.measureText("hello");

        xValues = new String[]{"1", "2", "3,", "4", "5", "6,"};
        yValues = new String[]{"5k","10K","15k","20k","25k","30k"};
        xy = new int[][]{{60, 100}, {100, 50}, {150, 150}, {200, 250}, {250, 200}, {400, 150}};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        if(wMode == MeasureSpec.EXACTLY){
            width = wSize;
        }

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if(hMode == MeasureSpec.EXACTLY){
            height = hSize;
        }



        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int i1 = height / yValues.length;
        for(int i =0 ; i < yValues.length ; i ++){
            canvas.drawText(yValues[i],0,mAxisPaint.measureText(yValues[i]) + 24 + i1 * i,mAxisPaint);
        }

        int i = width / xValues.length;
        for(int j =0 ; j < xValues.length ; j ++){
            canvas.drawText(xValues[j],mAxisPaint.measureText(xValues[j]) + j * i,height,mAxisPaint);
        }


        Path path = new Path();
        path.moveTo(xy[0][0],xy[0][1]);
        for(int k = 0 ; k < xy.length ; k ++){
            int[] ints = xy[k];
            canvas.drawCircle(ints[0],ints[1],4,mAxisPaint);
            path.lineTo(ints[0],ints[1]);
        }
        canvas.drawPath(path,mAxisPaint);

    }
}
