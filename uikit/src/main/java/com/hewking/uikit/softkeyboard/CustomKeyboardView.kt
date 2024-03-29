package com.hewking.uikit.softkeyboard

import android.content.Context
import android.graphics.*
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import com.hewking.uikit.R
import java.lang.reflect.Field

/**
 * @author: jianhao
 * @create: 2019/9/11
 * @description:
 */
class CustomKeyboardView : KeyboardView {

  private var mContext: Context
  private var mKeyBoard: Keyboard? = null

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    mContext = context
  }

  constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
    context,
    attrs,
    defStyle
  ) {
    mContext = context
  }

  /**
   * 重新画一些按键
   */
  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    mKeyBoard = this.keyboard
    var keys: List<Keyboard.Key>? = null
    if (mKeyBoard != null) {
      keys = mKeyBoard!!.keys
    }
    if (keys != null) {
      for (key in keys) {
        // 数字键盘的处理
        if (key.codes[0] == -4) {
          drawKeyBackground(R.drawable.drawable_keyboard_green, canvas, key)
          drawText(canvas, key)
        }
      }
    }
  }

  private fun drawKeyBackground(drawableId: Int, canvas: Canvas, key: Keyboard.Key) {
    val npd = mContext.resources.getDrawable(
      drawableId
    )
    val drawableState = key.currentDrawableState
    if (key.codes[0] != 0) {
      npd.state = drawableState
    }
    npd.setBounds(
      key.x, key.y, key.x + key.width, key.y
          + key.height
    )
    npd.draw(canvas)
  }

  private fun drawText(canvas: Canvas, key: Keyboard.Key) {
    val bounds = Rect()
    val paint = Paint()
    paint.textAlign = Paint.Align.CENTER
    paint.isAntiAlias = true
    paint.color = Color.WHITE
    if (key.label != null) {
      val label = key.label.toString()
      val field: Field
      if (label.length > 1 && key.codes.size < 2) { //NOSONAR
        var labelTextSize = 0
        try {
          field = KeyboardView::class.java.getDeclaredField("mLabelTextSize")
          field.isAccessible = true
          labelTextSize = field[this] as Int
        } catch (e: NoSuchFieldException) {
          e.printStackTrace()
        } catch (e: IllegalAccessException) {
          e.printStackTrace()
        }
        paint.textSize = labelTextSize.toFloat()
        paint.typeface = Typeface.DEFAULT_BOLD
      } else {
        var keyTextSize = 0
        try {
          field = KeyboardView::class.java.getDeclaredField("mLabelTextSize")
          field.isAccessible = true
          keyTextSize = field[this] as Int
        } catch (e: NoSuchFieldException) {
          e.printStackTrace()
        } catch (e: IllegalAccessException) {
          e.printStackTrace()
        }
        paint.textSize = keyTextSize.toFloat()
        paint.typeface = Typeface.DEFAULT
      }
      paint.getTextBounds(
        key.label.toString(), 0, key.label.toString()
          .length, bounds
      )
      canvas.drawText(
        key.label.toString(), (key.x + key.width / 2).toFloat(), (
            key.y + key.height / 2 + bounds.height() / 2).toFloat(), paint
      )
    } else if (key.icon != null) {
      key.icon.setBounds(
        key.x + (key.width - key.icon.intrinsicWidth) / 2,
        key.y + (key.height - key.icon.intrinsicHeight) / 2,
        key.x + (key.width - key.icon.intrinsicWidth) / 2 + key.icon.intrinsicWidth,
        key.y + (key.height - key.icon.intrinsicHeight) / 2 + key.icon.intrinsicHeight
      )
      key.icon.draw(canvas)
    }
  }
}