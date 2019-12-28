package com.hewking.custom.loading

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R
import com.hewking.custom.util.L
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.getColor

/**
 * 仿麦子钱包loadingView
 */
class MathWalletLoadingView(ctx : Context, attrs : AttributeSet)
    : View(ctx,attrs) {

    private val TAG = "MathWalletLoadingView"

    private var radius : Float = dp2px(2f).toFloat()

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = getColor(R.color.app_color_blue_2)
        }
    }

    init {

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // start animation
        startFlowAnim()
        startRotateAnim()
    }

    private var flowValue = 0f
    private var biggerValue = 0f
    private var degress = 0f
    private var count = 0

    /**
     * 开启
     */
    private fun startFlowAnim() {
        ValueAnimator.ofFloat(0f,1f)
                .apply {
                    duration = 500
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.RESTART
                    addUpdateListener {
                        flowValue = it.animatedValue as Float
                        postInvalidateOnAnimation()
                    }
                    addListener(object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                            super.onAnimationRepeat(animation)
                            // 结束动画之后变大
                            count++
                            startBiggerAnim(0f + count,1f + count)
                        }
                    })
                    start()
                }
    }

    /**
     * 旋转
     */
    private fun startRotateAnim() {
        ValueAnimator.ofFloat(0f,1f)
                .apply {
                    duration = 2000
                    repeatMode = ValueAnimator.RESTART
                    repeatCount = ValueAnimator.INFINITE
                    addUpdateListener {
                        degress = it.animatedValue as Float * 360
                        postInvalidateOnAnimation()
                    }
                    addListener(object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                            super.onAnimationRepeat(animation)
                            biggerValue = 0f
                            count = 0
                        }
                    })
                    start()
                }
    }

    private fun startBiggerAnim(start : Float,end : Float) {
        ValueAnimator.ofFloat(start,end)
                .apply {
                    duration = 500
                    addUpdateListener {
                        biggerValue = it.animatedValue as Float
                        L.d(TAG,"bigerValue : $biggerValue")
                        postInvalidateOnAnimation()
                    }
                    start()
                }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = 16 * radius.toInt()
        }

        if (hMode != MeasureSpec.EXACTLY) {
            hSize = 16 * radius.toInt()
        }
        setMeasuredDimension(wSize,hSize)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        canvas.save()
        // rotate
        canvas.rotate(degress,width.div(2f),height.div(2f))
        canvas.save()
        canvas.translate(width.div(2f),height.div(2f))
        canvas.drawCircle(0f,0f,radius * (1 + biggerValue),paint)
        canvas.drawCircle(-7 * radius * (1 - flowValue),0f,radius,paint)
        canvas.restore()
        canvas.restore()
    }

}