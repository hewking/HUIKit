package com.hewking.demo

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.hewking.custom.R
import com.hewking.uikit.guide.HighLightChildFrameLayout
import com.hewking.utils.dp2px

class GuideLayoutDemoFragment : Fragment(){

    private var guide : HighLightChildFrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        guide = HighLightChildFrameLayout(context!!).apply {
            layoutParams = ViewGroup.LayoutParams(-1,-1)
            addView(FlexboxLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(-1,-1)
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP

                setOnClickListener {
                    process(null)
                }

                for (i in 0 until 5) {
                    addView(LinearLayout(context).apply {
                        layoutParams = FlexboxLayout.LayoutParams(-2,-2)
                        orientation = LinearLayout.VERTICAL
                        addView(createImageView(i))
                        addView(createEditView(i))
                        setOnTouchListener { v, event ->
                            when(event.actionMasked){
                                MotionEvent.ACTION_DOWN -> {
                                    // up事件 保存坐标，然后显示GuideLayout mask
                                    process(Rect(v.left
                                            ,v.top
                                            ,v.right
                                            ,v.bottom))

                                }
                            }
                            false
                        }
                    })
                }
            })
        }
        return guide
    }

    private fun process(rect: Rect?) {
        guide?.showHightLight(rect)
    }

    private fun createEditView(i: Int): View? {
        return EditText(context).apply {
            layoutParams = ViewGroup.LayoutParams(-2,-2)
            hint = "$i text"
        }
    }

    private fun createImageView(i: Int): View? {
        return ImageView(activity).apply {
            layoutParams = ViewGroup.LayoutParams(dp2px(150f), dp2px(120f))
            setImageResource(R.drawable.asm_logo)
            scaleType = ImageView.ScaleType.CENTER_CROP
            setOnTouchListener { v, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // up事件 保存坐标，然后显示GuideLayout mask
                        process(Rect(v.left
                                , v.top
                                , v.right
                                , v.bottom))

                    }
                }
                false
            }
        }
    }
}