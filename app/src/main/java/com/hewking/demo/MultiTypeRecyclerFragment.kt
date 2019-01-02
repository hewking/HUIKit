package com.hewking.demo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.hewking.base.recyclerview.ComnViewHolder
import com.hewking.base.recyclerview.MultiTypeAdapter
import com.hewking.custom.R
import com.hewking.custom.XfermodeSampleView2
import kotlinx.android.synthetic.main.fragment_multi_type.*

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/2
 * 修改人员：hewking
 * 修改时间：2019/1/2
 * 修改备注：
 * Version: 1.0.0
 */

class MultiTypeRecyclerFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_multi_type,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.addItemDecoration(RBaseItemDecoration())
        recyclerview.itemAnimator = DefaultItemAnimator()

        val adapter = DemoAdapter()
        adapter.append("fsdfsdf")
        adapter.append(33)
        recyclerview.adapter = adapter
    }

    class DemoAdapter : MultiTypeAdapter(){
        init {
            register(String::class.java,VH1::class.java)
            register(Int::class.java,VH2::class.java)
        }
    }

    inner class VH1 : ComnViewHolder{
        constructor(): super(XfermodeSampleView2(context!!,null).apply {
            layoutParams = ViewGroup.LayoutParams(-2,-2)
        }) {

        }
    }

    inner  class  VH2 : ComnViewHolder{
        constructor(): super(TextView(context!!,null).apply {
            layoutParams = ViewGroup.LayoutParams(-2,-2)
            text = "hahaha"
            textSize = 30f
            gravity = Gravity.CENTER
        }) {

        }
    }

}