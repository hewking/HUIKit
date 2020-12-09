package com.hewking.uikit.chart.line

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.hewking.utils.DrawHelper
import com.hewking.utils.toDp
import java.util.*
import java.util.Collections.fill
import java.util.Collections.max


class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

  //当前View的高
  private var mViewHeight: Int = 0

  //当前View的宽
  private var mViewWidth: Int = 0

  //绘制折线的画笔
  private var mPaintLine: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

  // 绘制折线上的点的画笔
  private var mPaintLinePoint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

  //折线小圆点的颜色
  var brokenLinePointColor = Color.parseColor("#00EE00")
    set(value) {
      field = value
      mPaintLinePoint.color = brokenLinePointColor
      postInvalidate()
    }

  //点的半径
  var brokenLinePointRadius = 6f
    set(value) {
      field = value
      postInvalidate()
    }

  //绘制覆盖区域
  private var mPaintFillArea: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private lateinit var pointList: ArrayList<Point>

  //x，y坐标轴每多少一个刻度
  private var xScale = 2f
  private var yScale = 10f
  private var everyXwidth: Float = 0.0f
  private var everyYheight: Float = 0.0f

  //传入点的X的最大坐标
  private var maxX: Int = 0

  //传入点的Y的最大坐标
  var maxY: Int = -1
    set(value) {
      field = value
      invalidate()
    }

  //折线的颜色
  var brokenLineColor = Color.WHITE
    set(value) {
      field = value
      mPaintLine.color = value
      postInvalidate()
    }

  var fillColor = Color.WHITE
    set(value) {
      field = value
      mPaintFillArea.color = value
      postInvalidate()
    }

  //折线的宽度
  var brokenLineSize = 2f.toInt().toDp()
    set(value) {
      field = value
      postInvalidate()
    }

  //折线图距离四周的像素大小
  var margin = 0
    set(value) {
      field = value
      postInvalidate()
    }


  //是否填充
  var fillArea = true
    set(value) {
      field = value
      invalidate()
    }

  private lateinit var mShader: LinearGradient

  private val fillPath = Path()
  private val path = Path()
  private val zeroPoint = Point()


  init {
    initPaint()
  }


  //初始化画笔
  private fun initPaint() {
    //虚线需要关闭硬件加速
    setLayerType(LAYER_TYPE_SOFTWARE, null)

    mPaintFillArea.color = brokenLineColor
    mPaintFillArea.style = Paint.Style.FILL
//    mPaintFillArea.style = Paint.Style.STROKE

    mPaintLine.style = Paint.Style.STROKE
    mPaintLine.strokeWidth = brokenLineSize
    mPaintLine.color = brokenLineColor
    mPaintLine.strokeCap = Paint.Cap.ROUND
    mPaintLine.strokeJoin = Paint.Join.ROUND

    mPaintLinePoint.style = Paint.Style.FILL
    mPaintLinePoint.color = brokenLinePointColor

  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }


  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    //获取当前View的宽高
    mViewWidth = w
    mViewHeight = h
    //渐变
    mShader = LinearGradient(0f, 0f, 0f, h.toFloat(), intArrayOf(brokenLineColor, Color.WHITE), null, Shader.TileMode.CLAMP)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    getWidthAndHeight()
    drawCurve(canvas)
    //  设置动画
    setAnim(canvas)

    DrawHelper.drawCoordinate(canvas, mViewWidth, mViewHeight.toInt())
  }

  private fun getWidthAndHeight() {
    //x轴上需要绘制的刻度的个数
    val numX = maxX / xScale
    //每格的宽度
    everyXwidth = (mViewWidth - margin * 2) / (numX - 1f)
    //y轴上需要绘制的刻度的个数
    val numY = maxY / yScale
    //每格的高度
    everyYheight = (mViewHeight - margin * 2) / (numY - 1f)
  }

  /**
   * 绘制曲线
   */
  private fun drawCurve(canvas: Canvas) {
    path.reset()
    fillPath.reset()
    zeroPoint.x = (margin + pointList[0].x / (xScale * 1f) * everyXwidth).toInt()
    zeroPoint.y = (mViewHeight - margin - pointList[0].y / (yScale * 1f) * everyYheight).toInt()
    if (fillArea) {
      //移动到原点
      fillPath.moveTo(margin.toFloat(), (mViewHeight - margin).toFloat())
      fillPath.lineTo(zeroPoint.x.toFloat(), zeroPoint.y.toFloat())
    }
    path.moveTo(zeroPoint.x.toFloat(), zeroPoint.y.toFloat())
    for (i in 0 until pointList.size - 1) {
      val startX = pointList[i].x / (xScale * 1f) * everyXwidth
      val startY = pointList[i].y / (yScale * 1f) * everyYheight
      val startPx = margin + startX
      val startPy = mViewHeight.toFloat() - margin.toFloat() - startY
      val endX = pointList[i + 1].x / (xScale * 1f) * everyXwidth
      val endY = pointList[i + 1].y / (yScale * 1f) * everyYheight
      val endPx = margin + endX
      val endPy = mViewHeight.toFloat() - margin.toFloat() - endY
      val wt = (startPx + endPx) / 2
      val p3 = Point()
      val p4 = Point()
      p3.y = startPy.toInt()
      p3.x = wt.toInt()
      p4.y = endPy.toInt()
      p4.x = wt.toInt()
      path.cubicTo(p3.x.toFloat(), p3.y.toFloat(), p4.x.toFloat(), p4.y.toFloat(), endPx, endPy)
      if (fillArea) {
        fillPath.cubicTo(p3.x.toFloat(), p3.y.toFloat(), p4.x.toFloat(), p4.y.toFloat(), endPx, endPy)
        if (i == pointList.size - 2) {
          fillPath.lineTo(endPx, mViewHeight.toFloat() - margin.toFloat())
          fillPath.close()
          mPaintFillArea.shader = mShader
        }
      }
//      canvas.drawPath(path, mPaintLine)
      //这里绘制两次是为了把线盖住，效果更好
      if (i != 1) {
        canvas.drawCircle(startPx, startPy, brokenLinePointRadius, mPaintLinePoint)
      }
      canvas.drawCircle(endPx, endPy, brokenLinePointRadius, mPaintLinePoint)
    }
  }

  fun setChartPoints(points: ArrayList<Int>) {
    pointList = ArrayList()
    points.forEachIndexed { index, value ->
      val point = Point()
      point.x = index
      point.y = value
      pointList.add(point)
    }
    val yPointArray = arrayListOf<Int>()

    for (i in pointList.indices) {
      yPointArray.add(pointList[i].y)
    }
    if (maxY == -1) {
      maxY = max(yPointArray)
    }
    maxX = points.size
    //默认x有10格，y5格，这里可以修改
    xScale = 1f
    yScale = 1f
    initAnimator(maxX)
    invalidate()
  }

  private lateinit var valueAnimator: ValueAnimator

  //  private var mAnimatorValue: Int = 0
  private lateinit var mUpdateListener: ValueAnimator.AnimatorUpdateListener
  private val defaultDuration = 600L
  private var mProgress = 0f
  private var interpolator: Interpolator? = null

  private fun initAnimator(maxX: Int) {
    //  初始化坐标轴画笔
    interpolator = LinearInterpolator()
//    valueAnimator = ValueAnimator.ofInt(0, maxX).setDuration(defaultDuration.toLong())
//    mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
//      mAnimatorValue = animation.animatedValue as Int
//      invalidate()
//    }
//    valueAnimator.addUpdateListener(mUpdateListener)
//    valueAnimator.start()
    startAnim(defaultDuration)
  }

  //  属性动画的set方法
  fun setPercentage(percentage: Float) {
    require(!(percentage < 0.0f || percentage > 1.0f)) { "setPercentage not between 0.0f and 1.0f" }
    mProgress = percentage
    invalidate()
  }

  private fun setAnim(canvas: Canvas) {
    val measure = PathMeasure(path, false)
    val pathLength = measure.length
    val effect: PathEffect = DashPathEffect(floatArrayOf(pathLength,
        pathLength), pathLength - pathLength * mProgress)

    Log.d("setAnim", "progress: $mProgress")

    mPaintLine.pathEffect = effect
    canvas.drawPath(path, mPaintLine)

    val coords = FloatArray(2)
    measure.getPosTan(pathLength * mProgress, coords, null)

    canvas.save()
    canvas.clipRect(margin.toFloat(), 0f, coords[0], mViewHeight.toFloat())
    canvas.drawPath(fillPath, mPaintFillArea)
    canvas.restore()

  }

  /**
   * @param duration 动画持续时间
   */
  fun startAnim(duration: Long) {
    val anim = ObjectAnimator.ofFloat(this, "percentage", 0.0f, 1.0f)
    anim.duration = duration
    anim.interpolator = interpolator
    anim.start()
  }

}
