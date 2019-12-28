package com.hewking.base

import android.view.View

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/30 0030
 * 修改人员：hewking
 * 修改时间：2018/11/30 0030
 * 修改备注：
 * Version: 1.0.0
 */
class DelayClickListener(val listener: View.OnClickListener) : View.OnClickListener {

    private var delayTime = 0L
    private var interval = 2000// 2000 mills

    override fun onClick(v: View?) {
        val current = System.currentTimeMillis()
        if (current - delayTime > interval) {
            delayTime = current
            listener.onClick(v)
        }
    }

}