package com.example.administrator.customviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/22.
 */
public class PathMeasure extends View {

    private int width;
    private int height;

    private Paint mPaint;
    private Bitmap mBitmap;
    private Matrix matrix;
    private float contentValue;

    float[] pos = new float[2];
    float[] tan = new float[2];

    public PathMeasure(Context context) {
        this(context,null);
    }

    public PathMeasure(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMeasure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.jiantou,options);
        matrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //平移坐标系
        canvas.translate(width / 2 ,height / 2);
        Path path = new Path();
        //往path中添加一个圆
        path.addCircle(0,0,200, Path.Direction.CW);
        //计算当前位置距离远点 比例
        contentValue += 0.01;
        if(contentValue > 1){
            contentValue = 0;
        }
        android.graphics.PathMeasure pathMeasure = new android.graphics.PathMeasure();
        pathMeasure.setPath(path,false);
        //计算当前位置坐标和正切值
        pathMeasure.getPosTan(pathMeasure.getLength() * contentValue,pos,tan);

        matrix.reset();
        //根据正切值算出箭头旋转角度
        float degress = (float) (Math.atan2(tan[1],tan[0]) * 180 / Math.PI);
        //矩阵旋转角度
        matrix.postRotate(degress,mBitmap.getWidth() / 2,mBitmap.getHeight() / 2);
        //矩阵平移 箭头到当前位置
        matrix.postTranslate(pos[0] - mBitmap.getWidth() / 2 ,pos[1] - mBitmap.getHeight() / 2);

        canvas.drawPath(path,mPaint);
        canvas.drawBitmap(mBitmap,matrix,mPaint);
        invalidate();
    }
}
