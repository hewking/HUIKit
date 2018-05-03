package com.hewking.demo

import android.app.Activity
import android.support.annotation.IdRes
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

fun <T : View> Activity.v(@IdRes resId : Int) : T{
    return findViewById(resId) as T
}

fun <T : View> View.v(@IdRes resId : Int) : T{
    return findViewById(resId) as T
}