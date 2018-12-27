package com.hewking.custom.loading

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.getColor

/**
 * 项目名称：FlowChat
 * 类的描述：抖音底部加载条
 * 创建人员：hewking
 * 创建时间：2018/12/27 0027
 * 修改人员：hewking
 * 修改时间：2018/12/27 0027
 * 修改备注：
 * Version: 1.0.0
 */
class DyLineLoadingView(ctx: Context, attr: AttributeSet) : View(ctx, attr) {

    private var animValue = 0f

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = getColor(R.color.yellow_ffe2b1)
        }
    }

    private val anim = ValueAnimator.ofFloat(0f, 1f).apply {
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        duration = 1000
        interpolator = BounceInterpolator()
        addUpdateListener {
            animValue = it.animatedValue as Float
            postInvalidateOnAnimation()
        }
        addListener(object : AnimatorListenerAdapter() {

        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        anim.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = dp2px(300f)
        }

        if (hMode != MeasureSpec.EXACTLY) {
            hSize = dp2px(2f)
        }
        setMeasuredDimension(wSize, hSize)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        canvas.save()
        val xMax = width.div(2f)
        val yMax = height.div(2f)
        canvas.translate(xMax, yMax)
        canvas.drawRect(-xMax.times(animValue), -yMax.div(2f), xMax.times(animValue), yMax.div(2f), paint)
        canvas.restore()
    }

}