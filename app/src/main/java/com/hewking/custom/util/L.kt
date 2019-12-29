package com.hewking.custom.util

import android.util.Log

/**
 * Created by test on 2018/1/21.
 */
fun Log.d(tag : String,content : String) {
    Log.d(tag,content)
}

object L {

    @JvmStatic
    fun d(msg: String, tag: String = "HUILibrary") {
        Log.d(tag, msg)
    }

    @JvmStatic
    fun e(msg: String, tag: String = "HUILibrary") {
        Log.e(tag, msg)
    }

    @JvmStatic
    fun i(msg: String, tag: String = "HUILibrary") {
        Log.i(tag, msg)
    }

    @JvmStatic
    fun w(msg: String, tag: String = "HUILibrary") {
        Log.w(tag, msg)
    }
}