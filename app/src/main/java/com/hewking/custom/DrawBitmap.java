package com.hewking.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class DrawBitmap extends View {

    private int width;
    private int height;

    private Bitmap bitmap;
    private Paint paint;

    public DrawBitmap(Context context) {
        this(context,null);
    }

    public DrawBitmap(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawBitmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.nice_girl);
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap,new Matrix(),paint);
        canvas.translate(width/2,height / 2);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),new Rect(0,0,width / 2,height / 2),paint);

    }
}
