package com.hewking.androidview.flexboxlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import kotlinx.android.synthetic.main.flexboxlayout_fragment.*
import java.text.NumberFormat

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/21
 * 修改人员：hewking
 * 修改时间：2018/5/21
 * 修改备注：
 * Version: 1.0.0
 */
class FlexBoxLayoutFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.flexboxlayout_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in 0 until 12) {
//            if (i.rem(2) == 0) {
//                //偶数
//                flowlayout.addView(createImageView(i))
//            } else {
            flowlayout.addView(createTextView(i))
//            }
        }
    }

    private fun createImageView(i: Int): View? {
        return ImageView(activity).apply {
            layoutParams = ViewGroup.LayoutParams(dp2px(150f), dp2px(120f))
            setImageResource(R.drawable.asm_logo)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun createTextView(i: Int): View? {
        return TextView(activity).apply {
            //            text = "text ${(i + 1).div(2)}"
            val format = NumberFormat.getInstance()
            format.isGroupingUsed = false
            text = "text ${format.format(Math.pow(10.toDouble(), i.toDouble()))}"
            layoutParams = ViewGroup.LayoutParams(-2, -2)
            setPadding(dp2px(10f), dp2px(10f), dp2px(10f), dp2px(10f))
        }
    }


}