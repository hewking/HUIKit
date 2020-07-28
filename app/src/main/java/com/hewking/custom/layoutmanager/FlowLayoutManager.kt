package com.hewking.custom.layoutmanager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hewking.utils.L

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

    private val TAG = "FlowLayoutManager"

    private var mFirstPos = -1
    private var mLastPos = -1
    private var mVerticalOffset = 0

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
        L.d(TAG, "dy : $dy ")
        // 不符合滑动条件不滑动
        if (dy == 0 || childCount == 0) {
            return 0
        }

        // 往下滑动为- 往上为+
        var realOffset = dy

        val absDy = Math.abs(dy)

        // 往上滑动
        if (mVerticalOffset + dy < 0) {
            realOffset = -mVerticalOffset
        } else if (realOffset > 0) {
            val lastChild = recycler!!.getViewForPosition(childCount - 1)
            L.d(TAG, "childCount -1 : ${childCount - 1} pos : ${getPosition(lastChild)} itemCount -1 ${itemCount - 1}")
            if (getPosition(lastChild) == itemCount - 1) {
                // 说明是向上滑动过偏移的
                val yOffset = height - paddingBottom - getDecoratedBottom(lastChild)
                if (yOffset > 0) {
                    realOffset = -yOffset
                } else if (yOffset == 0) {
                    realOffset = 0
                } else {
                    realOffset = Math.min(realOffset, -yOffset)
                }
            }
        }

        offsetChildrenVertical(-realOffset)
        L.d(TAG, "realOffset : ${-realOffset} ")
        mVerticalOffset += realOffset
        return realOffset
    }

    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        state ?: return

        var rowMaxLen = getHorinzontalSpace()
        var columnMaxLen = getVerticalSpace()

        // 每行的最大行高，因为每行的某个itemView 可能高度不一致，那么每行
        //行高取决于最大itemVie的高度
        var columnMaxLineLen = 0

        var rowLen = 0
        var columnLen = 0

        L.d(TAG, "mLastPos $mLastPos")

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
            val decoratedWidthWithMargin = getDecoratedWidthWithMargin(child)
            val decoratedHeightWithMargin = getDecoratedHeightWithMargin(child)
            if (rowLen + decoratedWidthWithMargin > rowMaxLen) {
                rowLen = 0
                columnLen += columnMaxLineLen
                columnMaxLineLen = decoratedHeightWithMargin

                // 高度已经大于最大高度了，相当于离屏了 不可见了，这时候要回收
                if (columnLen + decoratedHeightWithMargin > columnMaxLen) {
                    removeAndRecycleView(child, recycler)
                } else {
                    layoutDecoratedWithMargins(child, rowLen
                            , columnLen
                            , rowLen + decoratedWidthWithMargin
                            , columnLen + decoratedHeightWithMargin)
                    rowLen += decoratedWidthWithMargin
                }
            } else {
                layoutDecoratedWithMargins(child, rowLen
                        , columnLen
                        , rowLen + decoratedWidthWithMargin
                        , columnLen + decoratedHeightWithMargin)
                rowLen += decoratedWidthWithMargin
                // 最大值child高度决定行高
                columnMaxLineLen = Math.max(decoratedHeightWithMargin, columnMaxLineLen)
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