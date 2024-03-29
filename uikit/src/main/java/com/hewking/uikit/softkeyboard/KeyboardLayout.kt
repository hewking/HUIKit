package com.hewking.uikit.softkeyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hewking.uikit.R
import com.hewking.utils.ResUtil

/**
 * 项目名称：
 * 类的描述：数字支付密码输入布局
 * 创建人员：Robi
 * 创建时间：2018/07/20 09:30
 * 修改人员：Robi
 * 修改时间：2018/07/20 09:30
 * 修改备注：
 * Version: 1.0.0
 */
class KeyboardLayout(context: Context, attributeSet: AttributeSet? = null) :
  ViewGroup(context, attributeSet), View.OnClickListener {

  /*可以决定按键的显示位置, 个数必须对*/
  var keys = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "x", "0", "-1")

  /*单独按键的高度, wrap_content的时候使用*/
  var keyViewHeight: Int = (45 * density).toInt()
  var keyTextSize = 20 * density

  var keyViewBGDrawable: Drawable? = null

  /*横竖间隙*/
  var vSpace = (1 * density).toInt()
  var hSpace = (1 * density).toInt()

  /*使用图片键盘*/
  var useImageKey = false

  /*显示小数点*/
  var showDot = false

  var onKeyboardInputListener: OnKeyboardInputListener? = null

  private var mBackgroundDrawable: Drawable? = null

  init {
    val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyboardLayout)
    keyViewHeight =
      typedArray.getDimensionPixelOffset(R.styleable.KeyboardLayout_r_key_height, keyViewHeight)
    //typedArray.getDimensionPixelOffset(R.styleable.KeyboardLayout_r_key_width, keyViewHeight)
    keyTextSize = typedArray.getDimension(R.styleable.KeyboardLayout_r_key_text_size, keyTextSize)
    useImageKey = typedArray.getBoolean(R.styleable.KeyboardLayout_r_use_image_key, useImageKey)
    showDot = typedArray.getBoolean(R.styleable.KeyboardLayout_r_show_dot, showDot)

    keyViewBGDrawable = typedArray.getDrawable(R.styleable.KeyboardLayout_r_key_background)

    hSpace = typedArray.getDimensionPixelOffset(R.styleable.KeyboardLayout_r_key_hspace, hSpace)
    vSpace = typedArray.getDimensionPixelOffset(R.styleable.KeyboardLayout_r_key_vspace, vSpace)

    if (keyViewBGDrawable == null) {
      keyViewBGDrawable = getDrawable(R.drawable.base_white_bg_selector)
    }
    mBackgroundDrawable = typedArray.getDrawable(R.styleable.KeyboardLayout_r_background)
    if (mBackgroundDrawable == null) {
      mBackgroundDrawable = ColorDrawable(getColor(R.color.base_chat_bg_color))
    }

    setWillNotDraw(false)
    typedArray.recycle()

    keys.forEach {
      val keyView: View = when (it) {
        "-1" -> {
          //删除
          imageView(R.drawable.keyboard_del, R.drawable.keyboard_del_press).apply {
            background = null
            setBackgroundColor(Color.parseColor("#E2E7ED"))
          }
        }
        "" -> {
          //占位View
          if (!showDot) {
            View(context).apply {
              setBackgroundColor(Color.parseColor("#E2E7ED"))
            }
          } else {
            //小数点
            textViewDot(".")
          }
        }
        else -> {
          createKeyView(it)
        }
      }
      if (it == "" && showDot) {
        keyView.tag = "."
      } else {
        keyView.tag = it
      }
      addView(keyView)
    }
  }

  private fun createKeyView(key: String): View {
    return if (useImageKey) {
      val keyRes = when (key) {
        "1" -> R.drawable.keyboard_1
        "2" -> R.drawable.keyboard_2
        "3" -> R.drawable.keyboard_3
        "4" -> R.drawable.keyboard_4
        "5" -> R.drawable.keyboard_5
        "6" -> R.drawable.keyboard_6
        "7" -> R.drawable.keyboard_7
        "8" -> R.drawable.keyboard_8
        "9" -> R.drawable.keyboard_9
        else -> R.drawable.keyboard_0
      }
      imageView(keyRes)
    } else {
      textView(key)
    }
  }

  private fun imageView(res: Int, pressRes: Int = -1): ImageView {
    return ImageView(context).apply {

      if (pressRes == -1) {
        setImageResource(res)
      } else {
        setImageResource(res)
        setImageDrawable(ResUtil.selector(getDrawable(res), getDrawable(pressRes)))
      }

      scaleType = ImageView.ScaleType.CENTER

      keyViewBGDrawable?.let {
        background = it.constantState?.newDrawable()
      }

      setOnClickListener(this@KeyboardLayout)
    }
  }

  private fun textView(text: String): TextView {
    return TextView(context).apply {
      gravity = Gravity.CENTER
      this.text = text
      setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)

      keyViewBGDrawable?.let {
        background = it.constantState?.newDrawable()
      }
      setTextColor(Color.BLACK)

      setOnClickListener(this@KeyboardLayout)
    }
  }

  private fun textViewDot(text: String): TextView {
    return TextView(context).apply {
      gravity = Gravity.CENTER
      this.text = text
      setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)

      setBackgroundColor(Color.parseColor("#E2E7ED"))

      setTextColor(Color.BLACK)

      setOnClickListener(this@KeyboardLayout)
    }
  }

  /*计算出来的值, 请勿设置*/
  private var childWidth = 0
  private var childHeight = 0

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    var widthSize = MeasureSpec.getSize(widthMeasureSpec)
    val widthMode = MeasureSpec.getMode(widthMeasureSpec)
    var heightSize = MeasureSpec.getSize(heightMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)

    if (widthMode != MeasureSpec.EXACTLY) {
      widthSize = resources.displayMetrics.widthPixels
    }

    if (heightMode != MeasureSpec.EXACTLY) {
      heightSize = (4 * keyViewHeight + 3 * vSpace)
    }

    childWidth = ((widthSize - 2 * hSpace - paddingLeft - paddingRight) / 3)
    childHeight = ((heightSize - 3 * vSpace - paddingTop - paddingBottom) / 4)
    childs { _, view ->
      view.measure(exactlyMeasure(childWidth), exactlyMeasure(childHeight))
    }
    setMeasuredDimension(widthSize, heightSize)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    //一行一行布局, 共4行
    for (line in 0..3) {

      val top: Int = (paddingTop + line * (childHeight + vSpace))

      //3列
      for (i in 0..2) {
        val left: Int = (paddingLeft + i * (childWidth + hSpace))

        getChildAt(line * 3 + i).layout(left, top, left + childWidth, top + childHeight)
      }
    }
  }

  override fun onClick(v: View?) {
    if (onKeyboardInputListener == null) {
      return
    }

    v?.let { view ->
      val tag = view.tag
      if (tag is String) {
        val isDel = "-1" == tag
        onKeyboardInputListener?.onKeyboardInput(tag, isDel)
      }
    }
  }

  override fun draw(canvas: Canvas) {
    mBackgroundDrawable?.bounds = canvas.clipBounds
    mBackgroundDrawable?.draw(canvas)
    super.draw(canvas)
  }

  fun setupEditText(editText: EditText) {
    onKeyboardInputListener = object : OnKeyboardInputListener {
      override fun onKeyboardInput(key: String, isDel: Boolean) {
        if (isDel) {
          val newText = editText.text.toString()
          editText.setText(newText.subSequence(0, newText.length - 1).toString())
        } else {
          val newText = editText.text.toString() + key
          editText.setText(newText)
        }
      }
    }
  }

  interface OnKeyboardInputListener {
    fun onKeyboardInput(key: String, isDel: Boolean)
  }

  val View.density: Float
    get() = resources.displayMetrics.density

  fun View.getDrawable(resId: Int): Drawable? {
    if (resId == -1) {
      return null
    }
    return ContextCompat.getDrawable(context, resId)
  }

  fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

  fun View.exactlyMeasure(size: Int): Int =
    View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)

  fun ViewGroup.childs(map: (Int, View) -> Unit) {
    for (i in 0 until childCount) {
      val childAt = getChildAt(i)
      map.invoke(i, childAt)
    }
  }

}