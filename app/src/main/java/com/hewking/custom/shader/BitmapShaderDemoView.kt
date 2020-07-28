package com.hewking.custom.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R
import com.hewking.util.L
import com.hewking.util.dp2px

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
            style = Paint.Style.FILL
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.asm_logo)
            val bs = BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT)
            val matrix = Matrix()
            matrix.setScale(0.1f,0.1f)
            bs.setLocalMatrix(matrix)
            shader = bs

        }
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        L.d("BitmapShaderDemoView", "wsize : $wSize  hSize : $hSize  wMode :$wMode hMode :$hMode")
        if (wMode != MeasureSpec.EXACTLY) {
            wSize = dp2px(200f)
        }

        if (hMode != MeasureSpec.EXACTLY) {
            hSize = dp2px(200f)
        }

        setMeasuredDimension(wSize,hSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?:return
        canvas.translate(width.div(2f),height.div(2f))
        canvas.drawCircle(0f,0f,width.div(2f),mPaint)
    }

}