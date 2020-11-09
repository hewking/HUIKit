package com.hewking.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 *@Description: (基础类型数据单位扩展)
 *@Author: 朱丽君
 *@Date:   20-10-27 上午11:38
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */

val Float.dp
  get() = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      this,
      Resources.getSystem().displayMetrics
  )

val Int.dp
  get() = this.toFloat().dp.toInt()