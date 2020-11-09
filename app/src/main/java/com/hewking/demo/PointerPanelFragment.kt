package com.hewking.demo

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import com.hewking.utils.dpi
import kotlinx.android.synthetic.main.fragment_pointerpanel.*
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
class PointerPanelFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pointerpanel,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        first_view.onPointerTextConfig = { degrees, progress, config ->
            when (progress) {
                0f -> {
                    config.text = "0°C"
                    config.textOffsetX = -10 * dpi
                    config.textOffsetY = 8 * dpi
                }
                1f -> {
                    config.text = "65°C"
                    config.textOffsetX = 10 * dpi
                }
                0.5f -> {
                    config.text = "45°C"
                    config.textOffsetY = -8 * dpi
                }
            }
        }

        second_view.isEnabled = false
        second_view.onPointerTextConfig = { degrees, progress, config ->
            Log.i("main", "$progress")
            when (progress) {
                0.1f -> {
                    config.text = "25°C"
                }
                0.3f -> {
                    config.text = "35°C"
                }
                0.5f -> {
                    config.text = "45°C"
                }
                0.7f -> {
                    config.text = "55°C"
                }
                0.9f -> {
                    config.text = "65°C"
                }
            }
        }


        first_view.onProgressChange = { progress, fromUser ->
            if (fromUser) {
                second_view.setProgress(progress)
            }
            text_view.text = "${(65 * progress).toInt()}°C"
        }
    }

}