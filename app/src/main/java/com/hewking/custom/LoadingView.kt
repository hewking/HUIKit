package com.hewking.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.hewking.utils.L
import com.hewking.utils.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/12 0012
 * 修改人员：hewking
 * 修改时间：2018/11/12 0012
 * 修改备注：
 * Version: 1.0.0
 */
open class LoadingView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private val TAG = "LoadingView"

    var color = Color.BLACK

    var size = dp2px(10f)

    var stroke = dp2px(10f)

    var count = 3

    // 一秒三次
    var interval = 1
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    init {
        val typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        color = typedArray.getColor(R.styleable.LoadingView_h_color,color)
        typedArray.recycle()

    }

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        interpolator = BounceInterpolator()
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                // 1-3
                val value = makeValue(animation?.animatedValue as Float)
                L.d("LoadingView", "value -> ${animation?.animatedValue} value : $value")

                if (value != interval) {
                    interval = value
                }
            }

        })
    }

    private fun makeValue(value : Float) : Int {
        val a = 1f.div(3)
        return (value.div(a) + 1).toInt()
    }

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        L.d(TAG,"onMeasure")
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)

        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (wMode == MeasureSpec.AT_MOST) {
            width = paddingStart + paddingEnd + size * count + 2 * stroke
        }

        if (hMode == MeasureSpec.AT_MOST) {
            height = paddingTop + paddingBottom + size
        }

        setMeasuredDimension(width, height)

    }

    override fun onDraw(canvas: Canvas?) {
        L.d(TAG,"onDraw")
        if (canvas == null) return
        var radius = size / 2
        var cy = height / 2
        for (i in 1 until interval + 1) {
            var cx = paddingStart + radius + (size + stroke) * (i - 1)
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mPaint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // start animator
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // end animator
        animator.end()
    }

}