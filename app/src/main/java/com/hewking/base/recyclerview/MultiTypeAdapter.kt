//package com.hewking.base.recyclerview
//
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
///**
// * 项目名称：FlowChat
// * 类的描述：
// * 创建人员：hewking
// * 创建时间：2019/1/2 0002
// * 修改人员：hewking
// * 修改时间：2019/1/2 0002
// * 修改备注：
// * Version: 1.0.0
// */
//open class MultiTypeAdapter : RecyclerView.Adapter<ComnViewHolder>() {
//
//    private var mDatas = mutableListOf<Any>()
//    private val viewTypes = mutableMapOf<Class<out Any>, Int>()
//    private val itemTypes = mutableMapOf<Any, Class<out ComnViewHolder>>()
//
//    init {
//
//    }
//
//    open fun append(value : Any){
//        mDatas.add(value)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComnViewHolder {
//        val holderClazz = getVH(viewType)
//        return holderClazz?.getConstructor()?.newInstance()!!
//    }
//
//    override fun getItemCount(): Int {
//        return mDatas.size
//    }
//
//    override fun onBindViewHolder(holder: ComnViewHolder, position: Int) {
//        holder.bind()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val clazz = mDatas[position]::class.java
//        return viewTypes[clazz]!!
//    }
//
//    protected open fun register(clazz: Class<out Any>, holder: Class<out ComnViewHolder>) {
//        itemTypes[clazz] = holder
//        viewTypes[clazz] = viewTypes.size + 1
//    }
//
//    fun getVH(viewType : Int) : Class<out ComnViewHolder>? {
//        val entries = viewTypes.entries
//        for (entry in entries) {
//            if (entry.value == viewType) {
//                return itemTypes[entry.key]
//            }
//        }
//        return null
//    }
//
//}