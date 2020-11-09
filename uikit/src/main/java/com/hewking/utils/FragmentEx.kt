
package com.hewking.utils

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/5
 * 修改人员：hewking
 * 修改时间：2019/1/5
 * 修改备注：
 * Version: 1.0.0
 */

fun Fragment.dp2px(dp : Float) : Int {
    return (activity?.resources?.displayMetrics?.density?.times(dp)?.plus(0.5))?.toInt()?:0
}

fun Fragment.px2dp(px : Int) : Float{
    return (px.div(activity?.resources?.displayMetrics?.density!!).plus(0.5f)).toFloat()
}

@ColorInt
fun Fragment.getColor(@ColorRes res: Int): Int {
    return ContextCompat.getColor(context!!, res)
}