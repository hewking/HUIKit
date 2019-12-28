package com.hewking.custom.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/7 0007
 * 修改人员：hewking
 * 修改时间：2019/1/7 0007
 * 修改备注：
 * Version: 1.0.0
 */
object DrawHelper {

    /**
     * 在宽高为 w,h 的平面内，绘制坐标系，原点居中
     */
    fun drawCoordinate(canvas: Canvas, w: Int, h: Int) {
        canvas.save()
        canvas.translate(w.div(2f), h.div(2f))
        val paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#666666")
            textSize = 12f
        }
        // 绘制x,y 坐标轴
        canvas.drawLine(-w.div(2f), 0f, w.div(2f), 0f, paint)
        canvas.drawLine(0f, -h.div(2f), 0f, h.div(2f), paint)

        val step = 20
        val scaleHeight = 5f
        // 绘制x,y坐标轴上的刻度
        for (posX in 0..w.div(2) step step) {
            if (posX == 0) {
                continue
            }
            canvas.drawLine(posX.toFloat(), 0f, posX.toFloat(), -scaleHeight, paint)
            canvas.drawLine(-posX.toFloat(), 0f, -posX.toFloat(), -scaleHeight, paint)

            canvas.drawText(posX.div(step).toString()
                    , posX - paint.textWidth(posX.div(step).toString()).div(2f)
                    , scaleHeight + paint.textHeight() - paint.descent()
                    , paint)

            canvas.drawText((-posX.div(step)).toString()
                    , -posX - paint.textWidth((-posX.div(step)).toString()).div(2f)
                    , scaleHeight + paint.textHeight() - paint.descent()
                    , paint)
        }

        for (posY in 0..h.div(2) step step) {
            if (posY == 0) {
                continue
            }
            canvas.drawLine(0f, posY.toFloat(), scaleHeight, posY.toFloat(), paint)
            canvas.drawLine(0f, -posY.toFloat(), scaleHeight, -posY.toFloat(), paint)

            canvas.drawText(posY.div(step).toString()
                    , -scaleHeight - paint.textWidth(posY.div(step).toString())
                    , posY + (paint.textHeight()).div(2f) - paint.descent(), paint)

            canvas.drawText((-posY.div(step)).toString()
                    , -scaleHeight - paint.textWidth((-posY.div(step)).toString())
                    , -posY + (paint.textHeight()).div(2f) - paint.descent(), paint)
        }

        // 绘制方向箭头
        val angle = 30 * Math.PI / 180
        canvas.drawLine(w.div(2f), 0f, w.div(2f) - step * Math.cos(angle).toFloat(), step * Math.sin(angle).toFloat(), paint)
        canvas.drawLine(w.div(2f), 0f, w.div(2f) - step * Math.cos(angle).toFloat(), -step * Math.sin(angle).toFloat(), paint)

        canvas.drawLine(0f, h.div(2f), -step * Math.sin(angle).toFloat(), h.div(2f) - step * Math.cos(angle).toFloat(), paint)
        canvas.drawLine(0f, h.div(2f), step * Math.sin(angle).toFloat(), h.div(2f) - step * Math.cos(angle).toFloat(), paint)

        canvas.restore()
    }

    fun drawCoordinate(canvas: Canvas, rect: Rect) {
        drawCoordinate(canvas, rect.width(), rect.height())
    }

}