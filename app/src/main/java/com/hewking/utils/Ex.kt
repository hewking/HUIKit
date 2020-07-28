package com.hewking.utils

import android.view.View

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/3
 * 修改人员：hewking
 * 修改时间：2018/5/3
 * 修改备注：
 * Version: 1.0.0
 */

//fun <T : View> Activity.v(@IdRes resId : Int) : T{
//    return findViewById(resId) as T
//}
//
//fun <T : View> View.v(@IdRes resId : Int) : T{
//    return findViewById(resId) as T
//}
//
//fun View.dp2px(dp : Float) : Int{
//    return (context.resources.displayMetrics.density * dp + 0.5).toInt()
//}

fun View.px2dp(px : Int) : Int{
    return (px.div(context.resources.displayMetrics.density) + 0.5).toInt()
}