package com.hewking.custom.viewdrager

import android.content.Context
import android.graphics.Point
import androidx.customview.widget.ViewDragHelper
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

    private lateinit var mDragHelper : androidx.customview.widget.ViewDragHelper
    private var mDragView : View? = null
    private var mAutoBackView : View? = null
    private var mEdgeTouchView : View? = null

    private val mPoint = Point()

    init {
        mDragHelper = androidx.customview.widget.ViewDragHelper.create(this,1.0f,object : androidx.customview.widget.ViewDragHelper.Callback(){
            override fun tryCaptureView(p0: View, p1: Int): Boolean {

                return p0 == mDragView || p0 == mAutoBackView
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

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                if (releasedChild == mAutoBackView) {
                    mDragHelper.settleCapturedViewAt(mPoint.x,mPoint.y)
                    invalidate()
                }
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                mEdgeTouchView?:return
                mDragHelper.captureChildView(mEdgeTouchView!!,pointerId)
            }

            /**
             * 一下两个方法，是clickable = true,子view消耗事件的情况
             * 如果这时候，需要一下判断范围是否capture
             */
            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }
        })
        mDragHelper.setEdgeTrackingEnabled(androidx.customview.widget.ViewDragHelper.EDGE_LEFT.or(androidx.customview.widget.ViewDragHelper.EDGE_RIGHT))

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mAutoBackView?.let {
            mPoint.x = it.left
            mPoint.y = it.top
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ensureChildView()
        if(childCount < 3) {
            return
        }
        mDragView= getChildAt(0)
        mAutoBackView = getChildAt(1)
        mEdgeTouchView = getChildAt(2)
    }

    private fun ensureChildView() {

    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)){
            postInvalidate()
        }
    }


}