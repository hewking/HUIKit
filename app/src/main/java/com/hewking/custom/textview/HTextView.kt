package com.hewking.custom.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.hewking.custom.BuildConfig
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.textWidth

/**
 * 类的描述：多功能疯转的TextView
 * 1.需要在TextView 文字的左右添加一个点 用于显示 并且可以控制点的大小颜色
 * 创建人员：hewking
 * 创建时间：2018/5/3
 * 修改人员：hewking
 * 修改时间：2018/5/3
 * 修改备注：
 * Version: 1.0.0
 */
class HTextView(ctx : Context,attrs : AttributeSet) : AppCompatTextView(ctx,attrs) {

    /**
     * 点的颜色
     */
    private var dotColor = Color.BLACK

    /**
     * 点跟文字的间距
     */
    private var dotPadding = dp2px(10f)

    /**
     * 点的半径
     */
    private var dotRadis = dp2px(4f)

    /**
     * 是否显示小圆点 必须配合 gravity = center 使用
     */
    private var showDot = false

    /**
     * 是否文字与圆点间距 1.5倍还是 1倍  根据 MeasusreSpec 取值来定
     * Exactly 1 倍  At_MOST 1.5 倍
     */
    private var measureMode = MeasureSpec.EXACTLY

    /**
     * 圆点paint
     */
    private val circlePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = dotColor
        }
    }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs,R.styleable.HTextView)
        dotColor = typeArray.getColor(R.styleable.HTextView_dot_color,Color.BLACK)
        dotPadding = typeArray.getDimensionPixelOffset(R.styleable.HTextView_dot_padding,dp2px(10f))
        dotRadis = typeArray.getDimensionPixelOffset(R.styleable.HTextView_dot_radius,dp2px(4f))
        showDot = typeArray.getBoolean(R.styleable.HTextView_dot,false)
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)

        if (widthSpecMode == MeasureSpec.AT_MOST && showDot) {
            measureMode = MeasureSpec.AT_MOST
            setMeasuredDimension(measuredWidth + dotRadis * 2 + dotPadding,measuredHeight)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            if (BuildConfig.DEBUG) {
                circlePaint.color = Color.parseColor("#31BDF3")
                it.drawRect(Rect(0,0,width,height),circlePaint)
                circlePaint.color = Color.BLACK
            }
            if (showDot) {
                it.save()
                val dx = dotRadis * 2f + dotPadding
                it.translate(dx,0f)
                super.onDraw(canvas)
                it.restore()
                val scale = if (measureMode == MeasureSpec.EXACTLY) 1f else 1.5f
                val cx = (width - circlePaint.textWidth(text.toString())) / 2 - scale * dx
                val cy = height / 2f
//                it.drawCircle(cx,cy,dotRadis.toFloat(),circlePaint)
                            it.drawCircle(dotRadis.toFloat() + paddingLeft,cy,dotRadis.toFloat(),circlePaint)
            } else {
                super.onDraw(canvas)
            }
        }
//        canvas?.drawCircle(width / 2f,height / 2f,dotRadis * 1f,circlePaint)

    }



}