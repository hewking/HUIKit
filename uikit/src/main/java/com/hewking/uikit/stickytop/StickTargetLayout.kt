package com.hewking.uikit.stickytop

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/11
 * 修改人员：hewking
 * 修改时间：2018/5/11
 * 修改备注：
 * Version: 1.0.0
 */
class StickTargetLayout(ctx : Context, attrs : AttributeSet) : LinearLayout(ctx,attrs) {


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

}