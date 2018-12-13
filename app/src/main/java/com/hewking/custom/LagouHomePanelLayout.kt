package com.hewking.custom

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/6/25
 * 修改人员：hewking
 * 修改时间：2018/6/25
 * 修改备注：
 * Version: 1.0.0
 */
class LagouHomePanelLayout(ctx: Context, attrs: AttributeSet) : ViewGroup(ctx, attrs) {

    private val TAG = "LagouHomePanelLayout"

    private var mTopView: View? = null
    private var mContentView: View? = null

    private var mTopHeight = 0

    private var parallRate = 0.6f

    private var mCurOffset = 0f

    private val mScroller : OverScroller by lazy {
        OverScroller(ctx)
    }

    private fun ensureTargetView() {
        if (childCount > 1) {
            if (mTopView == null) {
                mTopView = getChildAt(0)
            }
            if (mContentView == null) {
                mContentView = getChildAt(1)
            }
        } else {
            throw IllegalArgumentException("must have two child view!!")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        ensureTargetView()
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        mTopHeight = mTopView?.measuredHeight?:0
        val height = (mTopView?.measuredHeight ?: 0) + (mContentView?.measuredHeight!! ?: 0)
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        ensureTargetView()
        mTopView?.layout(l,t,r,t + mTopHeight)
        mContentView?.layout(l,t + mTopHeight,r,mTopHeight + mContentView?.measuredHeight!!)
    }

    private val mGestenerDetector : GestureDetectorCompat = GestureDetectorCompat(context,object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            Log.i(TAG,"e1 y : ${e1?.y}  e2 y : ${e2?.y}  distanceY : ${distanceY}")
            refreshLayout(distanceY)
            postInvalidate()
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            mScroller.fling(0,mCurOffset.toInt(),0,velocityY.toInt() / 2,0,0,-mTopHeight,0)
            postInvalidate()
            return true
        }
    })

    private fun refreshLayout(distanceY: Float) {
        val offset = mCurOffset - distanceY
        if (offset != mCurOffset) {
            mCurOffset = offset
            val l = mTopView?.left?:0
            val t = mTopView?.top?:0
            val r = mTopView?.right?:0

            // mCurOffset 上限 -mTopHeight  下限 0
            val offsetLimit = Math.min(0,Math.max(mCurOffset?.toInt(),-mTopHeight))
            val topLimit = mTopHeight + offsetLimit?.toInt()?:0

            val topT = (parallRate * offsetLimit).toInt()

            mTopView?.layout(l,topT,r,topT + mTopHeight)

            mContentView?.layout(l,topLimit,r,topLimit + mTopHeight + mContentView?.measuredHeight!!)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 需要拦截的时候返回 true
        ev?:return false
        if (mCurOffset <= - mTopHeight && ev.actionMasked != MotionEvent.ACTION_DOWN) {
//            val downEv = MotionEvent.obtain(ev)
//            downEv.action = MotionEvent.ACTION_DOWN
//            mContentView?.onTouchEvent(downEv)
            return false
        }
        return mGestenerDetector.onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestenerDetector.onTouchEvent(event)
        return true
    }

    /**
     * 动态展开
     */
    fun expand(){
        mScroller.startScroll(0,0,0,mTopHeight)
        invalidate()
    }

    /**
     * 折叠起来
     */
    fun collapsing(){
        mScroller.startScroll(0,mCurOffset?.toInt(),0,-mTopHeight)
        invalidate()
    }

    private var lastScrollY = -1f

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            var dy = mScroller.currY?.toFloat() - lastScrollY
            if (lastScrollY == -1f) {
                dy = 0f
            }
            lastScrollY = mScroller.currY?.toFloat()
            Log.i(TAG,"computeScroll currY : ${mScroller.currY?.toFloat()}  dy : $dy")
            refreshLayout(-dy)
            postInvalidate()
        }
    }

}