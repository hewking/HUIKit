package com.hewking.custom.util

import androidx.appcompat.app.AppCompatActivity
import android.view.View

/**
 * Created by test on 2018/1/21.
 */
fun <T : View> androidx.appcompat.app.AppCompatActivity.v(resid : Int) : T {
    return findViewById(resid) as T
}