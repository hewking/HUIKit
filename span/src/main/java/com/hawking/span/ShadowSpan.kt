package com.fcbox.screw.span

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * @Description: 设置shadow 的span
 * @Author: jianhao
 * @Date:   2021/4/14 17:09
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
internal class ShadowSpan(private val radius: Float,
                 private val dx: Float,
                 private val dy: Float,
                 private val shadowColor: Int) : CharacterStyle(), UpdateAppearance {

  override fun updateDrawState(tp: TextPaint) {
    tp.setShadowLayer(radius, dx, dy, shadowColor)
  }

}