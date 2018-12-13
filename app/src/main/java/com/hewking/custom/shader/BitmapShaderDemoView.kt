package com.hewking.custom.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R
import com.hewking.custom.util.dp2px

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/12 0012
 * 修改人员：hewking
 * 修改时间：2018/12/12 0012
 * 修改备注：
 * Version: 1.0.0
 */
class BitmapShaderDemoView(ctx : Context,attr: AttributeSet) : View(ctx,attr){

    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.jiantou)
            shader = BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        }
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?:return
        canvas.drawRect(0f,0f,dp2px(100f).toFloat(),dp2px(100f).toFloat(),mPaint)
    }

}