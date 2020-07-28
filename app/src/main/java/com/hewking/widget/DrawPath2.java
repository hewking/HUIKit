package com.hewking.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hewking on 2016/10/21.
 */
public class DrawPath2 extends View {

    private Paint mPaint;
    private int width;
    private int height;

    public DrawPath2(Context context) {
        this(context,null);
    }

    public DrawPath2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawPath2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
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
//        canvas.scale(1,-1);//反转y轴
        mPaint.setColor(Color.BLUE);
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);//奇偶规则
        path.toggleInverseFillType();//切换为反 xx规则
        path.addRect(-200,-200,200,200,Path.Direction.CW);
        canvas.drawPath(path,mPaint);
    }
}
