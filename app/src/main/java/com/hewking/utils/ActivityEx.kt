package com.hewking.utils

import android.app.Activity
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