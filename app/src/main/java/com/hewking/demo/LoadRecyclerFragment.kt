package com.hewking.demo

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.hewking.base.BaseRecyclerFragment
import com.hewking.base.recyclerview.ComnBaseAdapter
import com.hewking.base.recyclerview.ComnViewHolder
import com.hewking.utils.v

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/30 0030
 * 修改人员：hewking
 * 修改时间：2018/10/30 0030
 * 修改备注：
 * Version: 1.0.0
 */
class LoadRecyclerFragment : BaseRecyclerFragment<String>() {

    private val handler = Handler(Looper.getMainLooper())

    override fun buildAdapter(): ComnBaseAdapter<String> {
        return LoadAdapter()
    }

    class LoadAdapter : ComnBaseAdapter<String>() {

        init {
//            enableStateView = false
        }

        override fun onBindComnViewHolder(holder: ComnViewHolder?, t: String?, position: Int) {
            holder?.itemView?.v<TextView>(android.R.id.text1)?.text = t
        }

        override fun getItemLayoutId(type: Int): Int {
            return android.R.layout.simple_list_item_1
        }

    }

    override fun onLoadData() {
        super.onLoadData()
        handler.postDelayed({
            onLoadEnd(createItems())
        }, 2000)
    }

    private fun createItems(): MutableList<String>? {
        val list = mutableListOf<String>()
        if (mPage > 2) {
            return list
        }
        for (i in mPage * 20 until (mPage + 1) * 20) {
            list.add("text ${i}")
        }
        return list
    }

}