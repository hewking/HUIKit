package com.hewking.demo

import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.hewking.base.recyclerview.BaseItemOffsetDecoration
import com.hewking.base.recyclerview.ComnBaseAdapter
import com.hewking.base.recyclerview.ComnViewHolder
import com.hewking.custom.R
import com.hewking.uikit.layoutmanager.FlowLayoutManager2
import com.hewking.utils.ResUtil
import com.hewking.utils.dp2px
import com.hewking.utils.getColor
import java.text.NumberFormat

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/11 0011
 * 修改人员：hewking
 * 修改时间：2019/1/11 0011
 * 修改备注：
 * Version: 1.0.0
 */
class FlowLayoutManagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(activity).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1).apply {
                addView(RecyclerView(activity!!).apply {
                    id = R.id.root
                    layoutParams = FrameLayout.LayoutParams(-1, -1).apply {
                        gravity = Gravity.CENTER
                    }
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.root).apply {
            addItemDecoration(object : BaseItemOffsetDecoration() {
                override fun applyOffset(): Rect {
                    return Rect(dp2px(4f), dp2px(4f), dp2px(4f), dp2px(4f))
                }
            })

            itemAnimator = DefaultItemAnimator()
            layoutManager = FlowLayoutManager2()
            val textAdapter = buildAdapter()
            adapter = textAdapter
            textAdapter.setData(buildDatas())
        }
    }

    private fun buildDatas(): MutableList<String>? {
        val list = mutableListOf<String>()
        for (i in 0 until 100) {
            list.add("random $i ${format.format(Math.pow(10.toDouble(), 2 + Math.random() * 8))}")
        }
        return list
    }

    val format = NumberFormat.getInstance().apply {
        isGroupingUsed = false
    }

    private fun buildAdapter(): ComnBaseAdapter<String> {
        return object : ComnBaseAdapter<String>() {
            override fun onBindComnViewHolder(holder: ComnViewHolder?, t: String?, position: Int) {
                holder ?: return
                holder.tv(R.id.tv_text).background = ResUtil.generateRoundBorderDrawable(dp2px(4f).toFloat(), dp2px(1f).toFloat(), getColor(R.color.pink_fa758a))
                holder.tv(R.id.tv_text).text = t
                holder.tv(R.id.tv_text).apply {
                    val padding = dp2px((5f + Math.random() * 10f).toFloat())
                    setPadding(padding, padding, padding, padding)
                }
            }

            override fun getItemLayoutId(type: Int): Int {
                return R.layout.item_text
            }
        }
    }

}