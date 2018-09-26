package com.hewking.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/8/24 16:03
 * 修改人员：hewking
 * 修改时间：2018/8/24 16:03
 * 修改备注：
 * Version: 1.0.0
 */
class MaskLinearLayout(ctx: Context, attrs: AttributeSet) : LinearLayout(ctx, attrs) {

    private var showMask = false
    private var mask: Drawable? = null
        set(value) {
            field = value
            update()
        }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs,R.styleable.MaskLinearLayout)

        showMask = typeArray.getBoolean(R.styleable.MaskLinearLayout_mask_showmask,false)
        mask = typeArray.getDrawable(R.styleable.MaskLinearLayout_mask_drawable)

        typeArray.recycle()
    }

    private fun update() {
        requestLayout()
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)

        if (showMask) {
            mask?.setBounds(0, 0, width, height)
            mask?.draw(canvas)
        }
    }

}