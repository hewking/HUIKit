package com.hewking.uikit.stickytop

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.Scroller

/**
 * Created by test on 2017/12/26.
 * 恐龙谷主页点击置顶layout
 */
class StickyTopLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var expand = false

    private val mScroller : Scroller by lazy {
        Scroller(getContext())
    }
    public fun top(boo : Boolean){
        expand = boo
        requestLayout()
    }

    init {
        mScroller
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 2) {
            val childHeight = getChildAt(0).measuredHeight
            if (!expand) {
                getChildAt(0).layout(l, t, r, childHeight)
                getChildAt(1).layout(l, childHeight, r, b)
            } else {
                getChildAt(0).layout(l, - childHeight, r, t)
                getChildAt(1).layout(l, t, r, b)
            }
        } else {
            throw IllegalAccessException()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!expand) {
            return true
        }
//        return true
        return super.onInterceptTouchEvent(ev)
    }

    val point = Point()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                point.y = event.y.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val dy = event.y.toInt() - point.y
                point.y = event.y.toInt()

                if (scrollY <= 0 && dy > 0) {
                    return  false
                }

                if (scrollY >= getChildAt(0).measuredHeight && dy < 0) {
                    return false
                }

                scrollBy(0,-dy)
            }

            MotionEvent.ACTION_UP -> {
                val topHeight = getChildAt(0).measuredHeight
                if (scrollY > topHeight / 2) {
                    smoothTo(0,0)
                    top(false)
                }

                if (scrollY < topHeight / 2) {

                    smoothTo(0,0)
                    top(true)
                }
            }
        }
        return true
    }

    private fun smoothTo(disX:Int,disY:Int) {
        val dy = disY - scrollY
        val dx = disX - scrollX
        mScroller.startScroll(scrollX,scrollY,dx,dy)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX,mScroller.currY)
            postInvalidate()
        }
    }

}