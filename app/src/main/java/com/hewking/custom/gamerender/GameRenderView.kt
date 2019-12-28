package com.hewking.custom.gamerender

import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.hewking.custom.R

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Created by test on 2017/12/27.
 */
class GameRenderView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var poker = context.getDrawable(R.drawable.pocker_0x01)

    private val mFrameAnimator = ValueAnimator.ofInt(0, 60).apply {
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        duration = 1000
        addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val frame = animation?.animatedValue as Int
                // 一帧
                val top = frame * 10
                poker.setBounds(paddingLeft + 100,top,paddingLeft + 100 + poker.intrinsicWidth ,top + poker.intrinsicHeight)
                postInvalidateOnAnimation()
            }
        })

//        addListener(
//
//        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mFrameAnimator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        mFrameAnimator.st
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        poker.draw(canvas)
    }

}