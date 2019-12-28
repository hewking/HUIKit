package com.hewking.custom.guide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hewking.custom.R
import com.hewking.custom.util.getColor

class HighLightChildFrameLayout : FrameLayout {

    constructor(ctx : Context) : this(ctx,null)

    constructor(ctx : Context,attrs : AttributeSet?) : super(ctx,attrs)

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = getColor(R.color.cc_white)
            style = Paint.Style.FILL
        }
    }

    private var lightRect : Rect? = null

    init {
        setWillNotDraw(false)
    }

    /**
     * 在 dispatchDraw draw子view之前执行，所以content 在
     * 子view 下方显示，如果需要在子view 上方显示则需要在dispatchDraw中draw
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun showHightLight(rect : Rect?){
        lightRect = rect
        invalidate()
    }

    fun showHightLight(left : Int,top : Int,right : Int,bottom : Int) {
        showHightLight(Rect(left,top,right,bottom))
    }

    /**
     *  /**
     * drawBackground
     * drawFade
     * drawContent
     * darwChild
     * drawDecoration
     * drawForground
    */
     */
    override fun dispatchDraw(canvas: Canvas?) {
        canvas?:return
        super.dispatchDraw(canvas)

        lightRect?.apply {
            //draw mask
            val sc = canvas.saveLayer(0f,0f,width.toFloat(),height.toFloat(),mPaint,Canvas.ALL_SAVE_FLAG)
            canvas.drawPaint(mPaint)
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            canvas.drawRect(this,mPaint)
            mPaint.xfermode = null
            canvas.restoreToCount(sc)
        }

    }
}