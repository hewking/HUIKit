package com.hewking.uikit.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.hewking.uikit.BuildConfig
import com.hewking.uikit.R
import com.hewking.utils.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/29 0029
 * 修改人员：hewking
 * 修改时间：2018/11/29 0029
 * 修改备注：
 * Version: 1.0.0
 */
class LineTextView(ctx : Context,attrs : AttributeSet) : AppCompatTextView(ctx,attrs) {

    private var lineColor : Int = Color.GRAY
    private var lineWidth : Int = dp2px(0.5f)
    private var lineLength : Int = dp2px(50f)
    private var space = dp2px(5f)

    private val mPaint by lazy {
        Paint().apply {
            color = lineColor
            isAntiAlias = true
            strokeWidth = lineWidth.toFloat()
        }
    }

    init {
        val array = ctx.obtainStyledAttributes(attrs, R.styleable.LineTextView)
        for (i in 0..array.indexCount) {
            when(array.getIndex(i)) {
                R.styleable.LineTextView_line_color -> {
                    lineColor = array.getColor(R.styleable.LineTextView_line_color,lineColor)
                }
                R.styleable.LineTextView_line_length -> {
                    lineLength = array.getDimensionPixelSize(R.styleable.LineTextView_line_width,lineLength)
                }
                R.styleable.LineTextView_line_width -> {
                    lineWidth = array.getColor(R.styleable.LineTextView_line_width,lineWidth)
                }
                R.styleable.LineTextView_line_space -> {
                    space = array.getColor(R.styleable.LineTextView_line_space,space)
                }
            }
        }

        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth + 2 * (lineLength + space),measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        if (BuildConfig.DEBUG) {
            canvas.drawColor(Color.parseColor("#FF93ECB2"))
        }
        val linePosH = (height + lineWidth).div(2).toFloat()
        canvas.drawLine(paddingStart.toFloat(),linePosH,lineLength.toFloat(),linePosH,mPaint)
        canvas.save()
        canvas.translate(lineLength + space.toFloat() ,0f)
        super.onDraw(canvas)
        canvas.restore()
        canvas.drawLine(width - lineLength.toFloat(),linePosH,width - paddingEnd.toFloat(),linePosH,mPaint)
    }

}