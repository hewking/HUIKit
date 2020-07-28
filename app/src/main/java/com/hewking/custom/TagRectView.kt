package com.hewking.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.hewking.utils.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/24 0024
 * 修改人员：hewking
 * 修改时间：2018/10/24 0024
 * 修改备注：
 * Version: 1.0.0
 */
class TagRectView(ctx : Context,attrs : AttributeSet) : View(ctx,attrs) {

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.RED
            strokeWidth = dp2px(1f).toFloat()
        }
    }

    init {
        val typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.CalendarView)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (wMode == MeasureSpec.AT_MOST) {
            width = dp2px(15f)
        }

        if (hMode == MeasureSpec.AT_MOST) {
            height = dp2px(30f)
        }

        setMeasuredDimension(width,height)

        if (isInEditMode) {
            setMeasuredDimension(100,200)
        }
    }

    private val path : Path by lazy {
        Path()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        val smallH = height / 3f
        val biggerH = height / 3f * 2
        path.moveTo(0f,smallH)
        path.rLineTo(0f,biggerH)
        path.rLineTo(width.toFloat(),-smallH)
        path.rLineTo(0f,-biggerH)
        path.close()
        canvas.drawPath(path,mPaint)


    }

}