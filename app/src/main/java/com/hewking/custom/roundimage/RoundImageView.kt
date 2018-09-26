package com.hewking.custom.roundimage

import android.content.Context
import android.graphics.*
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.hewking.custom.util.dp2px

class RoundImageView(ctx: Context, attrs: AttributeSet) : AppCompatImageView(ctx, attrs) {

    private var showBorder = true

    private var borderColor = Color.RED

    private var radius: Float = dp2px(4f).toFloat()

    private val path = Path()

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = borderColor
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            isFilterBitmap = true
            strokeWidth = radius / 4
        }
    }

    init {

    }

    override fun onDraw(canvas: Canvas) {
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
        if (showBorder) {
            rect.inset(1f, 1f)
            canvas?.drawRoundRect(rect, radius, radius, mPaint)
        }
    }

}