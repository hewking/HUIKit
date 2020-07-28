package com.hewking.demo.dispatch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.FrameLayout
import com.hewking.util.L

class DispatchLayout : FrameLayout {

    private val TAG : String = "DispatchLayout"

    constructor(ctx : Context) : this(ctx,null){

    }

    constructor(ctx : Context ,attrs : AttributeSet?) : super(ctx,null)

    init {
        setWillNotDraw(false)
        addView(DispatchView(context),FrameLayout.LayoutParams(-2,-2).apply {
            gravity = Gravity.CENTER
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        L.d("dispatchTouchEvent",TAG)
        val consume = super.dispatchTouchEvent(ev)
        L.d("dispatchTouchEvent ev : ${ev?.actionMasked} consume : $consume",TAG)
        return consume
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val consume = super.onInterceptTouchEvent(ev)
        L.d("onInterceptTouchEvent ev : ${ev?.actionMasked} consume : $consume",TAG)
        return consume
    }

    private var point : PointF? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        L.d("onTouchEvent",TAG)
        point = PointF(event?.x?:0f,event?.y?:0f)
        invalidate()
        return super.onTouchEvent(event)
    }

    private val mPaint : Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.parseColor("#ff984323")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            canvas.save()
            translate(width /2f ,height/2f)
            drawCircle(point?.x?.minus(width/2f)?:0f,point?.y?.minus(height / 5f)?:0f,width/5f,mPaint)
            canvas.restore()
        }
    }


}