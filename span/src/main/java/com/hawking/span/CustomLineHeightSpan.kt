package com.hawking.span

import android.graphics.Paint
import android.text.Spanned
import android.text.style.LineHeightSpan

/**
 * @Description: 自定义行高的span
 * @Author: jianhao
 * @Date:   2021/4/14 17:13
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
internal class CustomLineHeightSpan(
    private val height: Int,
    private val mVerticalAlignment: Int
) : LineHeightSpan {

  override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                            spanstartv: Int, v: Int, fm: Paint.FontMetricsInt) {
    if (sfm == null) {
      sfm = Paint.FontMetricsInt().apply {
        top = fm.top
        ascent = fm.ascent
        descent = fm.descent
        bottom = fm.bottom
        leading = fm.leading
      }

    } else {
      sfm?.let {
        fm.top = it.top
        fm.ascent = it.ascent
        fm.descent = it.descent
        fm.bottom = it.bottom
        fm.leading = it.leading
      }
    }
    var need = height - (v + fm.descent - fm.ascent - spanstartv)
    if (need > 0) {
      when (mVerticalAlignment) {
        ALIGN_TOP -> {
          fm.descent += need
        }
        ALIGN_CENTER -> {
          fm.descent += need / 2
          fm.ascent -= need / 2
        }
        else -> {
          fm.ascent -= need
        }
      }
    }
    need = height - (v + fm.bottom - fm.top - spanstartv)
    if (need > 0) {
      when (mVerticalAlignment) {
        ALIGN_TOP -> {
          fm.bottom += need
        }
        ALIGN_CENTER -> {
          fm.bottom += need / 2
          fm.top -= need / 2
        }
        else -> {
          fm.top -= need
        }
      }
    }
    if (end == (text as Spanned).getSpanEnd(this)) {
      sfm = null
    }
  }

  companion object {
    const val ALIGN_CENTER = 2
    const val ALIGN_TOP = 3
    var sfm: Paint.FontMetricsInt? = null
  }
}