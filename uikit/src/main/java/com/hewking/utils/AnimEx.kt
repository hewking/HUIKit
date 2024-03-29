package com.hewking.utils

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.*
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import kotlin.math.hypot

/**
 *@Description: Animator DSL
 *@Author: jianhao
 *@Date:   2021-07-19 17:25
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

/**动画默认时长*/
const val ANIM_DURATION = 300L

/**从指定资源id中, 加载动画[Animation]*/
fun animationOf(context: Context, @AnimRes id: Int): Animation? {
  try {
    if (id == 0 || id == -1) {
      return null
    }
    return AnimationUtils.loadAnimation(context, id)
  } catch (e: Exception) {
    e.printStackTrace()
    return null
  }
}

/**从指定资源id中, 加载动画[Animator]*/
fun animatorOf(context: Context, @AnimatorRes id: Int): Animator? {
  try {
    if (id == 0 || id == -1) {
      return null
    }
    return AnimatorInflater.loadAnimator(context, id)
  } catch (e: Exception) {
    e.printStackTrace()
    return null
  }
}

fun anim(from: Int, to: Int, config: AnimatorConfig.() -> Unit = {}): ValueAnimator {
  return animatorInner(ValueAnimator.ofInt(from, to), config)
}

fun anim(from: Float, to: Float, config: AnimatorConfig.() -> Unit = {}): ValueAnimator {
  return animatorInner(ValueAnimator.ofFloat(from, to), config)
}

private fun animatorInner(
  animator: ValueAnimator,
  config: AnimatorConfig.() -> Unit = {}
): ValueAnimator {
  val animatorConfig = AnimatorConfig()

  animator.duration = ANIM_DURATION
  animator.interpolator = LinearInterpolator()
  animator.addUpdateListener {
    animatorConfig.onAnimatorUpdateValue(it.animatedValue, it.animatedFraction)
  }
  animator.addListener(DefaultAnimatorListener().apply {
    onAnimatorFinish = { _, _ ->
      animatorConfig.onAnimatorEnd(animator)
    }
  })

  animatorConfig.config()
  animatorConfig.onAnimatorConfig(animator)

  animator.start()
  return animator
}

open class DefaultAnimationListener : Animation.AnimationListener {
  override fun onAnimationRepeat(animation: Animation) {
  }

  override fun onAnimationEnd(animation: Animation) {
  }

  override fun onAnimationStart(animation: Animation) {
  }
}

open class DefaultAnimatorListener : Animator.AnimatorListener {

  private var isCancel = false

  var onAnimatorFinish: (animator: Animator, fromCancel: Boolean) -> Unit =
    { animator, fromCancel ->
      _onAnimatorFinish(animator, fromCancel)
    }

  override fun onAnimationRepeat(animation: Animator) {}
  override fun onAnimationEnd(animation: Animator) {
    if (isCancel) {
      //当动画被取消的时候, 系统会回调onAnimationCancel, 然后 onAnimationEnd
      //所以, 这里过滤一下
    } else {
      onAnimatorFinish(animation, false)
    }
  }

  override fun onAnimationCancel(animation: Animator) {
    isCancel = true
    onAnimatorFinish(animation, true)
  }

  override fun onAnimationStart(animation: Animator) {
    isCancel = false
  }

  open fun _onAnimatorFinish(animator: Animator, fromCancel: Boolean) {

  }

}

class AnimatorConfig {
  var onAnimatorConfig: (animator: ValueAnimator) -> Unit = {}

  var onAnimatorUpdateValue: (value: Any, fraction: Float) -> Unit = { _, _ -> }

  var onAnimatorEnd: (animator: ValueAnimator) -> Unit = {}
}

/**缩放属性动画*/
fun View.scale(
  from: Float,
  to: Float,
  duration: Long = ANIM_DURATION,
  interpolator: Interpolator = LinearInterpolator(),
  onEnd: () -> Unit = {}
): ValueAnimator {
  return anim(from, to) {
    onAnimatorUpdateValue = { value, _ ->
      scaleX = value as Float
      scaleY = scaleX
    }

    onAnimatorConfig = {
      it.duration = duration
      it.interpolator = interpolator
      onAnimatorEnd = { _ -> onEnd() }
    }
  }
}

/**平移属性动画*/
fun View.translationX(
  from: Float,
  to: Float,
  duration: Long = ANIM_DURATION,
  interpolator: Interpolator = LinearInterpolator(),
  onEnd: () -> Unit = {}
): ValueAnimator {
  return anim(from, to) {
    onAnimatorUpdateValue = { value, _ ->
      translationX = value as Float
    }

    onAnimatorConfig = {
      it.duration = duration
      it.interpolator = interpolator
      onAnimatorEnd = { _ -> onEnd() }
    }
  }
}

fun View.translationY(
  from: Float,
  to: Float,
  duration: Long = ANIM_DURATION,
  interpolator: Interpolator = LinearInterpolator(),
  onEnd: () -> Unit = {}
): ValueAnimator {
  return anim(from, to) {
    onAnimatorUpdateValue = { value, _ ->
      translationY = value as Float
    }

    onAnimatorConfig = {
      it.duration = duration
      it.interpolator = interpolator
      onAnimatorEnd = { _ -> onEnd() }
    }
  }
}

/**补间动画*/
fun View.rotateAnimation(
  fromDegrees: Float = 0f,
  toDegrees: Float = 360f,
  duration: Long = ANIM_DURATION,
  interpolator: Interpolator = LinearInterpolator(),
  config: RotateAnimation.() -> Unit = {},
  onEnd: (animation: Animation) -> Unit = {}
): RotateAnimation {
  return RotateAnimation(
    fromDegrees,
    toDegrees,
    RotateAnimation.RELATIVE_TO_SELF,
    0.5f,
    RotateAnimation.RELATIVE_TO_SELF,
    0.5f
  ).apply {
    this.duration = duration
    this.interpolator = interpolator
    setAnimationListener(object : DefaultAnimationListener() {
      override fun onAnimationEnd(animation: Animation) {
        onEnd(animation)
      }
    })
    config()
    this@rotateAnimation.startAnimation(this)
  }
}


/**颜色渐变动画*/
fun colorAnimator(
  fromColor: Int,
  toColor: Int,
  infinite: Boolean = false,
  interpolator: Interpolator = LinearInterpolator(),
  duration: Long = ANIM_DURATION,
  onEnd: (cancel: Boolean) -> Unit = {},
  config: ValueAnimator.() -> Unit = {},
  onUpdate: (animator: ValueAnimator, color: Int) -> Unit
): ValueAnimator {
  //颜色动画
  val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
  colorAnimator.addUpdateListener { animation ->
    val color = animation.animatedValue as Int
    onUpdate(animation, color)
  }
  colorAnimator.addListener(object : DefaultAnimatorListener() {
    override fun _onAnimatorFinish(animator: Animator, fromCancel: Boolean) {
      super._onAnimatorFinish(animator, fromCancel)
      onEnd(fromCancel)
    }
  })
  colorAnimator.interpolator = interpolator
  colorAnimator.duration = duration
  if (infinite) {
    colorAnimator.repeatCount = ValueAnimator.INFINITE
    colorAnimator.repeatMode = ValueAnimator.REVERSE
  }
  colorAnimator.config()
  colorAnimator.start()
  return colorAnimator
}

/**背景变化动画*/
fun View.bgColorAnimator(
  fromColor: Int,
  toColor: Int,
  infinite: Boolean = false,
  interpolator: Interpolator = LinearInterpolator(),
  duration: Long = ANIM_DURATION,
  onEnd: (cancel: Boolean) -> Unit = {},
  config: ValueAnimator.() -> Unit = {}
): ValueAnimator {
  //背景动画
  return colorAnimator(
    fromColor,
    toColor,
    infinite,
    interpolator,
    duration,
    onEnd,
    config
  ) { _, color ->
    setBackgroundColor(color)
  }
}

/**
 * 抖动 放大缩小
 */
fun View.scaleAnimator(
  from: Float = 0.5f,
  to: Float = 1f,
  interpolator: Interpolator = BounceInterpolator(),
  onEnd: () -> Unit = {}
) {
  scaleAnimator(from, from, to, to, interpolator, onEnd)
}

fun View.scaleAnimator(
  fromX: Float = 0.5f,
  fromY: Float = 0.5f,
  toX: Float = 1f,
  toY: Float = 1f,
  interpolator: Interpolator = BounceInterpolator(),
  onEnd: () -> Unit = {}
) {
  scaleX = fromX
  scaleY = fromY
  animate()
    .scaleX(toX)
    .scaleY(toY)
    .setInterpolator(interpolator)
    .setDuration(ANIM_DURATION)
    .withEndAction { onEnd() }
    .start()
}