package com.hewking.custom.bizerdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.BuildConfig
import com.hewking.custom.R
import com.hewking.custom.util.DrawHelper
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.getColor

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/6
 * 修改人员：hewking
 * 修改时间：2019/1/6
 * 修改备注：
 * Version: 1.0.0
 */
class BezierView3(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private val defaultHeight = dp2px(60f)

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = getColor(R.color.app_color_theme_6)
            strokeWidth = dp2px(1f).toFloat()
        }
    }

    init {

    }

    private var offset = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                offset = it.animatedValue as Float
                postInvalidateOnAnimation()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)
                }
            })
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.EXACTLY))
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        if (BuildConfig.DEBUG) {
            DrawHelper.drawCoordinate(canvas, width, height)
        }

        canvas.translate(width.div(2f), height.div(2f))
        val waveWidth = width.div(4f)
        val waveHeight = defaultHeight.div(2f)

        val path = Path()
        path.reset()

        val start = -4 * waveWidth + 2 * offset * waveHeight
        path.moveTo(start, 0f)

        // 绘制2段完整波形
        for (i in 0 until 4) {
            path.quadTo(start + (4 * i + 1) * 0.5f * waveWidth, waveHeight
                    , start + (2 * i + 1) * waveWidth, 0f)

            path.quadTo(start + (4 * i + 3) * 0.5f * waveWidth, -waveHeight
                    , start + (i + 1) * 2 * waveWidth, 0f)
        }

        canvas.drawPath(path, paint)
    }

}