package com.hewking.custom.textview

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.textclassifier.TextClassifier

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/26 0026
 * 修改人员：hewking
 * 修改时间：2018/11/26 0026
 * 修改备注：
 * Version: 1.0.0
 */
class SmartLinkTextView(ctx : Context,attrs : AttributeSet) : AppCompatTextView(ctx,attrs) {


    init {

    }

    override fun setTextClassifier(textClassifier: TextClassifier?) {
        super.setTextClassifier(textClassifier)
    }

}