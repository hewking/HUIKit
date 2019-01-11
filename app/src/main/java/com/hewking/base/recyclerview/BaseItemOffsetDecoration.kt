package com.hewking.base.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/4 0004
 * 修改人员：hewking
 * 修改时间：2019/1/4 0004
 * 修改备注：
 * Version: 1.0.0
 */
abstract class BaseItemOffsetDecoration : RecyclerView.ItemDecoration() {

    private val TAG = "BaseItemOffsetDecoration"

    protected abstract fun applyOffset(): Rect

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        val params = view.layoutParams as RecyclerView.LayoutParams
        val layoutPosition = params.viewLayoutPosition

        val targetRect = applyOffset()

        if (layoutManager is GridLayoutManager) {
            if (layoutManager.orientation == GridLayoutManager.VERTICAL) {
                outRect.left = targetRect.left.div(2)
                outRect.right = targetRect.right.div(2)
                outRect.top = targetRect.top.div(2)
                outRect.bottom = targetRect.bottom.div(2)

                if (isStartOfItem(layoutPosition, layoutManager.itemCount, layoutManager.spanCount)) {
                    outRect.left = targetRect.left
                }

                if (isEndOfItem(layoutPosition, layoutManager.itemCount, layoutManager.spanCount)) {
                    outRect.right = targetRect.right
                }

                if (isTopOfItem(layoutPosition, layoutManager.itemCount, layoutManager.spanCount)) {
                    outRect.top = targetRect.top
                }

                if (isBottomOfItem(layoutPosition, layoutManager.itemCount, layoutManager.spanCount)) {
                    outRect.bottom = targetRect.bottom
                }
            } else {
                // 横着的方向

            }
        } else if (layoutManager is LinearLayoutManager) {

        }

    }

    /**
     * 是否是最后一排(是否靠最底部)
     */
    private fun isBottomOfItem(layoutPosition: Int, itemCount: Int, spanCount: Int): Boolean {
        val ceil = Math.ceil(itemCount.div(spanCount.toDouble()))
        return layoutPosition >= ceil * spanCount - spanCount
    }

    /**
     * 是否是第一排(靠近顶部)
     */
    private fun isTopOfItem(layoutPosition: Int, itemCount: Int, spanCount: Int): Boolean {
        return layoutPosition <= spanCount - 1
    }

    /**
     * 是否是中间
     */
    private fun isMiddleOfItem(layoutPosition: Int, itemCount: Int, spanCount: Int): Boolean {
        val rem = layoutPosition.rem(spanCount)
        return rem > 0 && rem < spanCount - 1
    }

    /**
     * 是否是第一行
     */
    private fun isStartOfItem(layoutPosition: Int, itemCount: Int, spanCount: Int): Boolean {
//        L.d(TAG,"isStartOfItem layoutPos :$layoutPosition itemCount : $itemCount sc : $spanCount")
        return layoutPosition.rem(spanCount) == 0
    }

    /**
     * 是否是最后一行(是否靠最右边)
     */
    private fun isEndOfItem(layoutPosition: Int, itemCount: Int, spanCount: Int): Boolean {
        return layoutPosition.rem(spanCount) == spanCount - 1 || layoutPosition == itemCount - 1
    }

}