package com.hewking.customviewtest.util

import android.graphics.Paint
import android.view.View

/**
 * Created by test on 2018/1/21.
 */
fun <T : View> View.v(resid : Int) :T{
    return findViewById(resid) as T
}

fun View.dp2px(dp : Float) : Int{
    return (UiUtil.getScreenDensity(context) * dp + 0.5).toInt()
}

fun Paint.textHeight() : Float{
    return descent() - ascent()
}