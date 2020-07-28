package com.hewking.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.hewking.utils.dp2px
import com.hewking.utils.textHeight

/**
 * 类的描述：跟apidemos 里面的 Xfermodes 结果不一样，原因在于paint.darw() 不是paint.drawBitmap 导致。
 * 创建人员：hewking
 * 创建时间：2018/12/22
 * 修改人员：hewking
 * 修改时间：2018/12/22
 * 修改备注：
 * Version: 1.0.0
 */

class XfermodeSampleView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

    private var rectSize = dp2px(100f)

    private val modes = arrayOf(PorterDuff.Mode.CLEAR
            , PorterDuff.Mode.SRC
            , PorterDuff.Mode.DST
            , PorterDuff.Mode.SRC_OVER
            , PorterDuff.Mode.DST_OVER
            , PorterDuff.Mode.SRC_IN
            , PorterDuff.Mode.DST_IN
            , PorterDuff.Mode.SRC_OUT
            , PorterDuff.Mode.DST_OUT
            , PorterDuff.Mode.SRC_ATOP
            , PorterDuff.Mode.DST_ATOP
            , PorterDuff.Mode.XOR
            , PorterDuff.Mode.DARKEN
            , PorterDuff.Mode.LIGHTEN
            , PorterDuff.Mode.MULTIPLY
            , PorterDuff.Mode.SCREEN
            , PorterDuff.Mode.ADD
            , PorterDuff.Mode.OVERLAY)

    private val mBG: Shader     // background checker-board pattern

        private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = dp2px(12f).toFloat()
            color = ContextCompat.getColor(ctx, R.color.colorPrimary)
            style = Paint.Style.STROKE
        }
    }

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(ctx, R.color.app_color_blue_2_pressed)
            style = Paint.Style.FILL
        }
    }

    init {
        // make a ckeckerboard pattern
        val bm = Bitmap.createBitmap(intArrayOf(-0x1, -0x333334, -0x333334, -0x1), 2, 2,
                Bitmap.Config.RGB_565)
        mBG = BitmapShader(bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT)

        val m = Matrix()
        m.setScale(6f, 6f)
        mBG.setLocalMatrix(m)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        if (wMode == MeasureSpec.AT_MOST) {
            wSize = rectSize * 4
        }

        if (wMode == MeasureSpec.EXACTLY) {
            rectSize = Math.max(wSize, hSize).div(4)
        }

        if (hMode == MeasureSpec.AT_MOST) {
            hSize = ((rectSize + textPaint.textHeight()) * 5).toInt()
        }

        setMeasuredDimension(wSize, hSize)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        canvas.drawColor(Color.WHITE)
        val radius = rectSize.div(3f)

        var posX = 0
        var posY = 0

        paint.isFilterBitmap = false
//        paint.setShader(mBG)

        for (i in 0 until modes.size) {

            posX = i.rem(4)
            posY = i.div(4)

            val textX = posX.times(rectSize)
            val textY = posY.times(rectSize) + textPaint.textHeight()

            val cx = radius + posX.times(rectSize)
            val cy = radius + posY.times(rectSize) + textPaint.textHeight()

            /*canvas.drawRect(textX.toFloat(),rectSize * posY.toFloat() + textPaint.textHeight()
                    ,rectSize * (posX + 1).toFloat(),rectSize * (posY + 1) + textPaint.textHeight(),paint)*/

            canvas.drawText(modes[i].name, textX.toFloat(), textY, textPaint)

//            paint.style = Paint.Style.FILL_AND_STROKE

            val sc = canvas.saveLayer(posX.toFloat() * rectSize
                    , posY.toFloat() * rectSize
                    , (posX.toFloat() + 1) * rectSize, (posY.toFloat() + 1) * rectSize, null,
                    Canvas.ALL_SAVE_FLAG )
            // draw dst
            paint.color = ContextCompat.getColor(context, R.color.app_color_blue_2_pressed)
            canvas.drawCircle(cx, cy, radius, paint)

            paint.xfermode = PorterDuffXfermode(modes[i])
            paint.color = ContextCompat.getColor(context, R.color.app_color_theme_3)
            // draw src
            canvas.drawRect(cx, cy
                    , cx + radius.times(2), cy + radius.times(2), paint)

            paint.xfermode = null
            canvas.restoreToCount(sc)
        }

    }

}