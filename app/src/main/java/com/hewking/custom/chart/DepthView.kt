package com.hewking.custom.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.textHeight
import com.hewking.custom.util.textWidth


/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/9
 * 修改人员：hewking
 * 修改时间：2018/4/9
 * 修改备注：
 * Version: 1.0.0
 */
class DepthView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private var mLeftBgColor = Color.RED

    private val dataManager by lazy {
        DepthDataManager()
    }

    /**
     *  画出深度图背景渐变的paint
     */
    private val bgPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = dp2px(1f).toFloat()
            color = mLeftBgColor
        }
    }

    private val linePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = dp2px(1f).toFloat()
            color = Color.GRAY
            style = Paint.Style.STROKE
        }
    }

    private val textPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            textSize = dp2px(12f).toFloat()
            color = Color.GRAY
        }
    }

    private val mLeftRect by lazy {
        RectF()
    }

    private val mRightRect by lazy {
        RectF()
    }

    init {
        val style = ctx.obtainStyledAttributes(attrs, R.styleable.DepthView)
        mLeftBgColor = style.getColor(R.styleable.DepthView_bg_color, Color.GREEN)
        style.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mLeftRect.set(0f + paddingLeft
                , 0f + paddingTop
                , w.toFloat() / 2 - paddingRight
                , h.toFloat() - paddingBottom)

        mRightRect.set(w.toFloat() / 2
                , 0f + paddingTop
                , w.toFloat() - paddingRight
                , h.toFloat() - paddingBottom)

        dataManager.init(w / 2, h)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        drawLeft(canvas)
        drawRight(canvas)
        drawBorder(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas?) {
        val textSpace = height / 3
        val text = "232.33"
        for (i in 0..2) {
            canvas?.drawText(text,paddingLeft.toFloat() + textPaint.textHeight(),i * textSpace + textPaint.textHeight() + paddingTop,textPaint)
        }

        val textWidth = textPaint.textWidth(text)
        val bottomX = (width - textWidth) / 2
        val bottomY = height - paddingBottom - textPaint.textHeight() / 2 - textPaint.descent()
        canvas?.drawText(text,bottomX,bottomY,textPaint)
    }

    private fun drawBorder(canvas: Canvas?) {
        val pts = floatArrayOf(paddingLeft.toFloat(), paddingTop.toFloat(), paddingLeft.toFloat() ,height.toFloat() - paddingBottom,
                paddingLeft.toFloat(), height.toFloat() - paddingBottom, width.toFloat() - paddingRight, height.toFloat() - paddingBottom,
                width - paddingRight.toFloat(), paddingTop.toFloat(), width.toFloat() - paddingRight, height.toFloat() - paddingBottom,
                paddingLeft.toFloat(), height.toFloat() - paddingBottom - textPaint.textHeight() * 2, width.toFloat() - paddingRight, height.toFloat() - paddingBottom - textPaint.textHeight() * 2)
        canvas?.drawLines(pts, linePaint)
    }

    private fun drawRight(canvas: Canvas?) {
        canvas?.save()
        canvas?.translate(mRightRect.left,0f)
        bgPaint.color = Color.GREEN
        bgPaint.shader = LinearGradient(0f, mRightRect.top, 0f, mRightRect.bottom, intArrayOf(Color.GREEN, Color.WHITE), null, Shader.TileMode.CLAMP)
        val path = Path()
        val linePath = Path()
        linePath.moveTo(0f,mRightRect.bottom)
        path.moveTo(0f, mRightRect.bottom)
        val leftDatas = dataManager.getLeftData()
        leftDatas.forEach {
            path.lineTo(it.x, it.y)
            linePath.lineTo(it.x,it.y)
        }
        path.close()
        canvas?.drawPath(path, bgPaint)
        bgPaint.shader = null
        bgPaint.color = Color.BLUE
        bgPaint.style = Paint.Style.STROKE
        canvas?.drawPath(linePath,bgPaint)
        canvas?.restore()
    }

    private fun drawLeft(canvas: Canvas?) {
        bgPaint.color = mLeftBgColor
        bgPaint.shader = LinearGradient(0f, mLeftRect.top, 0f, mLeftRect.bottom, intArrayOf(mLeftBgColor, Color.WHITE), null, Shader.TileMode.CLAMP)
        val path = Path()
        path.moveTo(0f, mLeftRect.bottom)
        val leftDatas = dataManager.getLeftData()
        leftDatas.forEach {
            Log.d(DepthView::class.java.simpleName, "x : ${it.x}  y : ${it.y}")
            path.lineTo(it.x, it.y)
        }
        path.close()
        canvas?.drawPath(path, bgPaint)
    }


}