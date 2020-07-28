package com.hewking.widget.hencoder.layout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.OverScroller

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/6/24
 * 修改人员：hewking
 * 修改时间：2018/6/24
 * 修改备注：
 * Version: 1.0.0
 */
class HenStickTopLayout(ctx : Context,attrs : AttributeSet) : ViewGroup(ctx,attrs) {

    private var mTopHeight = 0

    private val mScroller : OverScroller by lazy {
        OverScroller(ctx)
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    fun smoothScrollTo(desY : Int){
        mScroller.startScroll(scrollX,scrollY,0,desY - scrollY)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX,mScroller.currX)
            invalidate()
        }
    }

}