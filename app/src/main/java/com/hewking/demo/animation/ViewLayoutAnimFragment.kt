package com.hewking.demo.animation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/2/22 0022:10:12
 * 修改人员：hewking
 * 修改时间：2019/2/22 0022 10 12
 * 修改备注：
 * Version: 1.0.0
 */
class ViewLayoutAnimFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val id1 = View.generateViewId()
        val id2 = View.generateViewId()
        return FrameLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(-1,-1)
            addView(TextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(-2,-2).apply {
                    gravity = Gravity.TOP.or(Gravity.LEFT)
                }
                text = "来盘我啊"
                textSize = 16f
                id = id1
                setOnClickListener {
                    // 移动位置
                    swipAnimation(view!!.findViewById<View>(id1),view!!.findViewById<View>(id2))
                }
            })

            addView(TextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(-2,-2).apply {
                    gravity = Gravity.BOTTOM.or(Gravity.CENTER_HORIZONTAL)
                }
                text = "盘你咋样"
                textSize = 16f
                id = id2
                setOnClickListener {
                    // 移动位置

                    swipAnimation(view!!.findViewById<View>(id2),view!!.findViewById<View>(id1))
                }
            })
        }
    }

    fun swipAnimation(v1 :View,v2 : View){
        val targetT = v2.left
        val targetB = v2.top

        val srcT = v1.left
        val srcB = v1.top
        layoutAnim(v1,targetT,targetB)
        layoutAnim(v2,srcT,srcB)

    }

    fun layoutAnim(v : View,x : Int,y : Int) {
        ObjectAnimator.ofInt(v,"Left",x).setDuration(400).start()
        ObjectAnimator.ofInt(v,"Right",x + v.measuredWidth).setDuration(400).start()
        ObjectAnimator.ofInt(v,"Top",y).setDuration(400).start()
        ObjectAnimator.ofInt(v,"Bottom",y + v.measuredHeight).setDuration(400).start()
    }



}