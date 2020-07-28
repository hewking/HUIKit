package com.hewking.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R
import com.hewking.util.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/6
 * 修改人员：hewking
 * 修改时间：2018/4/6
 * 修改备注：
 * Version: 1.0.0
 */
class CircleView(ctx : Context, attrs : AttributeSet) : View(ctx,attrs) {

    private var mCircleColor : Int = 0
    private var mRadius = 0f

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    init {
        val style = ctx.obtainStyledAttributes(attrs, R.styleable.CircleView)
        mCircleColor = style.getColor(R.styleable.CircleView_color,Color.RED)
        mRadius = style.getDimension(R.styleable.CircleView_raduis,dp2px(20f).toFloat())
        style.recycle()

        mPaint.color = mCircleColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /**
         * 处理 wrap_content 否则  wrap_content 的大小 与 match_parent 一样
         * 父view 的可利用空间大小
         */
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        val defaultHeight = paddingLeft  * 2 + 2 * mRadius.toInt()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultHeight,defaultHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultHeight,heightSpecSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize,defaultHeight)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        // 处理 padding
        canvas?.translate(paddingLeft + mRadius,paddingLeft + mRadius)
        canvas?.drawCircle(0f,0f,mRadius,mPaint)
    }


}