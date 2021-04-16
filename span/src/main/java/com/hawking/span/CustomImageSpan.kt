package com.fcbox.screw.span

import android.graphics.drawable.Drawable

/**
 * @Description: 自定义ImageSpan
 * @Author: jianhao
 * @Date:   2021/4/14 17:11
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
class CustomImageSpan(
    drawable: Drawable,
    verticalAlignment: Int
) : CustomDynamicDrawableSpan(verticalAlignment) {

  private var mDrawable: Drawable? = drawable

  init {
    mDrawable?.apply {
      setBounds(
          0, 0, intrinsicWidth, intrinsicHeight
      )
    }
  }

  override val drawable: Drawable?
    get() {
      return mDrawable
    }
}