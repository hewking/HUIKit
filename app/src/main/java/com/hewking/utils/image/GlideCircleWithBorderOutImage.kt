package com.hewking.uikit.image

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.fcbox.hiveconsumer.common.R
import kt.com.fcbox.hiveconsumer.common.extension.toDp
import kt.com.fcbox.hiveconsumer.common.extension.toDpi
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * @创建者 003099
 * @创建时间 2020/7/7 15:13
 * @描述: Circle with border ， 并且使bitmap 内容在border内部
 */
class GlideCircleWithBorderOutImage(
    val borderWidth: Int = 0,
    val borderColor: Int = 0
) : BitmapTransformation() {

  private val ID: String = "kt.com.fcbox.hiveconsumer.common.utils.GlideCircleWithBorderOutImage"
  private val ID_BYTES: ByteArray = ID.toByteArray(Charset.forName("UTF-8"))


  override fun equals(o: Any?): Boolean {
    return o is GlideCircleWithBorderOutImage
  }

  override fun hashCode(): Int {
    return ID.hashCode()
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    messageDigest.update(ID_BYTES)
  }

  override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
    return toTransform.createBitmapWithBorder(borderWidth.toFloat(), borderColor)
  }

  /**
   * Create a new bordered bitmap with the specified borderSize and borderColor
   *
   * @param borderSize - The border size in pixel
   * @param borderColor - The border color
   * @return A new bordered bitmap with the specified borderSize and borderColor
   */
  private fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int): Bitmap {
    val borderOffset = (borderSize * 2).toInt()
    val halfWidth = width / 2
    val halfHeight = height / 2
    val circleRadius = Math.min(halfWidth, halfHeight).toFloat()
    val newBitmap = Bitmap.createBitmap(
        width + borderOffset,
        height + borderOffset,
        Bitmap.Config.ARGB_8888
    )

    // Center coordinates of the image
    val centerX = halfWidth + borderSize
    val centerY = halfHeight + borderSize

    val paint = Paint()
    val canvas = Canvas(newBitmap).apply {
      // Set transparent initial area
      drawARGB(0, 0, 0, 0)
    }

    // Draw the transparent initial area
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    canvas.drawCircle(centerX, centerY, circleRadius, paint)

    // Draw the image
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, borderSize, borderSize, paint)

    // Draw the createBitmapWithBorder
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = borderColor
    paint.strokeWidth = borderSize
    canvas.drawCircle(centerX, centerY, circleRadius, paint)
    return newBitmap
  }


}