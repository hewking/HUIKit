package com.hewking.custom

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/15
 * 修改人员：hewking
 * 修改时间：2018/12/15
 * 修改备注：
 * Version: 1.0.0
 */

class WaterRippleView(ctx : Context,attrs : AttributeSet) : View(ctx,attrs) {

    private var progress : Int = 20
    set(value) {
        field = value
        invalidate()
    }

    private var path : Path = Path()
    private var bizerPath = Path()

    private val mPaint by lazy{
        Paint().apply{
            color = ContextCompat.getColor(context,R.color.yellow_EADA50)
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    var radius = 0f

    // layout 之后调用
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = Math.min(w,h).div(2f)
        path.addCircle(w.div(2f),h.div(2f),radius,Path.Direction.CW)// 顺时针
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        canvas.save()
        canvas.clipPath(path)
        canvas.drawPaint(mPaint)
        canvas.save()
        canvas.translate(width.div(2f),height.div(2f))

        val lowheight = progress.div(100f).times(2).times(radius)
        val start = Math.sqrt(Math.pow(radius.toDouble(),2.toDouble()) + Math.pow(radius.toDouble() - lowheight,2.toDouble()))
        val end = - start

        mPaint.setColor(Color.RED)
        bizerPath.lineTo(start.toFloat(),radius-lowheight)
        bizerPath.quadTo(0f,0f,end.toFloat(),radius-lowheight)
        canvas.drawPath(bizerPath,mPaint)

        canvas.restore()
        canvas.restore()
    }

}