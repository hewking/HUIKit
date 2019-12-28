package com.hewking.custom.refreshlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/8
 * 修改人员：hewking
 * 修改时间：2018/4/8
 * 修改备注：
 * Version: 1.0.0
 */
class HRefreshLayout(ctx : Context,attrs : AttributeSet) : ViewGroup(ctx,attrs) {

    /**
     * 用于刷新的view
     */
    private var mRefreshView : View? = null
    /**
     * 内容view
     */
    private var mTargetView : View? = null

    private val mScroller : Scroller by lazy {
        Scroller(context)
    }

    init {
        mScroller
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        super.addView(child, index, params)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 需要判断下标 否则可能有越界的情况
        if (childCount >= 2) {
            mRefreshView = getChildAt(0)
            mTargetView = getChildAt(1)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        Log.d(HRefreshLayout::class.java.simpleName , "size : ${childCount}  onMeasure refreshheight : ${mRefreshView?.measuredHeight} height : ${mTargetView?.measuredHeight}")
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSice = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mTargetView?.measuredWidth?:0,mTargetView?.measuredHeight?:0)
        } else if(widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mTargetView?.measuredWidth?:0,heightSpecSize)
        } else if(heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSice,mTargetView?.measuredHeight?:0)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount >= 2) {
            mRefreshView?.layout(l,-(mRefreshView?.measuredHeight?:0),r,0)
            Log.d(HRefreshLayout::class.java.simpleName , "layout height : ${-(mRefreshView?.measuredHeight?:0)}")
            mTargetView?.layout(l,t,r,mTargetView?.measuredHeight?:0)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        super.onInterceptTouchEvent(ev)
        return true
    }

    private var mLastY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var consume = super.onTouchEvent(event)
        when(event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event?.y
                consume = true
            }

            MotionEvent.ACTION_MOVE -> {
                val dy = event?.y - mLastY

                // 向下滑 并且还未到 滑动最大距离
                if (scrollY >= -(mRefreshView?.height?:0) && dy > 0) {
                    scrollBy(0,-(dy.toInt()?:0))
                }

                // 滑倒顶部了 不能再滑了
                if (scrollY <= 0 && dy < 0) {
                    scrollBy(0,-(dy.toInt()?:0))
                }

                mLastY = event?.y
                consume = true
            }

            MotionEvent.ACTION_UP -> {
                val topHeight = mRefreshView?.height?:0
                Log.d(HRefreshLayout::class.java.simpleName , "scrollY : ${scrollY} topHeight : ${topHeight}  ${- topHeight / 2}")
                if (scrollY > - topHeight / 2) {
                    smoothTo(-topHeight)
                } else {
                    smoothTo(0)
                }
                consume = true
            }
        }
        return consume
    }

    fun smoothTo(destY : Int) {
        val dy = destY - scrollY
        mScroller.startScroll(0,scrollY,0,dy)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX,mScroller.currY)
            postInvalidate()
        }
    }


}