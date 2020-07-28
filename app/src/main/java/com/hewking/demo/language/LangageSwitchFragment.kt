package com.hewking.demo.language

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.hewking.demo.MainActivity

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/9
 * 修改人员：hewking
 * 修改时间：2018/5/9
 * 修改备注：
 * Version: 1.0.0
 */
class LangageSwitchFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = LinearLayout(context)
        val params= ViewGroup.LayoutParams(-1,-1)
        root.orientation = LinearLayout.VERTICAL
        root.layoutParams = params

        val textlanguate = TextView(context)
        textlanguate.text = "当前语言 ： ${LanguageUtils.curLanguage}"

        val textSwitch = TextView(context)
        textSwitch.text = "切换"


        root.addView(textlanguate, LinearLayout.LayoutParams(-2, -2))
        root.addView(textSwitch, LinearLayout.LayoutParams(-2, -2))

        textSwitch.setOnClickListener {
            // 切换
            val intent = Intent(activity,MainActivity::class.java)
            LanguageUtils.curLanguage = "en"
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        return root
    }

}