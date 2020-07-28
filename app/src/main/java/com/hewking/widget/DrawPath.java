package com.hewking.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class DrawPath extends View {

    private int width;
    private int height;

    private Paint mPaint;

    public DrawPath(Context context) {
        this(context,null);
    }

    public DrawPath(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawPath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
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

        canvas.translate(width / 2 , height / 2);
        Path path = new Path();
        //连接到 某个点
        path.lineTo(200,200);
        path.lineTo(200,0);

        //改变上一次操作最后一个点位置
        path.setLastPoint(300,0);

        //移动到某个位置
        path.moveTo(150,150);

        path.addRect(new RectF(-200,200,200,200),Path.Direction.CW);

        canvas.scale(1,-1);//反转y轴坐标 跟正常正交坐标系一样了 哈哈

        Path circle = new Path();
        circle.addCircle(0,200,100, Path.Direction.CW);
        path.addPath(circle);

        //连接成闭合曲线
        path.close();
        canvas.drawPath(path,mPaint);
    }
}
