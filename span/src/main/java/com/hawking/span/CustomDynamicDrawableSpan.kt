package com.fcbox.screw.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import java.lang.ref.WeakReference

/**
 * @Description: 自定义Drawable Span，可以设置Drawable的Align
 * @Author: jianhao
 * @Date:   2021/4/14 17:10
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
abstract class CustomDynamicDrawableSpan : ReplacementSpan {

  private val mVerticalAlignment: Int

  private constructor() {
    mVerticalAlignment = ALIGN_BOTTOM
  }

  constructor(verticalAlignment: Int) {
    mVerticalAlignment = verticalAlignment
  }

  abstract val drawable: Drawable?

  override fun getSize(paint: Paint, text: CharSequence,
                       start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
    val d = cachedDrawable
    val rect = d?.bounds
    if (fm != null && rect != null) {
      val lineHeight = fm.bottom - fm.top
      if (lineHeight < rect.height()) {
        when (mVerticalAlignment) {
          ALIGN_TOP -> {
            fm.top = fm.top
            fm.bottom = rect.height() + fm.top
          }
          ALIGN_CENTER -> {
            fm.top = -rect.height() / 2 - lineHeight / 4
            fm.bottom = rect.height() / 2 - lineHeight / 4
          }
          else -> {
            fm.top = -rect.height() + fm.bottom
            fm.bottom = fm.bottom
          }
        }
        fm.ascent = fm.top
        fm.descent = fm.bottom
      }
    }
    return rect?.right ?: 0
  }

  override fun draw(canvas: Canvas, text: CharSequence,
                    start: Int, end: Int, x: Float,
                    top: Int, y: Int, bottom: Int, paint: Paint) {
    val d = cachedDrawable
    val rect = d?.bounds
    canvas.save()
    val transY: Float
    val lineHeight = bottom - top
    if (rect != null && rect.height() < lineHeight) {
      transY = when (mVerticalAlignment) {
        ALIGN_TOP -> {
          top.toFloat()
        }
        ALIGN_CENTER -> {
          ((bottom + top - rect.height()) / 2).toFloat()
        }
        ALIGN_BASELINE -> {
          (y - rect.height()).toFloat()
        }
        else -> {
          (bottom - rect.height()).toFloat()
        }
      }
      canvas.translate(x, transY)
    } else {
      canvas.translate(x, top.toFloat())
    }

    d?.draw(canvas)
    canvas.restore()
  }

  private val cachedDrawable: Drawable?
    get() {
      val wr = mDrawableRef
      var d: Drawable? = null
      if (wr != null) {
        d = wr.get()
      }
      if (d == null) {
        d = drawable
        mDrawableRef = WeakReference(d)
      }
      return d
    }
  private var mDrawableRef: WeakReference<Drawable?>? = null

  companion object {
    const val ALIGN_BOTTOM = 0
    const val ALIGN_BASELINE = 1
    const val ALIGN_CENTER = 2
    const val ALIGN_TOP = 3
  }
}