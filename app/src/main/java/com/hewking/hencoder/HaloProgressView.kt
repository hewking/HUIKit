package com.hewking.hencoder

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.util.UiUtil

/**
 * Created by test on 2017/11/26.
 */
class HaloProgressView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var probress : Int = 0
    var round = UiUtil.dipToPx(context,10)

    var mWidth = 0
    var mHeight = 0

    var outRadius = UiUtil.dipToPx(context,50)
    var innRaduus = UiUtil.dipToPx(context,40)
    var cicleCanvas : Canvas? = null
    var cicleBitmap : Bitmap? = null

    var cicleProgressCanvas : Canvas? = null
    var cicleProgressBitmap : Bitmap? = null

    lateinit var  paint : Paint
    lateinit var  paintProgress : Paint

    init {
        paint = Paint()
        paint.isAntiAlias = true
        paintProgress = Paint()
        paintProgress.isAntiAlias = true

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        cicleBitmap?.recycle()
        if (measuredHeight != 0 && measuredWidth != 0) {
            cicleBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
            cicleCanvas = Canvas(cicleBitmap)

            cicleProgressBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
            cicleProgressCanvas = Canvas(cicleProgressBitmap)

        }

        anin?.start()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        canvas?.save()
        path.addRoundRect(RectF(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat())
                ,round.toFloat(),round.toFloat(),Path.Direction.CW)
        canvas?.clipPath(path,Region.Op.INTERSECT)
        canvas?.drawARGB(36,66,66,66)
        canvas?.restore()

        if (probress < 100) {
            cicleCanvas?.let {
                it.save()
                it.translate(measuredWidth / 2f , measuredHeight / 2f)
                paint.style = Paint.Style.FILL
                paint.color = Color.BLUE
                it.drawCircle(0f,0f,outRadius.toFloat(),paint)
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))

                paint.color = Color.RED
                it.drawCircle(0f,0f,innRaduus.toFloat(),paint)
                paint.setXfermode(null)
                it.restore()
                canvas?.drawBitmap(cicleBitmap,0f,0f,null)
            }
        } else {
            cicleProgressCanvas?.let {
                it.save()
                it.translate(measuredWidth / 2f ,measuredHeight / 2f)

                paintProgress.color = Color.argb(36,66,66,66)
                it.drawRoundRect(RectF(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat())
                        ,round.toFloat(),round.toFloat(),paintProgress)

                paintProgress.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

                paintProgress.color = Color.TRANSPARENT
                it.drawCircle(0f,0f,outRadius.toFloat() + 100,paintProgress)

                it.restore()
                canvas?.drawBitmap(cicleProgressBitmap,0f,0f,null)

            }
        }
    }

    private var animatorValue : Float = 0f

    private val anin  by lazy {
        ObjectAnimator.ofFloat(0f,1f).apply {
            duration = 700
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                animatorValue = it.animatedValue as Float
                probress ++;
                if (probress > 100) {
                    probress = 0
                }
                postInvalidateOnAnimation()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cicleBitmap?.recycle()
    }


}