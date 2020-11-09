package com.hewking.utils

import android.content.res.Resources
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 *@Description: 添加一些View 相关的拓展函数
 *@Author: jianhao
 *@Date:   2020/10/27 7:22 PM
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of Hive Box,
 and it is prohibited to leak or used for other commercial purposes.
 */

internal fun TextView.setDrawableLeft(@DrawableRes drawableRes: Int) {
  this.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, drawableRes),
      null, null, null)
}

val Float.dp
  get() = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      this,
      Resources.getSystem().displayMetrics
  )

val Int.dp
  get() = this.toFloat().dp.toInt()