package com.hewking.custom.layoutmanager

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
class FlowLayoutManager : RecyclerView.LayoutManager() {

    private var mFirstPos = -1
    private var mLastPos = -1
    private var mMaxLineWidth = -1

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        if (itemCount == 0) {
            return
        }

        if (itemCount == 0 && state?.isPreLayout == true) {
            return
        }

        detachAndScrapAttachedViews(recycler)

        // init
        mFirstPos = 0
        mLastPos = itemCount - 1

        // fill layout
        fillChildren(recycler, state)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun fillChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        state ?: return

//        measureChildWithMargins()

    }

}