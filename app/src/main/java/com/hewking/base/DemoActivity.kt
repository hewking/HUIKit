package com.hewking.base

import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hewking.custom.util.ResUtil

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
            background = ResUtil.generateGradientDrawable(dp2px(4f), Color.parseColor("#7d3dcc"), Color.parseColor("#944aee"))
        }
        setContentView(tv)
    }


    fun androidx.appcompat.app.AppCompatActivity.dp2px(size : Float) : Float{
        return (resources.displayMetrics.density * size + 0.5).toFloat()
    }

    fun androidx.appcompat.app.AppCompatActivity.px2dp(size : Float) : Float{
        return (size.div(resources.displayMetrics.density) + 0.5).toFloat()
    }

}