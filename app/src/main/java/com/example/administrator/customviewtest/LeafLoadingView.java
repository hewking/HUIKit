package com.example.administrator.customviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/11/14.
 * <p>
 * 联系方式：。。。
 */
public class LeafLoadingView extends View {

    public static final String TAG = LeafLoadingView.class.getSimpleName();

    private static final int MAX_PROGRESS = 100;
    private int width;
    private int height;

    private Paint boundPaint;

    private int maxProgress = MAX_PROGRESS;
    private int currentProgress = 0;
    private int progressRectLen;
    private int mArcRadius;



    public LeafLoadingView(Context context) {
        this(context,null);
    }

    public LeafLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeafLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        boundPaint = new Paint();
        boundPaint.setStyle(Paint.Style.STROKE);
        boundPaint.setStrokeWidth(2);
        boundPaint.setAntiAlias(true);
        boundPaint.setColor(Color.parseColor("#FFA34779"));
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            currentProgress ++;
            if(currentProgress > MAX_PROGRESS){
                return;
            }
            invalidate();
        }
    };


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mArcRadius = 50 ;
        progressRectLen = - mArcRadius - 300;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画布移动到view 中心
        canvas.translate(width / 2,height / 2);
        if(progressRectLen  < -300){
            boundPaint.setStyle(Paint.Style.FILL);
            progressRectLen += currentProgress * 6;
            float cosAngle = 50 - 6 * currentProgress)/50;
            int angle = (int) Math.toDegrees(Math.acos(());
            Log.e(TAG,"ondraw angle : " + angle + "currentProgress : " + currentProgress);
            int startAngle = 180 - angle;
            int swipeAngle = 2 * angle;
            canvas.drawArc(new RectF(-300 - mArcRadius,- mArcRadius,-300 + mArcRadius , mArcRadius),startAngle,swipeAngle,false,boundPaint);
            boundPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(new RectF(-300 - mArcRadius,- mArcRadius,-300 + mArcRadius , mArcRadius),90,180,false,boundPaint);
            drawProgress(canvas);
        }else{
            boundPaint.setStyle(Paint.Style.FILL);
            drawProgress(canvas);
            canvas.drawArc(new RectF(-300 - mArcRadius,- mArcRadius,-300 + mArcRadius , mArcRadius),90,180,false,boundPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        boundPaint.setStyle(Paint.Style.STROKE);
        RectF round = new RectF(-300,-50,300,50);
        canvas.drawRect(round,boundPaint);

        boundPaint.setStyle(Paint.Style.FILL);
        RectF progressRectf = new RectF(-300,-50,-300 - 50 + 7 * currentProgress,50);
        canvas.drawRect(progressRectf,boundPaint);
        postDelayed(updateRunnable,1000);
        Log.e(TAG,"ondraw currentprogress : " + (-300 + 6 * currentProgress));

    }
}
