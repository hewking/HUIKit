package com.hewking.hencoder.layout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by test on 2017/11/12.
 */
class StickImageView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {

    var progress : Int = 0
    set(value) {
        progress = value
        requestLayout()
    }
    get() {
        return field
    }


    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = measuredHeight + measuredHeight * progress
        var width = measuredWidth
        if (height >= width) {
            height = width
        } else {
            width = height
        }
        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}