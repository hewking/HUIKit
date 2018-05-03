package com.hewking.customviewtest.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/27
 * 修改人员：hewking
 * 修改时间：2018/4/27
 * 修改备注：
 * Version: 1.0.0
 */

class PathView(ctx : Context,attrs : AttributeSet) : View(ctx,attrs) {

    init {

    }

    val mPaint : Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}