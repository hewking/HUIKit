package com.hewking.demo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hewking.custom.R
import com.hewking.uikit.gallerylayout.GalleryLayout2
import com.hewking.utils.L
import com.hewking.utils.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/6
 * 修改人员：hewking
 * 修改时间：2019/1/6
 * 修改备注：
 * Version: 1.0.0
 */

class GalleryLayoutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(activity).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            addView(GalleryLayout2(activity!!, null).apply {
                layoutParams = FrameLayout.LayoutParams(dp2px(300f),dp2px(200f)).apply {
                    gravity = Gravity.CENTER
                }
                for (i in 0 until 5) {
                    addView(createImg(i),ViewGroup.LayoutParams(dp2px(300f),dp2px(200f)))
                }
            },FrameLayout.LayoutParams(dp2px(300f),dp2px(200f)))
        }
    }

    private fun createImg(pos : Int): View? {
        L.d("GalleryLayoutFragment","rem : ${pos.rem(5)}")
        return ImageView(activity).apply {
            setImageResource(imgArr[pos.rem(5)])
            layoutParams = ViewGroup.LayoutParams(dp2px(300f),dp2px(200f))
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private val imgArr = arrayOf(R.drawable.asm_logo,R.drawable.pocker_0x01,R.drawable.pocker_0x02
    ,R.drawable.pocker_0x03,R.drawable.asm_logo)
}