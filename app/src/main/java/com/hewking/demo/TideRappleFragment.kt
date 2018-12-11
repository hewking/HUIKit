package com.hewking.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/9
 * 修改人员：hewking
 * 修改时间：2018/12/9
 * 修改备注：
 * Version: 1.0.0
 */

class TideRappleFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tide_rapple,container,false)
    }
}