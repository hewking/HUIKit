package com.fcbox.screw.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan

/**
 * @Description: 自定义引用线span
 * @Author: jianhao
 * @Date:   2021/4/14 17:12
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
internal class CustomQuoteSpan(private val color: Int, private val stripeWidth: Int, private val gapWidth: Int) : LeadingMarginSpan {

  override fun getLeadingMargin(first: Boolean): Int {
    return stripeWidth + gapWidth
  }

  override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                 top: Int, baseline: Int, bottom: Int,
                                 text: CharSequence, start: Int, end: Int,
                                 first: Boolean, layout: Layout) {
    val style = p.style
    val color = p.color
    p.style = Paint.Style.FILL
    p.color = this.color
    c.drawRect(x.toFloat(), top.toFloat(), (x + dir * stripeWidth).toFloat(), bottom.toFloat(), p)
    p.style = style
    p.color = color
  }
}