package com.hewking.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.hewking.custom.util.DrawHelper
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.getColor
import com.hewking.custom.util.textHeight

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

    // 进度 [0,100]
    private var progress : Int = 50
    set(value) {
        field = value
        invalidate()
    }
    // 绘制半径
    var radius = 0f

    // 波浪动画 offset
    var offset = 0f

    var textColor = getColor(R.color.pink_f5b8c2)

    var textSize = dp2px(14f)

    private var path : Path = Path()
    private var bizerPath = Path()

    private val mPaint by lazy{
        Paint().apply{
            color = ContextCompat.getColor(context,R.color.yellow_EADA50)
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }
    }

    private val textPaint by lazy{
        Paint().apply{
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = textColor
            textSize = this@WaterRippleView.textSize.toFloat()
        }
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = dp2px(200f)
        }

        if (hMode != MeasureSpec.EXACTLY) {
            hSize = dp2px(200f)
        }

        val size = Math.min(hSize,wSize)
        setMeasuredDimension(size,size)
    }

    // layout 之后调用
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = Math.min(w,h).div(2f)
        path.addCircle(w.div(2f),h.div(2f),radius,Path.Direction.CW)// 顺时针

        ValueAnimator.ofFloat(0f,1f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                offset = it.animatedValue as Float
                postInvalidateOnAnimation()
            }
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)
                }
            })
            start()
        }

        if (BuildConfig.DEBUG) {
            ValueAnimator.ofFloat(0f,1f).apply{
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                duration = 8000
                addUpdateListener {
                    progress = (it.animatedValue as Float).times(100).toInt()
                    postInvalidateOnAnimation()
                }
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationRepeat(animation: Animator?) {
                        super.onAnimationRepeat(animation)
                    }
                })
                start()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?:return

        canvas.save()
        canvas.clipPath(path)
        canvas.save()
        canvas.translate(width.div(2f),height.div(2f))

        val lowheight = progress.div(100f).times(2).times(radius)
//        val start = Math.sqrt(Math.pow(radius.toDouble(),2.toDouble()) + Math.pow(radius.toDouble() - lowheight,2.toDouble()))
//        val end = - start

//        val sc = canvas.saveLayer(-radius,-radius,radius,radius,mPaint,Canvas.ALL_SAVE_FLAG)
//        canvas.drawPaint(mPaint)
//        mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC))
        bizerPath.reset()
        val waveHeight = radius.div(8)
        val waveWidth = radius.div(2)
        bizerPath.moveTo(- 2 * waveWidth,radius - lowheight)
        val start = - 4 * waveWidth + offset * 2 * waveWidth
        for(i in 0 until 4) {
            bizerPath.quadTo(start + (4 * i + 1) * 0.5f * waveWidth
                    ,radius - lowheight - waveHeight
                    ,start + (2 * i + 1) * waveWidth
                    ,radius - lowheight)
            bizerPath.quadTo(start + (4 * i + 3) * 0.5f * waveWidth
                    ,radius - lowheight + waveHeight
                    ,start + (i + 1) * 2 * waveWidth
                    ,radius - lowheight)
        }
        bizerPath.lineTo(radius,radius)
        bizerPath.lineTo(-radius,radius)
        bizerPath.close()
        canvas.drawPath(bizerPath,mPaint)
//        mPaint.xfermode = null
//        canvas.restoreToCount(sc)

        // draw progress
        val text = "$progress %"
        canvas.drawText(text
                ,-textPaint.measureText(text).div(2)
                ,textPaint.textHeight().div(2) - textPaint.descent()
                ,textPaint)

        canvas.restore()
        canvas.restore()

        if (BuildConfig.DEBUG) {
            DrawHelper.drawCoordinate(canvas,width,height)
        }
    }

}