package com.hewking.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.resoloveSize
import com.hewking.custom.util.textHeight

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/21 0021
 * 修改人员：hewking
 * 修改时间：2018/11/21 0021
 * 修改备注：
 * Version: 1.0.0
 */
class TextLoadView(ctx: Context, attrs: AttributeSet)
    : LoadingView(ctx, attrs) {

    private var text: String? = "debug text"
        set(value) {
            field = text
            requestLayout()
            invalidate()
        }

    private val textPaint by lazy {
        Paint().apply {
            textSize = dp2px(14f).toFloat()
            color = Color.BLUE
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!TextUtils.isEmpty(text)) {
            val textWidth = textPaint.measureText(text)
            val textHeight = textPaint.textHeight()

            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            var width = MeasureSpec.getSize(widthMeasureSpec)

            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)

            if (widthMode != MeasureSpec.EXACTLY) {
                width = textWidth.toInt() + measuredWidth
            }

            if (heightMode != MeasureSpec.EXACTLY) {
                height = textHeight.toInt() + paddingBottom + paddingTop
            }
            setMeasuredDimension(width,height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        canvas.drawColor(Color.parseColor("#d6d6d6"))
        val textWidth = textPaint.measureText(text)
        canvas.drawText(text,0f,textPaint.textHeight(),textPaint)
        canvas.save()
        canvas.translate(textWidth,0f)
        super.onDraw(canvas)
        canvas.restore()
    }


}