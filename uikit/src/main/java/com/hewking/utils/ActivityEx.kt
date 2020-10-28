package com.hewking.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

/**
 * Created by test on 2018/1/21.
 */
fun <T : View> AppCompatActivity.v(resId : Int) : T {
    return findViewById(resId)
}

internal fun Activity.toast(msg : String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

/**获取[Context]包含的[Activity]*/
fun Context?.activityContent(max: Int = 10): Activity? {
    var ctx = this
    var i = 0
    while (i < max && ctx !is Activity && ctx is ContextWrapper) {
        ctx = ctx.baseContext
        i++
    }
    return if (ctx is Activity) {
        ctx
    } else {
        null
    }
}