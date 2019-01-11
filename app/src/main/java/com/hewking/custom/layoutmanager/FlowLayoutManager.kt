package com.hewking.custom.layoutmanager

import android.view.View
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

        // 为什么再这里调用回收detach,因为onLayoutChildren是数据源改变会触发得操作
        // 这时候就应该作回收，对于RecyclerView初始化，再RecyclerView onMeasure,onLayout 都会分别有
        //一次的调用onLyoutChildren所以是执行两次。所以不能重复添加，可以测试一下不添加这行是怎样的？
        detachAndScrapAttachedViews(recycler)

        // init
        mFirstPos = 0
        mLastPos = itemCount - 1

        // fill layout ,方法名参考LinearLayoutManager
        fill(recycler, state)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        state ?: return

        var rowMaxLen = getHorinzontalSpace()
        var columnMaxLen = getVerticalSpace()

        var rowLen = 0
        var columnLen = 0

        for (i in mFirstPos until mLastPos + 1) {
            // 1.first find a child view
            val child = recycler.getViewForPosition(i)
            // 2. addView to RecyclerView
            addView(child)
            // 3.measure child
            measureChildWithMargins(child, 0, 0)
            // 4. calc pos and layout

            // 考虑一种情况，如果first childView 的宽度已经大于columnMaxLen，会出现这种情况吗？
            //
            if (rowLen + getDecoratedWidthWithMargin(child) > columnMaxLen) {
                rowLen = 0
                columnLen += getDecoratedHeightWithMargin(child)

                // 高度已经大于最大高度了，相当于离屏了 不可见了，这时候要回收
                if (columnLen + getDecoratedHeightWithMargin(child) > columnMaxLen) {
                    removeAndRecycleView(child, recycler)
                } else {
                    layoutDecoratedWithMargins(child, rowLen
                            , columnLen
                            , rowLen + getDecoratedWidthWithMargin(child)
                            , columnLen + getDecoratedHeightWithMargin(child))
                }
            } else {
                layoutDecoratedWithMargins(child, rowLen
                        , columnLen
                        , rowLen + getDecoratedWidthWithMargin(child)
                        , columnLen + getDecoratedHeightWithMargin(child))
                rowLen += getDecoratedWidthWithMargin(child)
            }
        }

    }

    /**
     * 需要再child被measure之后调用
     */
    private fun getDecoratedWidthWithMargin(child: View): Int {
        val params = child.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredWidth(child) + params.leftMargin + params.rightMargin
    }

    private fun getDecoratedHeightWithMargin(child: View): Int {
        val params = child.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredHeight(child) + params.topMargin + params.bottomMargin
    }

    /**
     * 需要再被layout之后调用
     */
    private fun getHorinzontalSpace(): Int {
        return width - paddingLeft - paddingRight
    }

    private fun getVerticalSpace(): Int {
        return height - paddingTop - paddingBottom
    }

}