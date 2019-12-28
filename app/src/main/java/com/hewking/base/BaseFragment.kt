package com.hewking.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
open class BaseFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentLayoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadContentView()
    }

    open fun loadContentView() {

    }

    @LayoutRes
    open fun getContentLayoutId() : Int{
        return -1
    }

}