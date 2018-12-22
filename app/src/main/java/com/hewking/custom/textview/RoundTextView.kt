package com.hewking.custom.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import com.hewking.custom.util.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/19 0019
 * 修改人员：hewking
 * 修改时间：2018/12/19 0019
 * 修改备注：
 * Version: 1.0.0
 */
class RoundTextView(ctx: Context, attrs: AttributeSet) : TextView(ctx, attrs) {

    private val rect = Rect()

    override fun onDraw(canvas: Canvas?) {
        val text = text.toString()
        if (!TextUtils.isEmpty(text)) {
            paint.getTextBounds(text, 0, text.length - 1, rect)
            rect.left = paddingLeft
            rect.top = paddingTop
            rect.bottom = height - paddingBottom
            rect.right = width - paddingRight
            paint.color = Color.GREEN
            canvas?.drawRoundRect(RectF(rect), dp2px(4f).toFloat(), dp2px(4f).toFloat(), paint)
        }
        super.onDraw(canvas)

    }

}