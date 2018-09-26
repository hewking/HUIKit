package com.hewking.demo

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.hewking.androidview.chronomoter.T
import com.hewking.custom.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/4
 * 修改人员：hewking
 * 修改时间：2018/5/4
 * 修改备注：
 * Version: 1.0.0
 */
class HTextViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_htextview,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.v<EditText>(R.id.et_input).addTextChangedListener(HexInputFilter(view.v<EditText>(R.id.et_input)))

        view.v<TextView>(R.id.tv_image_span).setText(SpannableStringUtils.getBuilder("xxxx")
                .setResourceId(R.mipmap.img_coindrop_icon)
                .append("span image test")
                .append("fsdffffffffffffffffffffffff")
                .create())

        view.findViewById<TextView>(R.id.tv_click_span).apply {
            text = "gggggddgfddddddddddddddddddddddddddddddddddwww.baidu.com"
            setOnLongClickListener {
                T("LongClickListener")
                true
            }
        }
    }

    fun Fragment.T(msg :String){
        Toast.makeText(DemoApplication.context,msg,Toast.LENGTH_SHORT).show()
    }

}