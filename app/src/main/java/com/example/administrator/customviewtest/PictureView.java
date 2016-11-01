package com.example.administrator.customviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class PictureView extends View{

    private Picture mPicture;

    public PictureView(Context context) {
        this(context,null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //step1 ：创建 picture
        mPicture = new Picture();
        recording();
    }

    private void recording() {
        //step2 : 开始录制
        Canvas canvas = mPicture.beginRecording(500,500);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        canvas.translate(250,250);
        canvas.drawCircle(0,0,100,paint);

        //step3 : 结束录制
        mPicture.endRecording();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));

    }
}

