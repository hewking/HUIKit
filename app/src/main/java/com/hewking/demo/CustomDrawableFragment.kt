package com.hewking.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.custom.R
import com.hewking.custom.shader.WaterMarkDrawable

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/23
 * 修改人员：hewking
 * 修改时间：2018/12/23
 * 修改备注：
 * Version: 1.0.0
 */

class CustomDrawableFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tinder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.background = WaterMarkDrawable()
    }
}