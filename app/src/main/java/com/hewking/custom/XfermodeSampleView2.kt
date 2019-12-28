package com.hewking.custom

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.hewking.custom.util.textHeight
import com.hewking.demo.dp2px

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/22
 * 修改人员：hewking
 * 修改时间：2018/12/22
 * 修改备注：
 * Version: 1.0.0
 */

class XfermodeSampleView2(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

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

        var posX = 0
        var posY = 0

        paint.isFilterBitmap = false

        for (i in 0 until modes.size) {

            posX = i.rem(4)
            posY = i.div(4)

            val x = posX.times(rectSize).toFloat()
            val y = posY.times(rectSize + textPaint.textHeight())

            paint.setShader(mBG)
            // draw bg
            canvas.drawRect(x,y, x + rectSize.toFloat() - 25,y + rectSize.toFloat(),paint)
            paint.setShader(null)

            canvas.save()

//            val sc = canvas.saveLayer(x
//                    , y
//                    ,x + rectSize.toFloat(), y + rectSize + textPaint.textHeight(), null,
//                    Canvas.ALL_SAVE_FLAG )
            canvas.translate(x,y)
            val sc = canvas.saveLayer(0f
                    , 0f
                    ,rectSize.toFloat(), rectSize + textPaint.textHeight(), null,
                    Canvas.ALL_SAVE_FLAG )
            canvas.drawText(modes[i].name, 0f, textPaint.textHeight(), textPaint)

            canvas.translate(0f,textPaint.textHeight())

            // draw dst
            canvas.drawBitmap(makeDst()
            ,0f,0f,paint)

            paint.xfermode = PorterDuffXfermode(modes[i])
            // draw src

            canvas.drawBitmap(makeSrc(),0f,0f,paint)
            paint.xfermode = null
            canvas.restoreToCount(sc)
            canvas.restore()
        }

    }

    fun makeSrc() : Bitmap{
        val radius = rectSize.div(3f)
        val bitmap = Bitmap.createBitmap(rectSize
                ,rectSize,Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        val p = Paint().apply {
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.app_color_theme_3)
        }
        c.drawRect(radius,radius,rectSize.times(0.75f),rectSize.times(0.75f),p)
        return bitmap
    }

    fun makeDst() : Bitmap{
        val radius = rectSize.div(3f)
        val bitmap = Bitmap.createBitmap(radius.times(2).toInt()
                ,radius.times(2).toInt(),Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        val p = Paint().apply {
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.app_color_blue_2_pressed)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            c.drawOval(0f,0f,radius.times(2),radius.times(2),p)
        }
        return bitmap
    }

}