package com.hewking.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/5/25.
 */
public class StorageView extends View {

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private Paint mProgressPaint;
    private Paint mBackgroundPaint;
    private Paint mSmartRectPaint;

    private float precent;

    public StorageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    public StorageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StorageView(Context context) {
        this(context,null);
    }

    private void init() {
        mProgressPaint = new Paint();
        mProgressPaint.setColor(Color.BLUE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(2);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.parseColor("#aaffaa"));
        mBackgroundPaint.setAntiAlias(true);

        mSmartRectPaint = new Paint();
        mSmartRectPaint.setColor(Color.GRAY);
        mSmartRectPaint.setAntiAlias(true);


        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                precent  = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST){
            mWidth = widthSize;
        }

        if(heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST){
            mHeight = heightSize;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);o

        int smallRectLength = mWidth / 3 ;

        for(int i = 0 ; i < 3 ; i++){
            RectF rectF = new RectF(i * smallRectLength, 0, i * smallRectLength + 48, 40);
            canvas.drawRect(rectF,mSmartRectPaint);
        }


        RectF rectF = new RectF(0, 48, mWidth, 96);
        canvas.drawRect(rectF,mBackgroundPaint);
        RectF rectProgress = new RectF(0, 48,precent * (mWidth - 100), 96);
        canvas.drawRect(rectProgress,mProgressPaint);

    }
}
