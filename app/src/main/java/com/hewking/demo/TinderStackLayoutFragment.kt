package com.hewking.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.hewking.custom.R
import com.hewking.custom.tinderstack.TinderStackLayout
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.load
import kotlinx.android.synthetic.main.fragment_tinder.*

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/9
 * 修改人员：hewking
 * 修改时间：2018/12/9
 * 修改备注：
 * Version: 1.0.0
 */

class TinderStackLayoutFragment : androidx.fragment.app.Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tinder,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tinderStack.adapter = TinderCardAdapter().apply {

        }

        tinderStack.setChooseListener {
            if (it == 1) {
                Toast.makeText(activity,"right swipe select",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity,"left swipe select",Toast.LENGTH_SHORT).show()

            }
        }
    }

    inner class TinderCardAdapter : TinderStackLayout.BaseCardAdapter{

        val datas = mutableListOf<String>("https://tuimeizi.cn/random?w=665&h=656"
                ,"https://tuimeizi.cn/random?w=665&h=656"
                ,"https://tuimeizi.cn/random?w=667&h=656"
                ,"https://tuimeizi.cn/random?w=665&h=656"
                ,"https://tuimeizi.cn/random?w=668&h=656"
                ,"https://tuimeizi.cn/random?w=665&h=656"
                ,"https://tuimeizi.cn/random?w=665&h=659"
                ,"https://tuimeizi.cn/random?w=665&h=653"
                ,"https://tuimeizi.cn/random?w=665&h=656"
                ,"https://tuimeizi.cn/random?w=665&h=653")

        private var index = 0

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun getView(): View? {
            if (index > datas.size - 1) {
                return null
            }
            val img = ImageView(activity)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            img.layoutParams = ViewGroup.LayoutParams(dp2px(350f),dp2px(350f))
            img.load(datas[index])
            index ++
            return img
        }

    }
}