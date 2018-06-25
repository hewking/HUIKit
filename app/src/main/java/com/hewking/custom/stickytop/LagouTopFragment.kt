package com.hewking.custom.stickytop

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.hewking.custom.LagouHomePanelLayout
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.v
import com.hewking.demo.RBaseItemDecoration

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/7
 * 修改人员：hewking
 * 修改时间：2018/5/7
 * 修改备注：
 * Version: 1.0.0
 */
class LagouTopFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_lagou_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tablayout = view.v<TabLayout>(R.id.tablayout)
        val viewPager = view.v<ViewPager>(R.id.viewpager)

        val panelLayout = view.v<LagouHomePanelLayout>(R.id.panelLayout)

        view.v<Button>(R.id.btn_expand)?.setOnClickListener {
            panelLayout?.expand()
        }

        view.v<Button>(R.id.btn_collapsing)?.setOnClickListener {
            panelLayout?.collapsing()
        }

        viewPager?.adapter = object : PagerAdapter(){
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 3
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val recyclerView = RecyclerView(container?.context)
//                recyclerView.layoutParams = RecyclerView.LayoutParams(400,500)

                recyclerView.layoutManager = LinearLayoutManager(container?.context)
                recyclerView.addItemDecoration(RBaseItemDecoration())
                recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                        val text = TextView(parent?.context)
                        text.id = R.id.text_test
                        text.layoutParams = RecyclerView.LayoutParams(-1,-2)
                        return object : RecyclerView.ViewHolder(text){

                        }
                    }

                    override fun getItemCount(): Int {
                        return 20
                    }

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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