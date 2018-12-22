package com.hewking.custom.textview

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.Checkable

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/6 0006
 * 修改人员：hewking
 * 修改时间：2018/12/6 0006
 * 修改备注：
 * Version: 1.0.0
 */
class CheckedTextView(ctx : Context,attrs : AttributeSet) : AppCompatTextView(ctx,attrs)
        ,Checkable {

    private var isChecked = false

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun setChecked(checked: Boolean) {
        isChecked = checked
    }

}