package com.hewking.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import com.hewking.utils.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/10
 * 修改人员：hewking
 * 修改时间：2018/5/10
 * 修改备注：
 * Version: 1.0.0
 */
class RecreateActivityDemo : AppCompatActivity(){

    var isRecreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isRecreate) {
            Log.i("RecreateActivityDemo","isRecreate true ：savedInstanceState ：${savedInstanceState == null} ")
        } else {
            Log.i("RecreateActivityDemo","isRecreate false ：savedInstanceState ：${savedInstanceState == null} ")
        }

        val root = FrameLayout(this)

        val fl_p = FrameLayout.LayoutParams(-2,-2)
        fl_p.gravity = Gravity.CENTER
        val tv_switch = Button(this)
        tv_switch.text = "切换"
        tv_switch.setPadding(tv_switch.dp2px(10f),tv_switch.dp2px(10f),tv_switch.dp2px(10f),tv_switch.dp2px(10f))
        tv_switch.setOnClickListener {
            isRecreate = true
            recreate()
        }
        root.addView(tv_switch,fl_p)
        setContentView(root)

    }
}