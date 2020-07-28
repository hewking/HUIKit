package com.hewking.demo

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.hewking.custom.LagouHomePanelLayout
import com.hewking.custom.R
import com.hewking.utils.dp2px
import com.hewking.utils.v

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/7
 * 修改人员：hewking
 * 修改时间：2018/5/7
 * 修改备注：
 * Version: 1.0.0
 */
class LagouTopFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lagou_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tablayout = view.v<TabLayout>(R.id.tablayout)
        val viewPager = view.v<androidx.viewpager.widget.ViewPager>(R.id.viewpager)

        val panelLayout = view.v<LagouHomePanelLayout>(R.id.panelLayout)

        view.v<Button>(R.id.btn_expand)?.setOnClickListener {
            panelLayout?.expand()
        }

        view.v<Button>(R.id.btn_collapsing)?.setOnClickListener {
            panelLayout?.collapsing()
        }

        viewPager?.adapter = object : androidx.viewpager.widget.PagerAdapter(){
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 3
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val recyclerView = androidx.recyclerview.widget.RecyclerView(container?.context)
//                recyclerView.layoutParams = RecyclerView.LayoutParams(400,500)

                recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(container?.context)
                recyclerView.addItemDecoration(RBaseItemDecoration())
                recyclerView.adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(){
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                        val text = TextView(parent?.context)
                        text.id = R.id.text_test
                        text.layoutParams = androidx.recyclerview.widget.RecyclerView.LayoutParams(-1,-2)
                        return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(text){

                        }
                    }

                    override fun getItemCount(): Int {
                        return 20
                    }

                    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                        val text = holder?.itemView?.v<TextView>(R.id.text_test)
                        text?.text = "text ${position}"
                        text?.setTextColor(Color.BLACK)
                        text?.setTextSize(16f)
                        text?.setPadding(text.dp2px(10f),text.dp2px(10f),text.dp2px(10f),text.dp2px(10f))
                    }

                }
                // 必须要有
                container?.addView(recyclerView,ViewGroup.LayoutParams(-1,-2))
                return recyclerView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container?.removeView(`object` as View?)
            }

            override fun getPageTitle(position: Int): CharSequence {
                return "Test ${position}"
            }

        }
        tablayout?.setupWithViewPager(viewPager)

    }

}