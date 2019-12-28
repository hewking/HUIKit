package com.hewking.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by test on 2018/1/18.
 */
class EclipseAnimView : View{

    constructor(context : Context, attrs : AttributeSet?):super(context,attrs) {

    }

    constructor(context: Context) : this(context,null) {

    }

    private val mPaint : Paint by lazy {
        Paint().apply {
            color = Color.YELLOW
            strokeWidth = 2f
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private var circleCanvas : Canvas? = null
    private var circleBitmap : Bitmap? = null

    init{
        mPaint
    }

    private var mWidth = 0
    private var mHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        circleBitmap?.recycle()
        if (measuredWidth != 0 && measuredHeight != 0) {
            circleBitmap = Bitmap.createBitmap(measuredWidth,measuredHeight,Bitmap.Config.ARGB_8888)
            circleCanvas = Canvas(circleBitmap)
        }
        Log.d("EclipseAnimView : " ,"onSizeChanged :${measuredWidth}")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 开启动画
        startAnimator()
    }

    private var startAnimator: ValueAnimator? = null

    private var fraction : Float = 0f
    private var count : Int = 0
    private var eclipseEnd = false

    private fun startAnimator() {
        if (startAnimator == null) {
            startAnimator = ValueAnimator.ofFloat(0f,1f).apply {
                repeatMode = ValueAnimator.RESTART
                repeatCount = 2
                duration = 2000
                addUpdateListener {
                    fraction = it.animatedValue as Float
                    postInvalidateOnAnimation()
                }
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        eclipseEnd = true
                        fraction = 0f
                        ValueAnimator.ofFloat(0f,4f).apply {
                            duration = 1000
                            repeatCount = 1
                            repeatMode = ValueAnimator.RESTART
                            addUpdateListener({
                                fraction = it.animatedValue as Float
                                postInvalidateOnAnimation()
                            })
                            start()
                        }
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                        super.onAnimationRepeat(animation)
                        count ++
                        Log.d("EclipseAnimView : " ,"count :${count}")
                    }
                })
                start()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        startAnimator?.cancel()
        circleBitmap?.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    val radius = 100f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("EclipseAnimView : " ,"onDraw :${measuredWidth}")
        canvas.drawARGB(255,23,12,53)
        if (eclipseEnd) {
            canvas.save()
            mPaint.color = Color.YELLOW
            mPaint.style = Paint.Style.STROKE
            canvas.translate(mWidth/2f,mHeight/2f)
            val rectf = RectF(-radius,-radius,radius,radius)
            canvas.drawArc(rectf,-90 * (1 + fraction),90f ,false,mPaint)
            if (fraction >= 4) {
                mPaint.style = Paint.Style.FILL
                canvas.drawCircle(rectf.centerX(),rectf.centerY(),radius,mPaint)
            }
            canvas.restore()

        } else {
            circleCanvas?.let {
                it.save()
                it.drawARGB(255,23,12,53)
                mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                it.drawPaint(mPaint)
                mPaint.xfermode = null
                it.translate(mWidth / 2f , mHeight / 2f)
                mPaint.color = Color.YELLOW
                it.drawCircle(0f,0f,radius,mPaint)
                mPaint.color = Color.TRANSPARENT
                mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
                Log.d("EclipseAnimView : " ,"fraction :${if(count % 2 == 0)fraction else -fraction}")
                it.drawCircle((if(count % 2 == 0)fraction else -fraction) * radius * 2,0f,radius,mPaint)
                mPaint.xfermode = null
                it.restore()
                canvas.drawBitmap(circleBitmap,0f,0f,null)
            }
        }
    }
}