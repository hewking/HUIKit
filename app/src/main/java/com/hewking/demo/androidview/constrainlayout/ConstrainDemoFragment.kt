package com.hewking.demo.androidview.constrainlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/21
 * 修改人员：hewking
 * 修改时间：2018/5/21
 * 修改备注：
 * Version: 1.0.0
 */
class ConstrainDemoFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.constrainlayout_fragment,container,false)
    }

}