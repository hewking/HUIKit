package com.hewking.custom

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import androidx.core.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.hewking.custom.util.UiUtil

/**
 * 类的描述：模仿抖音
 * 创建人员：hewking
 * 创建时间：2018/7/23
 * 修改人员：hewking
 * 修改时间：2018/7/23
 * 修改备注：
 * Version: 1.0.0
 */
class DYRecordView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private var mLargeCanvas: Canvas? = null
    private var mLargeBitmap: Bitmap? = null

    private var mlargeRadius = UiUtil.dipToPx(ctx, 50)
    private var mSmallRadius = UiUtil.dipToPx(ctx, 40)

    private var mCx: Float = 0f
    private var mCy: Float = 0f

    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.GREEN
        }
    }

    init {
        val typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.DepthView)
        typedArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startBreatheAnim()
    }

    private var breathAnimator: ValueAnimator? = null
    private var breathValue = 0f

    private fun startBreatheAnim() {
        if (breathAnimator == null) {
            breathAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                repeatMode = ValueAnimator.REVERSE
                duration = 1000
                interpolator = DecelerateInterpolator()
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        breathValue = (animation?.animatedValue) as Float
                        postInvalidateOnAnimation()
                    }
                })
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
                start()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopBreathAnim()
    }

    private val mGesterDetector = GestureDetectorCompat(ctx, object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            mCy -= distanceX
            mCy -= distanceY
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return super.onDown(e)
        }
    })


    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return mGesterDetector.onTouchEvent(event)
        mCx = event?.x
        mCy = event?.y
        when(event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    private fun stopBreathAnim() {
        breathAnimator?.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            mLargeBitmap?.recycle()
            mLargeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            mLargeCanvas = Canvas(mLargeBitmap)

            mCx = w / 2f
            mCy = h / 2f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        mLargeCanvas?.let {
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            it.drawPaint(mPaint)
            mPaint?.xfermode = null
            mPaint?.color = Color.GREEN

            it.drawCircle(mCx, mCy, mlargeRadius.toFloat(), mPaint)
            mPaint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))
            mPaint?.color = Color.RED

            it.drawCircle(mCx, mCy, mSmallRadius.toFloat() + breathValue * 15, mPaint)

            canvas?.drawBitmap(mLargeBitmap, 0f, 0f, null)
        }
    }

}
