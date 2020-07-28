package com.hewking.demo.dispatch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.hewking.util.L

class DispatchView(ctx : Context) : View(ctx) {

    private val TAG = "DispatchView"

    private var mPaint : Paint? = null

    init {
        mPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            color = Color.parseColor("#ff887766")
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        L.d("dispatchTouchEvent",TAG)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val consume = super.onTouchEvent(event)
        L.d("onTouchEvent consume : $consume",TAG)
        return consume
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        radius = Math.min(measuredHeight,measuredWidth) / 4f
    }

    private var radius : Float = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            translate(width / 2f,height / 2f)
            drawCircle(0f,0f,radius,mPaint)
        }
    }

}