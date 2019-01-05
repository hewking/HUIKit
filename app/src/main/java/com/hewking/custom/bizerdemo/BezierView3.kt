package com.hewking.custom.bizerdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(defaultHeight,MeasureSpec.EXACTLY))
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.translate(width.div(2f), height.div(2f))
        val waveWidth = width.div(4f)
        val waveHeight = defaultHeight.div(2f)

        val path = Path()
        path.reset()
        path.moveTo(-width.div(2f),0f)
        path.quadTo(-1.5f.times(waveWidth),waveHeight,-waveWidth,0f)
        path.quadTo(-0.5f * waveWidth,-waveHeight,0f,0f)

        path.quadTo(0.5f * waveWidth,waveHeight,waveWidth,0f)
        path.quadTo(1.5f * waveWidth,-waveHeight,2 * waveWidth,0f)
        canvas.drawPath(path,paint)
    }

}