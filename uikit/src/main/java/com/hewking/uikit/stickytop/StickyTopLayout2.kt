package com.hewking.uikit.stickytop

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/4
 * 修改人员：hewking
 * 修改时间：2018/4/4
 * 修改备注：
 * Version: 1.0.0
 */
class StickyTopLayout2(ctx : Context,attrs : AttributeSet) : FrameLayout(ctx,attrs) {

    init {

    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)

    }

    /**
     * 判断是否允许滑动
     */
    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    /**
     * 刚好允许滑动时 回调
     */
    override fun onNestedScrollAccepted(child: View?, target: View?, axes: Int) {
        super.onNestedScrollAccepted(child, target, axes)
    }

    /**
     * 在内控件 滑动前 外控件滑动
     */
    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    /**
     * 在内控件 滑动后 外控件是否滑动
     */
    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)

    }


}