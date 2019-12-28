package com.hewking.custom.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/9 0009
 * 修改人员：hewking
 * 修改时间：2019/1/9 0009
 * 修改备注：
 * Version: 1.0.0
 */
class FlowLayout(ctx: Context, attrs: AttributeSet) : ViewGroup(ctx, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wSpec = MeasureSpec.getMode(widthMeasureSpec)
        val hSpec = MeasureSpec.getMode(heightMeasureSpec)

        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        var rowLen = 0// 行
        var columnLen = 0// 列

        var rowMaxLen = 0
        var columnMaxLen = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            rowLen += child.measuredWidth
            if (rowLen > wSize) {
                // 另起一行
                rowMaxLen = wSize
                rowLen = 0
                columnLen += child.measuredHeight
                if (columnLen > hSize) {
                    columnMaxLen = hSize
                } else {
                    columnMaxLen = columnLen
                }
            } else {
                rowMaxLen = Math.max(rowMaxLen, rowMaxLen)
            }
        }

        if (wSpec != MeasureSpec.EXACTLY) {
            // 根据widthSpec 跟rowMaxLen 比较获取 最终宽度
            wSize = resolveSize(widthMeasureSpec, rowMaxLen)
        }

        if (hSpec != MeasureSpec.EXACTLY) {
            hSize = resolveSize(heightMeasureSpec, columnMaxLen)
        }

        setMeasuredDimension(wSize, hSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var rowLen = l
        var columnLen = t
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (rowLen + child.measuredWidth > measuredWidth) {
                // 另起一行
                rowLen = left
                columnLen += child.measuredHeight
            }

            child.layout(rowLen, columnLen, rowLen + child.measuredWidth, columnLen + child.measuredHeight)
            rowLen += child.measuredWidth
        }

    }

}