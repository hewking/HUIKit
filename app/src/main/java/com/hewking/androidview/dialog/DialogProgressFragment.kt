package com.hewking.androidview.dialog

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.hewking.custom.R
import com.hewking.demo.v
import kotlinx.android.synthetic.main.activity_audiorecorder.view.*

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/8/24 10:50
 * 修改人员：hewking
 * 修改时间：2018/8/24 10:50
 * 修改备注：
 * Version: 1.0.0
 */
class DialogProgressFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_progress,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cb = view.v<CheckBox>(R.id.cb_show)


        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val dialog = ProgressDialog.show(activity,"dialog","message")
                buttonView.text = "hide"
            } else {
                buttonView.text = "show"
            }
        }

    }

}