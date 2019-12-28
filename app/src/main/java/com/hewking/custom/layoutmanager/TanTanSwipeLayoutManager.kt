package com.hewking.custom.layoutmanager

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/27 0027
 * 修改人员：hewking
 * 修改时间：2018/12/27 0027
 * 修改备注：
 * Version: 1.0.0
 */
class TanTanSwipeLayoutManager : RecyclerView.LayoutManager()
        , ItemTouchHelper.ViewDropHandler {

    override fun prepareForDrop(view: View, target: View, x: Int, y: Int) {

    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return LayoutParams(-2, -2)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
    }

    class LayoutParams(width: Int, height: Int) : RecyclerView.LayoutParams(width, height) {

    }

    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollVerticallyBy(dy, recycler, state)
    }

}