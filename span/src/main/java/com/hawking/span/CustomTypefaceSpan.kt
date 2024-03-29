package com.hawking.span

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

/**
 * @Description: 自定义Typeface 的span
 * @Author: jianhao
 * @Date:   2021/4/14 17:11
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
internal class CustomTypefaceSpan(private val newType: Typeface) : TypefaceSpan("") {
  override fun updateDrawState(textPaint: TextPaint) {
    apply(textPaint, newType)
  }

  override fun updateMeasureState(paint: TextPaint) {
    apply(paint, newType)
  }

  private fun apply(paint: Paint, tf: Typeface) {
    val oldStyle: Int
    val old = paint.typeface
    oldStyle = old?.style ?: 0
    val fake = oldStyle and tf.style.inv()
    if (fake and Typeface.BOLD != 0) {
      paint.isFakeBoldText = true
    }
    if (fake and Typeface.ITALIC != 0) {
      paint.textSkewX = -0.25f
    }
    paint.shader
    paint.typeface = tf
  }
}