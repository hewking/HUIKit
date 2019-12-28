package com.hewking.custom.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.DragEvent
import android.widget.TextView
import androidx.core.graphics.toColorInt

/**
 * @program: PointerPanelViewDemo
 * @author: hewking
 * @create: 2019-12-28 22:32
 * @description: border prop TextView
 **/
class BorderTextView(context: Context,attrs: AttributeSet? = null) : TextView(context,attrs){


    /**
     * border 跟文字的左边距
     */
    private var borderPaddingLeft = 3.toDp()

    /**
     * border 跟文字的右边距
     */
    private var borderPaddingRight = 3.toDp()

    /**
     * border raduis
     */
    private var borderRadius = 3.toDp()

    private var borderColor = "#999999".toColorInt()

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = 1.toDp()
        }
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.BorderTextView)
        borderPaddingLeft = typeArray.getDimension(R.styleable.BorderTextView_borderPaddingLeft,borderPaddingLeft)
        borderPaddingRight = typeArray.getDimension(R.styleable.BorderTextView_borderPaddingRight,borderPaddingRight)
        borderRadius = typeArray.getDimension(R.styleable.BorderTextView_borderRadius,borderRadius)
        borderColor = typeArray.getColor(R.styleable.BorderTextView_borderColor,borderColor)
        typeArray.recycle()
        paint
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth + borderPaddingLeft.toInt() + borderPaddingRight.toInt(),
            measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        canvas.save()
        canvas.translate(borderPaddingLeft,0f)
        super.onDraw(canvas)
        canvas.restore()
        canvas.drawRoundRect(0f + paddingLeft
            ,0f + paddingTop
            ,width.toFloat() - paddingRight
            ,height.toFloat() - paddingBottom
            ,borderRadius,borderRadius,paint)
    }

}