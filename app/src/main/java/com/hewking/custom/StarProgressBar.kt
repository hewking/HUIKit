package com.hewking.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by test on 2017/11/22.
 */
class StarProgressBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val patin : Paint  by lazy {
        Paint().apply {
            color = Color.RED
            strokeWidth = 2.toFloat()
        }
    }

    init {


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

}