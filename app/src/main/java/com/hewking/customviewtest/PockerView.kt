package com.hewking.customviewtest

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.hewking.customviewtest.util.UiUtil
import java.util.*

/**
 * Created by test on 2017/12/25.
 */
class PockerView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val res: Array<Int> = arrayOf(R.drawable.pocker_0x01
            , R.drawable.pocker_0x02, R.drawable.pocker_0x03, R.drawable.pocker_0x04, R.drawable.pocker_0x05)

    private var singleWidth = UiUtil.dipToPx(context,60)

    private var singleHeight = UiUtil.dipToPx(context,80)

    // 横向间距
    private var widthSpacing = UiUtil.dipToPx(context,15)

    private var currentRes : Array<Int>? = arrayOf(R.drawable.pocker_0x01
            , R.drawable.pocker_0x02, R.drawable.pocker_0x03)

    init {
        val random = Random()
//        currentRes?.set(0,res[random.nextInt(4)])
//        currentRes?.set(1,res[random.nextInt(4)])
//        currentRes?.set(2,res[random.nextInt(4)])

//        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.PockerView)
//        typeArray.getDimension(R.styleable.PockerView_p_windth,60f)
//        typeArray.recycle()

        currentRes?.set(0,res[0])
        currentRes?.set(1,res[1])
        currentRes?.set(2,res[2])
    }

    /**
     * 如果是 wrap_content
     * 则根据每个图片的宽加上 卡片之间间距 还有padding
     *
     * 高则是 卡片高
     *
     * 如果是 exactly 就是固定宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val hmode = MeasureSpec.getMode(heightMeasureSpec)
        var width = 0
        var height = 0
        if (mode == MeasureSpec.AT_MOST) {
            width = singleWidth
            for (i in 1..currentRes?.size!! - 1 ) {
                width += widthSpacing
            }

            width += paddingLeft + paddingRight

        }else if (mode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec)
        }

        if (hmode == MeasureSpec.AT_MOST) {
            height = singleHeight
        } else if (mode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec)
        }
        height += paddingTop + paddingBottom
        setMeasuredDimension(width,height)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0..currentRes?.size!! - 1) {
            val drawable = context.getDrawable(currentRes?.get(i)!!)
            drawable.setBounds(paddingLeft + widthSpacing * i,paddingTop,paddingRight + widthSpacing * (i + 1) + singleWidth,singleHeight + paddingBottom)
            drawable.draw(canvas)
        }

    }

}