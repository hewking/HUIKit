package com.hewking.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import kotlinx.android.synthetic.main.activity_key_board.view.*

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/7/23
 * 修改人员：hewking
 * 修改时间：2018/7/23
 * 修改备注：
 * Version: 1.0.0
 */
class DYRecordFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dyrecord,container,false)
    }

}