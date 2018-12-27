package com.hewking.custom.layoutmanager

import android.graphics.PointF
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
        , ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {
    override fun prepareForDrop(view: View, target: View, x: Int, y: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}