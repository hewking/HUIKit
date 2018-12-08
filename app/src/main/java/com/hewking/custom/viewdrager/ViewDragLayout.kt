package com.hewking.custom.viewdrager

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/8
 * 修改人员：hewking
 * 修改时间：2018/12/8
 * 修改备注：
 * Version: 1.0.0
 */

class ViewDragLayout(ctx : Context, attrs : AttributeSet) : FrameLayout(ctx,attrs) {

    private lateinit var mDragHelper : ViewDragHelper

    init {
        mDragHelper = ViewDragHelper.create(this,1.0f,object : ViewDragHelper.Callback(){
            override fun tryCaptureView(p0: View, p1: Int): Boolean {

                return true
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                val start = paddingLeft
                val end = width - paddingEnd - child.width
                val newLeft = Math.min(end,Math.max(left,start))

                return newLeft
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }
        })

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }

}