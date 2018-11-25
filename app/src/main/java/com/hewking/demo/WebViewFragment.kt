package com.hewking.demo

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import kotlinx.android.synthetic.main.fragment_webview.*

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/19 0019
 * 修改人员：hewking
 * 修改时间：2018/11/19 0019
 * 修改备注：
 * Version: 1.0.0
 */
class WebViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_webview,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadUrl("tronbet.io")
    }

}