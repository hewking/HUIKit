package com.hawking.span

import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * @Description: 设置shader 的span
 * @Author: jianhao
 * @Date:   2021/4/14 17:10
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
internal class ShaderSpan(private val mShader: Shader) : CharacterStyle(), UpdateAppearance {

  override fun updateDrawState(tp: TextPaint) {
    tp.shader = mShader
  }

}