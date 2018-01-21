package com.hewking.customviewtest.viewlifecircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by test on 2018/1/21.
 */
class LifeCircleView(context:Context,attrs : AttributeSet) : TextView(context,attrs) {

    final val TAG = LifeCircleView::class.java.simpleName

    /**
     * createion
     */
    init {
        Log.d(TAG,"constructor")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.d(TAG,"onFinishInflate")
    }

    /**
     * layout
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG,"onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG,"onLayout")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG,"onSizeChanged")
    }

    /**
     * drawing
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG,"onDraw")
    }

    /**
     * processing event
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        Log.d(TAG,"onTouchEvent")
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
        Log.d(TAG,"onkeyUP")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
        Log.d(TAG,"onKeyDown")
    }

    /**
     * focus changed
     */
    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        Log.d(TAG,"onFocusChanged")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        Log.d(TAG,"onWindowsFocusChanged")
    }

    /**
     * attach
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG,"onAttachToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG,"onDetachedFromWindow")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.d(TAG,"onWindowVisibilityChanged")
    }
}