package com.hewking.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.uikit.XfermodeSampleView2

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/23
 * 修改人员：hewking
 * 修改时间：2018/12/23
 * 修改备注：
 * Version: 1.0.0
 */

class XfermodeFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val xfermodeView = XfermodeSampleView2(context!!, null).apply {
            layoutParams = ViewGroup.LayoutParams(-2,-2)
        }
        return xfermodeView
    }
}