package com.hewking.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this).apply {
            text = "center text"
            textSize = dp2px(14f)
            gravity = Gravity.CENTER
            layoutParams = FrameLayout.LayoutParams(-2,-2).apply {
                gravity = Gravity.CENTER
            }
        }
        setContentView(tv)
    }


    fun Activity.dp2px(size : Float) : Float{
        return (resources.displayMetrics.density * size + 0.5).toFloat()
    }

    fun Activity.px2dp(size : Float) : Float{
        return (size.div(resources.displayMetrics.density) + 0.5).toFloat()
    }

}