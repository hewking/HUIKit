package com.hewking.hencoder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.hewking.custom.util.UiUtil

/**
 * Created by test on 2017/10/26.
 * 实现思路
 * 1 ， 首先画尺子一把 间隔 4dp  1cm 20dp  5 cm 为单位 变长 30 dp  最中间加粗 枢轴 50dp
 *
 */
class HenCoderRulerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mPaint: Paint? = null
    var mTextpaint: Paint? = null
    var mScroller : Scroller? = null

    init {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.color = Color.GREEN
        mPaint?.strokeWidth = 4f

        mTextpaint = Paint()
        mTextpaint?.isAntiAlias = true
        mTextpaint?.color = Color.BLACK
        mTextpaint?.textSize = UiUtil.dipToPx(context, 14).toFloat()
        mTextpaint?.strokeWidth = UiUtil.dipToPx(context, 4).toFloat()
//        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.PopupWindow)

        mScroller = Scroller(getContext())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        canvas.drawLine(0f, 0f, measuredWidth.toFloat(), 0f, mPaint)
        val count = measuredWidth / 30
        canvas.drawLine(measuredWidth / 2f, 0f, measuredWidth / 2f, 100f, mPaint)
        for (i in 0..count) {
            var height = 40f
            if (i % 10 == 0) {
                height = 80f
                canvas.drawText((i + scrollX / 30).toString(), i * count.toFloat() - 20, height + 40, mTextpaint)
            }
            canvas.drawLine(i * count.toFloat(), 0f, i * count.toFloat(), height, mPaint)

        }

    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(event)
    }

    var lastX  = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                var dx = event.x - lastX
                lastX = event.x
//                scrollBy(- dx.toInt() ,0)
                mScroller?.startScroll(event.x.toInt(),event.y.toInt(),- dx.toInt(),0)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {


            }

        }

        return true

    }


    override fun computeScroll() {
        super.computeScroll()
        if (mScroller?.computeScrollOffset()!!) {
            scrollTo(- mScroller?.currX!!,0)
            invalidate()
        }
    }


}