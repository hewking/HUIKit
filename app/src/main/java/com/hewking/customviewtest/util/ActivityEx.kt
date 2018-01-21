package com.hewking.customviewtest.util

import android.app.Activity
import android.view.View

/**
 * Created by test on 2018/1/21.
 */
fun <T : View> Activity.v(resid : Int) : T {
    return findViewById(resid) as T
}