package com.hewking.base.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/2 0002
 * 修改人员：hewking
 * 修改时间：2019/1/2 0002
 * 修改备注：
 * Version: 1.0.0
 */
class MultiTypeAdapter : RecyclerView.Adapter<ComnViewHolder>() {

    private var mDatas = mutableListOf<Any>()
    private val viewTypes = mutableMapOf<Class<out Any>, Int>()
    private val itemTypes = mutableMapOf<Any, Class<ComnViewHolder>>()

    init {

        register(String::class.java, ComnViewHolder::class.java)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComnViewHolder {

    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: ComnViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        val clazz = mDatas[position]::class.java
        return viewTypes[clazz]
    }

    private fun register(clazz: Class<out Any>, holder: Class<ComnViewHolder>) {
        itemTypes[clazz] = holder
        viewTypes[clazz] = viewTypes.size + 1
    }


}