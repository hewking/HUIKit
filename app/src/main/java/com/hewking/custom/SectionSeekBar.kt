package com.hewking.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.hewking.utils.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/3/16
 * 修改人员：hewking
 * 修改时间：2018/3/16
 * 修改备注：
 * Version: 1.0.0
 */
class SectionSeekBar(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    /**
     * 未选中的圆和线的颜色
     */
    private var mUnselectColor = 0
    /**
     * 已选中的圆和半径
     */
    private var mSelectColor = 0

    /**
     * 小圆的半径
     */
    private var mRadius = 0f
    /**
     * 选中的园的半径
     */
    private var mSelectRadius = 0f

    /**
     * 线的宽度
     */
    private var mLineWidth = 0f

    /**
     * 需要的section 数量
     */
    private var sectionCount = 0
    /**
     * 当前选中的位置
     */
    private var mCurPosition = -1

    /**
     * 是否支持拖动进度
     */
    private var canDrag = true

    private val mLinePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = mLineWidth
            style = Paint.Style.FILL
            color = mUnselectColor
        }
    }

    private val mSectionPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = mUnselectColor
        }
    }

    private var mOnSectionChangeListener : OnSectionChangeListener? = null

    init {
        val attrs = ctx.obtainStyledAttributes(attrs, R.styleable.SectionSeekBar)
        mRadius = attrs.getDimensionPixelSize(R.styleable.SectionSeekBar_section_radius, dp2px(4f)).toFloat()
        mSelectRadius = attrs.getDimensionPixelSize(R.styleable.SectionSeekBar_section_select_radus, (mRadius * 1.5f).toInt()).toFloat()
        mSelectColor = attrs.getColor(R.styleable.SectionSeekBar_section_select_colorr, Color.BLUE)
        mUnselectColor = attrs.getColor(R.styleable.SectionSeekBar_section_unselect_colro, Color.GRAY)
        sectionCount = attrs.getInteger(R.styleable.SectionSeekBar_section_section_count, 5)
        mCurPosition = attrs.getInteger(R.styleable.SectionSeekBar_section_cur_position, 0)
        mLineWidth = attrs.getDimensionPixelSize(R.styleable.SectionSeekBar_section_line_width, dp2px(1f)).toFloat()
        attrs.recycle()

        // 初始化画笔
        mLinePaint
        mSectionPaint

        if (BuildConfig.DEBUG) {
            mOnSectionChangeListener = object : OnSectionChangeListener{
                override fun onCurrentSectionChange(curPos: Int, oldPos: Int) {
                    Log.d(SectionSeekBar::class.java.simpleName,"curPos :  ${curPos}  oldPos : ${oldPos}")
                }
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == MeasureSpec.AT_MOST) {
            val mHeight = paddingTop + paddingBottom + 2 * mSelectRadius
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight.toInt())
        }
    }

    private var mLastX = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (canDrag) {
            when(event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastX = event?.x
                }

                MotionEvent.ACTION_MOVE -> {
                    mLastX = event?.x
                    invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    /**
                     * 计算当前 up 事件所在的点
                     */
                    mLastX = calcDstX(event?.x)
                    // 动画过渡 增强体验
                    smoothToTarget(event?.x,mLastX)
//                    invalidate()
                }
            }

            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

    private fun smoothToTarget(x: Float, lastX: Float) {
        ValueAnimator.ofFloat(x,lastX)
                .apply {
                    duration = 300
                    addUpdateListener {
                        mLastX = (it.animatedValue as Float)
                        postInvalidateOnAnimation()
                    }
                    start()
                }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 取消动画
    }

    /**
     * 计算当前点 x 坐标 距离 那个section 坐标绝对值最近 并返回
     * section 点 圆心 x坐标
     */
    private fun calcDstX(x: Float): Float {
        val maxX = width - paddingRight - mSelectRadius
        val minX = paddingLeft + mSelectRadius
        val targetX = Math.max(minX,Math.min(x,maxX))
        val sectionWidth = (width - paddingLeft - paddingRight - 2 * mSelectRadius) / (sectionCount - 1)
        val sectionRound = Math.round((targetX - minX) / sectionWidth)
        Log.d(SectionSeekBar::class.java.simpleName,"sectionRound : ${sectionRound} sectionWidth : ${sectionWidth} targetX : ${targetX}")
        val calcX = Math.min(sectionWidth * sectionRound + minX,maxX)
        val oldPos = mCurPosition
        // 判断是否当前section选中改变
        mCurPosition = (calcX / sectionWidth).toInt()
        if (oldPos != mCurPosition) {
            mOnSectionChangeListener?.onCurrentSectionChange(mCurPosition,oldPos)
        }

        return calcX
    }

    override fun onDraw(canvas: Canvas?) {
        drawBg(canvas)
        drawSection(canvas)
        drawProgress(canvas)
    }

    /**
     * 画圆圈
     */
    private fun drawSection(canvas: Canvas?) {
        val sectionWidth = (width - paddingLeft - paddingRight - 2 * mSelectRadius) / (sectionCount - 1)
        val cy = paddingTop.toFloat() + mSelectRadius
        var cx = 0f
        var startX = paddingLeft.toFloat() + mSelectRadius
        for (index in 0..sectionCount - 1) {
            cx = index * sectionWidth.toFloat() + startX
            if (cx <= mLastX) {
                mSectionPaint.color = mSelectColor
            } else {
                mSectionPaint.color = mUnselectColor
            }
            canvas?.drawCircle(cx, cy, mRadius, mSectionPaint)
        }
    }

    /**
     * 画进度
     */
    private fun drawProgress(canvas: Canvas?) {
        mLinePaint.color = mSelectColor
        val maxX = width - paddingRight - mSelectRadius
        val minX = paddingLeft + mSelectRadius
        val targetX = Math.max(minX,Math.min(mLastX,maxX))
        val rect = RectF(minX, paddingTop + mSelectRadius, targetX, paddingTop + mSelectRadius + mLineWidth)
        canvas?.drawRect(rect, mLinePaint)
        val cy = paddingTop.toFloat() + mSelectRadius
        // 绘制当前进度所在的园
        if (mLastX >= minX) {
            mSectionPaint.color = mSelectColor
            canvas?.drawCircle(targetX,cy,mSelectRadius,mSectionPaint)
        }

        Log.d(SectionSeekBar::class.java.simpleName,"mLastx :  ${mLastX}")
    }

    /**
     * 画默认进度条
     */
    private fun drawBg(canvas: Canvas?) {
        mLinePaint.color = mUnselectColor
        val rect = RectF(paddingLeft + mSelectRadius, paddingTop + mSelectRadius, width - paddingRight - mSelectRadius, paddingTop + mSelectRadius + mLineWidth)
        canvas?.drawRect(rect, mLinePaint)
    }

    interface OnSectionChangeListener{
        fun onCurrentSectionChange(curPos : Int, oldPos : Int)
    }

}
