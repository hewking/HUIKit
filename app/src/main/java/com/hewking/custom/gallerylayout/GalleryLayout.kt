package com.hewking.custom.gallerylayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.LinearInterpolator

/** 可以设置上下左右翻页的view requestlayout()实现
 * Created by test on 2018/1/14.
 */
class GalleryLayout(context : Context, attributeSet: AttributeSet?) : ViewGroup(context,attributeSet) {

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

    init {


    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0..count - 1) {
            val childLeft = measuredWidth * i - (offset * measuredWidth).toInt()
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
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

    private val animator : ValueAnimator = ValueAnimator.ofFloat(0f,1f)
            .setDuration(duration)
            .apply {
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                addUpdateListener(object : ValueAnimator.AnimatorUpdateListener{
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        offset = animation?.animatedValue as Float

                        requestLayout()
                    }
                })
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        offset += 1
                        if (offset >= childCount) {
                            offset = 0f
                        }
                    }
                })
            }

    override fun computeScroll() {

    }

}
