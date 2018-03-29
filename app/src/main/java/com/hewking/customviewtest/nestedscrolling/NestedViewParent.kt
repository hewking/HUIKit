package com.hewking.customviewtest.nestedscrolling

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/3/27
 * 修改人员：hewking
 * 修改时间：2018/3/27
 * 修改备注：
 * Version: 1.0.0
 */
class NestedViewParent(ctx : Context,attrs : AttributeSet) : LinearLayout(ctx,attrs),NestedScrollingParent{

    private var mTopView : View? = null

    init {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount < 2) {
            throw IllegalArgumentException("请检查xml,有且只有两个View")
        }
        mTopView = getChildAt(0)
    }

    /**
     * 当nestedchild 想要滑动时 会调用nestedparent onStartnestedScroll 方法，
     * 是否支持嵌套滑动
     */
    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes.equals(ViewCompat.SCROLL_AXIS_VERTICAL)
    }

    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    /**
     * 当nestedchild 要真正要滑动时候会调用nestedparent 方法，判断nestedparent 是否消耗
     */
    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        if (dy < 0 && scrollY > 0 || dy > 0 && scrollY < mTopView?.measuredHeight?:0) {
            consumed!![1] = dy
            scrollBy(dx,dy)
        }
    }

}