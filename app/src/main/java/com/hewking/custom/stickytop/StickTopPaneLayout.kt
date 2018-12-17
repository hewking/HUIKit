package com.hewking.custom.stickytop

import android.content.Context
import androidx.core.view.MotionEventCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import com.hewking.custom.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/6
 * 修改人员：hewking
 * 修改时间：2018/5/6
 * 修改备注：
 * Version: 1.0.0
 *
 * 需求
 * 1.两部分 header target
 * 2. header 和 target 是连续的 类似于 LinearLayout vertical
 * 3. 当header 滑动到隐藏时 ，此时事件分发需要考虑到target 是否支持滑动
 * 4.如果支持 则把滑动事件交给 target ，滑动包括了  向上滑或者向下滑 如果是向下滑动 并且不能滑动了
 * 事件不交给target 自己处理 把header 滑下来
 *
 */
class StickTopPaneLayout(ctx : Context,attrs : AttributeSet) : ViewGroup(ctx,attrs) {

    /**
     * 头部view
     */
    private var mHeadView : View? = null

    /**
     * 用于处理滑动事件分发的v子iew
     */
    private var mTargetView : View? = null

    /**
     * scroller 处理滑动
     */
    private val mScroller : Scroller by lazy{
        Scroller(context)
    }

    /**
     * 处理获取速度
     */
    private val mVelocityTracker : VelocityTracker by lazy {
        VelocityTracker.obtain()
    }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs,R.styleable.StickTopPaneLayout)
        typeArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 给view 赋值
        if (mHeadView == null) {

        }

        if (mTargetView == null) {

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 测量过 现在已经有测量过的值了
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        ensureHeaderAndTargetView()
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        var maxWidth = 0
        for (i in 0..childCount - 1) {
            if (getChildAt(i).measuredWidth > maxWidth) {
                maxWidth = getChildAt(i).measuredWidth
            }
        }
        setMeasuredDimension(maxWidth,(mHeadView?.measuredHeight?:0) + (mTargetView?.measuredHeight!! ?:0))
    }

    private fun ensureHeaderAndTargetView() {
        if (mHeadView != null && mTargetView != null) {
            return
        }
        if (mHeadView == null && mTargetView == null && childCount > 1) {
            // 这里还需要保证headview 和 targetview 的顺序一定是 0 是header 1 是 targetview
            mHeadView = getChildAt(0)
            mTargetView = getChildAt(1)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        ensureHeaderAndTargetView()
        mHeadView?.let {
            it.layout(l,t,r,it.measuredHeight)
        }
        mTargetView?.let {
            it.layout(l,mHeadView?.measuredHeight?:t,r,it.measuredHeight + mHeadView?.measuredHeight!!)
        }
    }

    var lastY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when(ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {

            }

            MotionEvent.ACTION_UP -> {

            }

        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(ev)
        val offsetEnd = mHeadView!!.height
        var intercept = false
        when(action) {
            MotionEvent.ACTION_DOWN -> {
                // header 没有滑动到顶部 继续拦截
                lastY = ev.y
                intercept = false
            }

            MotionEvent.ACTION_MOVE -> {
                // 内部拦截法 如果需要拦截就拦截
                if (scrollY < offsetEnd || scrollY < 0) {
                    intercept = true
                }
            }

            MotionEvent.ACTION_UP
            , MotionEvent.ACTION_CANCEL-> {
                return false
            }
        }

        return intercept
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val offsetEnd = mHeadView!!.height
        when(ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastY = ev.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = ev.y - lastY
                lastY = ev.y

                // 滑动到header 的高度停止滑动
                // 并且重新分发down事件给子view
                if (scrollY - dy > offsetEnd) {
//                    val oldAction = ev.action
//                    ev.action = MotionEvent.ACTION_DOWN
//                    dispatchTouchEvent(ev)
//                    ev.action = oldAction
                    return false
                }

                if (scrollY <= 0 && dy > 0) {
                    return false
                }

                scrollBy(0,-dy.toInt())
                return true
            }
        }
        return super.onTouchEvent(ev)
    }

    fun smoothTo(disX : Int , disY : Int){
        val dx = disX - scrollX
        val dy = disY - scrollY
        mScroller.startScroll(scrollX,dx,scrollY,dy)
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0,mScroller.currX)
            invalidate()
        }
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    fun acquireVelocity(){

    }

}