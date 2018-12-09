package com.livestar.flowchat.wallet.ui.tron

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hewking.demo.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/30 0030
 * 修改人员：hewking
 * 修改时间：2018/10/30 0030
 * 修改备注：
 * Version: 1.0.0
 */
class LoadView(val ctx : Context) : LinearLayout(ctx) {

    init {
        setUpView()
    }

    private fun setUpView() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        addView(TextView(ctx).apply {
            setPadding(dp2px(10f),dp2px(10f),dp2px(10f),dp2px(10f))
            text = "Loading"
            setTextColor(Color.parseColor("#666666"))
        })

        addView(ProgressBar(ctx).apply {
            setPadding(dp2px(10f),dp2px(10f),dp2px(10f),dp2px(10f))
        })
        layoutParams = RecyclerView.LayoutParams(-1,-2)
    }

}