package com.hewking.demo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.hewking.custom.R

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/20 0020
 * 修改人员：hewking
 * 修改时间：2018/12/20 0020
 * 修改备注：
 * Version: 1.0.0
 */
class ImageScaleTypeFragment : Fragment() {

    private val sScaleTypeArray =
            arrayOf<ImageView.ScaleType>(ImageView.ScaleType.MATRIX
                    , ImageView.ScaleType.FIT_XY
                    , ImageView.ScaleType.FIT_START
                    , ImageView.ScaleType.FIT_CENTER,
                    ImageView.ScaleType.FIT_END,
                    ImageView.ScaleType.CENTER,
                    ImageView.ScaleType.CENTER_CROP,
                    ImageView.ScaleType.CENTER_INSIDE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = FlexboxLayout(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            layoutParams = ViewGroup.LayoutParams(-2, -2)
        }

        sScaleTypeArray.forEach {
            val iv = createImageView()
            iv.scaleType = it
            val frame = FrameLayout(context)
            frame.addView(iv, FrameLayout.LayoutParams(dp2px(120f), dp2px(120f)))
            frame.addView(TextView(context).apply {
                text = it.name
                setPadding(20, 20, 20, 20)
            }, FrameLayout.LayoutParams(-2, -2).apply {
                width = -2
                height = -2
                gravity = Gravity.BOTTOM
            })
            root.addView(frame, FlexboxLayout.LayoutParams(dp2px(120f), dp2px(120f)).apply {
                setMargins(20, 20, 20, 20)
            })
        }

        return root
    }

    private fun createImageView(): ImageView {
        return ImageView(context).apply {
            setImageResource(R.drawable.asm_logo)
        }
    }

    fun Fragment.dp2px(dp: Float): Int {
        return (context?.resources?.displayMetrics?.density?.times(dp)?.plus(0.5))?.toInt() ?: 0
    }
}