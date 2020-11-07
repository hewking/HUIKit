package com.hewking.utils

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/3
 * 修改人员：hewking
 * 修改时间：2018/5/3
 * 修改备注：
 * Version: 1.0.0
 */

val dp: Float = Resources.getSystem()?.displayMetrics?.density ?: 0f
val dpi: Int = Resources.getSystem()?.displayMetrics?.density?.toInt() ?: 0

fun Int.toDp(): Float {
    return this * dp.toFloat()
}

fun Int.toDpi(): Int {
    return this * dpi
}