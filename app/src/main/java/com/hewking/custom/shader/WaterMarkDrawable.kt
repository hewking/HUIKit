package com.hewking.custom.shader

import android.graphics.*
import android.graphics.drawable.Drawable
import com.hewking.util.textHeight

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
class WaterMarkDrawable : Drawable() {

    private var waterMark = "ho ho ho!"

    private val paint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    init {
        val bs = BitmapShader(makeMark(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        val m = Matrix()
        m.postRotate(45f)
        bs.setLocalMatrix(m)
        paint.shader = bs
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(bounds, paint)
    }

    override fun setAlpha(alpha: Int) {
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    fun makeMark(): Bitmap {
        val p = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#f5b8c2")
            textSize = 30f
        }
        val w = p.measureText(waterMark) + 20
        val bitmap = Bitmap.createBitmap(w.toInt(), 40, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.save()
        canvas.translate(10f, 5f)
        canvas.drawText(waterMark, 0f, p.textHeight() - p.descent(), p)
        canvas.restore()
        return bitmap
    }

}