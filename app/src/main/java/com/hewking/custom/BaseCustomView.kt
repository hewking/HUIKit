package com.hewking.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Created by test on 2018/1/3.
 */
open class BaseCustomView(context : Context , attrs : AttributeSet): View(context,attrs) {



    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)



    }

    fun resolveSize2(size : Int, spec : Int) : Int{
        val specsize = MeasureSpec.getSize(spec)
        val mode = MeasureSpec.getMode(spec)
        var result = size
        when(mode) {
            MeasureSpec.AT_MOST -> {
                if (specsize < size) {
                    result = specsize
                }
            }
            MeasureSpec.EXACTLY -> {
                result = specsize
            }

            MeasureSpec.UNSPECIFIED -> {

            }
        }
        return result
    }



}