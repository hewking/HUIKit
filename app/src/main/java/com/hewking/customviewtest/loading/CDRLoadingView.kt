package com.hewking.customviewtest.loading

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hewking.customviewtest.util.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/2/5
 * 修改人员：hewking
 * 修改时间：2018/2/5
 * 修改备注：
 * Version: 1.0.0
 */
class CDRLoadingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mPaint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = dp2px(2f).toFloat()
            isAntiAlias = true
            color = Color.RED
        }
    }

    private var mHeight = 0
    private var mWidth = 0

    init {
        mPaint
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimator()
    }

    private var fraction: Float = 0f

    private fun startAnimator() {
        // 开启嘴张开动画
        ValueAnimator.ofFloat(0f, 1f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            duration = 500
            addUpdateListener {
                fraction = it.animatedValue as Float
                postInvalidateOnAnimation()
            }

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }
            })

            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val maxWidth = resolveSize((radius * 7).toInt(), widthMeasureSpec)
        val minHeight = resolveSize(2 * radius.toInt(), heightMeasureSpec)
        setMeasuredDimension(maxWidth, minHeight)

    }

    private var circleBitmap: Bitmap? = null
    private var circleCanvas: Canvas? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        circleBitmap?.recycle()
        if (mWidth > 0 && mHeight > 0) {
            circleBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
            circleCanvas = Canvas(circleBitmap)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        circleBitmap?.recycle()
    }

    private val radius = dp2px(20f).toFloat()

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawARGB(255, 63, 52, 43)
        circleCanvas?.let {
            it.save()
            mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
            it.drawPaint(mPaint)
            mPaint.setXfermode(null)
            it.translate(0f, mHeight / 2f)
            mPaint.color = Color.RED
            it.drawCircle(radius, 0f, radius, mPaint)

            // 画扇形
            mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))
            mPaint.color = Color.TRANSPARENT
            val rectF = RectF(0f, -radius, 2 * radius, radius)
            val sweep = 90f * fraction
            it.drawArc(rectF, -sweep / 2, sweep, true, mPaint)
            it.restore()
            canvas?.drawBitmap(circleBitmap, 0f, 0f, null)

        }
    }


}