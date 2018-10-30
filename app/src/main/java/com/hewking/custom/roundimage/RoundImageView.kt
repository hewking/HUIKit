package com.hewking.custom.roundimage

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.hewking.custom.R
import com.hewking.custom.util.dp2px

class RoundImageView(ctx: Context, attrs: AttributeSet) : AppCompatImageView(ctx, attrs) {

    private var showBorder = true

    private var borderColor = Color.RED

    private var radius: Float = dp2px(4f).toFloat()

    private val path = Path()

    private val rect = Rect()

    private var showTag = false

    private var tagRes = R.mipmap.icon_admin

    private var tagDrawable = ContextCompat.getDrawable(ctx,tagRes)

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
        val typeArray = ctx.obtainStyledAttributes(attrs,R.styleable.RoundImageView)
        showTag = typeArray.getBoolean(R.styleable.RoundImageView_r_show_tag,showTag)
        tagRes = typeArray.getResourceId(R.styleable.RoundImageView_r_tag_res,tagRes)
        tagDrawable = ContextCompat.getDrawable(ctx,tagRes)
        typeArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        tagDrawable?.bounds = rect.apply {
            left = w - tagDrawable?.intrinsicWidth!!
            top = h - tagDrawable?.intrinsicHeight!!
            right = w
            bottom = h
        }
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

        if (showTag) {
            tagDrawable?.draw(canvas)
        }
    }

}