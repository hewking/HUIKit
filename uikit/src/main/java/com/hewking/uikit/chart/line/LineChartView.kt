package com.hewking.uikit.chart.line

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.ColorUtils
import com.hewking.uikit.BuildConfig
import com.hewking.utils.DrawHelper
import com.hewking.utils.toDp
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * @author: jianhao
 * @create: 2020/10/19
 * @description:
 */
class LineChartView(val ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

  private var mValues: MutableList<Float> = mutableListOf()
    set(value) {
      field = value
      invalidate()
    }

  private val mPathPaint by lazy {
    Paint().apply {
      isAntiAlias = true
      style = Paint.Style.STROKE
      strokeWidth = 1.toDp()
      color = Color.BLUE
    }
  }

  private val mPath by lazy {
    Path()
  }

  private val mShaderPaint by lazy {
    Paint().apply {
      isAntiAlias = true
      style = Paint.Style.FILL
      strokeWidth = 1.toDp()
      color = ColorUtils.setAlphaComponent(Color.BLUE,0x80)
    }
  }

  private val mShaderPath by lazy {
    Path()
  }

  init {
    if (BuildConfig.DEBUG) {
      for (i in 0 until 15) {
        mValues.add(Random.nextFloat().times(20))
      }
    }

  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas ?: return

    /**
     * mDatas 只传递数据，比如 10，20 40，15，30 然后获取这个数组中的
     * 最大值和最小值 max,min.  绘制在图标中一个点需要 x,y 两个值，
     * x 容易确定，通过width.div(mDatas.size) 即可，这里需要重点关注
     * y 值得获取，(value - min).div(max - min).times(height)
     */
    if (mValues.isEmpty()) return
    // 翻转坐标轴
//    canvas.scale(1f, -1f, width.div(2f), height.div(2f))

    val min = mValues.reduce { acc, fl -> min(acc, fl) }
    val max = mValues.reduce { acc, fl -> max(acc, fl) }
    val xInterval = width.div(mValues.size).toFloat()
    val diff = max - min

//    mShaderPath.moveTo(0f,height.toFloat())
    for (i in 0 until mValues.size) {
      val x = xInterval.times(i)
      val y = height - mValues[i].div(diff).times(height)
      if (i == 0) {
        mPath.moveTo(x, y)
        mShaderPath.moveTo(x,y)
      } else {
        mPath.lineTo(x, y)
        mShaderPath.lineTo(x,y)
        if (i == mValues.lastIndex) {
          mShaderPath.lineTo(x, height.toFloat())
          mShaderPath.lineTo(0f,height.toFloat())
          mShaderPath.close()
        }
      }
    }
    mShaderPaint.shader = LinearGradient(0f, 0f, 0f, height.toFloat(), intArrayOf(Color.BLUE, Color.WHITE), null, Shader.TileMode.CLAMP)

    canvas.drawPath(mShaderPath,mShaderPaint)
    canvas.drawPath(mPath, mPathPaint)
    DrawHelper.drawCoordinate(canvas, width, height)


  }

}