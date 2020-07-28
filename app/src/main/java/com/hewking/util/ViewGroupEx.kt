package com.hewking.util

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.LayoutRes

/**
 * Kotlin ViewGroup的扩展
 * Created by angcyo on 2017-07-26.
 */
/**
 * 计算child在parent中的位置坐标, 请确保child在parent中.
 * */
public fun ViewGroup.getLocationInParent(child: View, location: Rect) {
    var x = 0
    var y = 0

    var view = child
    while (view.parent != this) {
        x += view.left
        y += view.top
        view = view.parent as View
    }

    x += view.left
    y += view.top

    location.set(x, y, x + child.measuredWidth, y + child.measuredHeight)
}

public fun ViewGroup.findView(targetView: View /*判断需要结果的View*/, touchRawX: Float, touchRawY: Float, offsetTop: Int = 0): View? {
    /**键盘的高度*/
    var touchView: View? = targetView
    val rect = Rect()

    for (i in childCount - 1 downTo 0) {
        val childAt = getChildAt(i)

        if (childAt.visibility != View.VISIBLE) {
            continue
        }

//        childAt.getWindowVisibleDisplayFrame(rect)
//        L.e("${this}:1 ->$i $rect")
        childAt.getGlobalVisibleRect(rect)
//        L.e("${this}:2 ->$i $rect")
//        L.e("call: ------------------end -> ")
        rect.offset(0, -offsetTop)

        fun check(view: View): View? {
            if (view.visibility == View.VISIBLE &&
                    view.measuredHeight != 0 &&
                    view.measuredWidth != 0 &&
                    (view.left != view.right) &&
                    (view.top != view.bottom) &&
                    rect.contains(touchRawX.toInt(), touchRawY.toInt())) {
                return view
            }
            return null
        }

        if (childAt is ViewGroup && childAt.childCount > 0) {
            val resultView = childAt.findView(targetView, touchRawX, touchRawY, offsetTop)
            if (resultView != null && resultView != targetView) {
                touchView = resultView
                break
            } else {
                val check = check(childAt)
                if (check != null) {
                    touchView = childAt
                    break
                }
            }
        } else {
            val check = check(childAt)
            if (check != null) {
                touchView = childAt
                break
            }
        }
    }
    return touchView
}

/**将子View的数量, 重置到指定的数量*/
public fun ViewGroup.resetChildCount(newSize: Int, onAddView: () -> View) {
    val oldSize = childCount
    val count = newSize - oldSize
    if (count > 0) {
        //需要补充子View
        for (i in 0 until count) {
            addView(onAddView.invoke())
        }
    } else if (count < 0) {
        //需要移除子View
        for (i in 0 until count.abs()) {
            removeViewAt(oldSize - 1 - i)
        }
    }
}

public fun <T> ViewGroup.addView(size: Int, datas: List<T>, onAddViewCallback: OnAddViewCallback<T>) {
    this.resetChildCount(size) {
        val layoutId = onAddViewCallback.getLayoutId()
        val childView = if (layoutId > 0) {
            LayoutInflater.from(context).inflate(layoutId, this, false)
        } else onAddViewCallback.getView()!!

        onAddViewCallback.onCreateView(childView)

        childView
    }

    for (i in 0 until size) {
        onAddViewCallback.onInitView(getChildAt(i), if (i < datas.size) datas[i] else null, i)
    }
}


/**动态添加View, 并初始化 (做了性能优化)*/
public fun <T> ViewGroup.addView(datas: List<T>, onAddViewCallback: OnAddViewCallback<T>) {
    addView(datas.size, datas, onAddViewCallback)
}

/**枚举所有child view*/
public fun ViewGroup.childs(map: (Int, View) -> Unit) {
    for (i in 0 until childCount) {
        val childAt = getChildAt(i)
        map.invoke(i, childAt)
    }
}

public fun ViewGroup.show(@LayoutRes layoutId: Int): View {
    return show(layoutId, null, null)
}


public fun ViewGroup.show(@LayoutRes layoutId: Int, enterAnimation: Animation?, otherExitAnimation: Animation?): View {
    val viewWithTag = findViewWithTag<View>(layoutId)
    if (viewWithTag == null) {

        //之前的view
        val preView = if (childCount > 0) {
            getChildAt(childCount - 1)
        } else {
            null
        }

        LayoutInflater.from(context).inflate(layoutId, this)
        val newView = getChildAt(childCount - 1)
        newView.tag = layoutId

        newView?.let { view ->
            enterAnimation?.let {
                view.startAnimation(it)
            }
        }

        preView?.let { view ->
            otherExitAnimation?.let {
                view.startAnimation(it)
            }
        }

        return newView
    }
    return viewWithTag
}

public fun ViewGroup.hide(@LayoutRes layoutId: Int): View? {
    return hide(layoutId, null, null)
}

public fun ViewGroup.hide(@LayoutRes layoutId: Int, exitAnimation: Animation?, otherEnterAnimation: Animation?): View? {
    val viewWithTag = findViewWithTag<View>(layoutId)
    if (viewWithTag == null || viewWithTag.parent == null) {
    } else {

        //之前的view
        val preView = if (childCount > 1) {
            getChildAt(childCount - 2)
        } else {
            null
        }

        val parent = viewWithTag.parent
        if (parent is ViewGroup) {

            viewWithTag.let { view ->

                exitAnimation?.let {
                    it.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            parent.removeView(viewWithTag)
                        }
                    })
                    view.startAnimation(it)
                }

                if (exitAnimation == null) {
                    parent.removeView(viewWithTag)
                }
            }

            preView?.let { view ->
                otherEnterAnimation?.let {
                    view.startAnimation(it)
                }
            }
        }
    }
    return viewWithTag
}

abstract class OnAddViewCallback<T> {
    open fun getLayoutId(): Int = -1

    open fun getView(): View? = null

    /**当首次创建View时, 回调*/
    open fun onCreateView(child: View) {

    }

    open fun onInitView(view: View, data: T?, index: Int) {

    }
}