package com.hewking.uikit.gallerylayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import com.hewking.utils.L

/** 可以设置上下左右翻页的view
 * Created by test on 2018/1/14.
 */
class GalleryLayout2(context : Context, attributeSet: AttributeSet?
) : ViewGroup(context,attributeSet) {

    /**
     * 翻页持续时间
     */
    var duration:Long = 2000

    /**
     * 翻页间隔时间
     */
    var interval : Long = 1000

    /**
     * 偏移比例
     */
    var offset  = 0f

    /**
     * scroller
     */
    val mScroller : Scroller by lazy {
        Scroller(getContext())
    }

    init {
        mScroller
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val childLeft = measuredWidth * i
            val childTop = t
            val childRight = measuredWidth  + childLeft
            val childBottom = b
            getChildAt(i).layout(childLeft,childTop,childRight,childBottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)
    }

    val pointTouch = Point()
    var interceptAnimoator = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                pointTouch.x = event.x.toInt()
                interceptAnimoator = true
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = event.x.toInt() - pointTouch.x
                pointTouch.x = event.x.toInt()

                scrollBy(-dx,0)
            }

            MotionEvent.ACTION_UP -> {
                interceptAnimoator = false
            }
        }

        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    val point = PointF()

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 如果上下左右滑动距离大于10 拦截
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                point.x = ev.x
                point.y = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - point.x
                val dy = ev.y - point.y
                point.x = ev.x
                point.y = ev.y

                if (dx > 30 || dy > 30) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private val animator : ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
            .setDuration(duration)
            .apply {
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        val value = animation?.animatedValue as Float
                        if (!interceptAnimoator) {
                            L.d("GalleryLayout2","offset : ${value + offset} value : ${(value * measuredWidth).toInt()}" )
                            scrollTo(((offset + value) * measuredWidth).toInt(),0)
                        }
                    }
                })
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {

                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                        super.onAnimationRepeat(animation)
                        offset += 1
                        //无限循环核心在于首尾 的数据必须一样，这样在通过offset = 0,scrollTo到最开始位置才不会有问题。
                        if (offset >= childCount - 1) {
                            offset = 0f
//                            smoothTo(0,0)
                        }
                    }
                })
            }

//    private fun smoothTo(disX : Int,disY : Int) {
//        val dx = disX - scrollX
//        mScroller.startScroll(scrollX,scrollY,dx,0)
//        invalidate()
//    }
//
//    override fun computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.currX,mScroller.currY)
//            postInvalidate()
//        }
//    }

}