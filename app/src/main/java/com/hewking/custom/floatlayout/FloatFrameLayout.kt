package com.hewking.custom.floatlayout

import android.content.Context
import androidx.core.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.hewking.custom.R

/**
 * Created by test on 2017/12/27.
 * 用于添加一个悬浮view 并且控制触摸事件
 */
class FloatFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.FloatFrameLayout)
        val gravity = typeArray.getString(R.styleable.FloatFrameLayout_gravity)
        typeArray.recycle()
    }

    var touchview: View? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount > 0) {
            touchview = getChildAt(0)
            touchview?.layout(left,measuredHeight / 2,left + touchview?.measuredWidth!!,measuredHeight / 2 + touchview?.measuredHeight!!)
            touchview?.setOnTouchListener(object : OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    return gestureDecter.onTouchEvent(event)
                }
            })
        }
    }

    private val gestureDecter = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (touchview != null) {
                val params = touchview?.layoutParams as FrameLayout.LayoutParams
                params?.leftMargin = params?.leftMargin + distanceX.toInt()
                params?.topMargin = params?.topMargin + distanceY.toInt()
                Log.d(FloatFrameLayout::class.java.simpleName,"OnScroll x:${touchview?.x} y ${touchview?.y}")
                return true
            } else {
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        }
    })

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return gestureDecter.onTouchEvent(event)
//    }

}
