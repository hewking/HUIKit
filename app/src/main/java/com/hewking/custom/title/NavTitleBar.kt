package com.hewking.custom.title

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.hewking.custom.R
import com.hewking.custom.util.dp2px
import com.hewking.custom.util.setPaddingLeft
import com.hewking.custom.util.setPaddingTop

class NavTitleBar @JvmOverloads constructor(@NonNull context: Context
                                            , @Nullable attrs: AttributeSet? = null
                                            , defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private var leftContainer: LinearLayout = LinearLayout(context)
    private var centerContainer: LinearLayout = LinearLayout(context)
    private var rightContainer: LinearLayout = LinearLayout(context)

    private val defaultConfig = ToolBarConfig.Builder()
            .addLeftView(buildImageView(context, R.drawable.icon_arrow_white).apply {
                setPaddingLeft(dp2px(10f))
                setPaddingTop(dp2px(4f))
                setPaddingLeft(dp2px(10f))
                setPaddingLeft(dp2px(4f))
            })
            .addCenterView(buildTextView(context, context.getString(R.string.app_name)))
            .build()

    init {

        addView(leftContainer, FrameLayout.LayoutParams(-2, -2).apply {
            gravity = (Gravity.START.or(Gravity.CENTER_VERTICAL))
        })
        addView(centerContainer, FrameLayout.LayoutParams(-2, -2).apply {
            gravity = Gravity.CENTER
        })
        addView(rightContainer, FrameLayout.LayoutParams(-2, -2).apply {
            gravity = Gravity.END.or(Gravity.CENTER_VERTICAL)
        })

        setBackgroundResource(R.color.colorPrimary)

        config(defaultConfig)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var hSpec = heightMeasureSpec
        if (hMode == MeasureSpec.AT_MOST) {
            hSpec = MeasureSpec.makeMeasureSpec(dp2px(48f), MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, hSpec)
    }

    fun buildImageView(ctx: Context, @DrawableRes res: Int): ImageView {
        return ImageView(ctx).apply {
            setImageResource(res)
        }
    }

    fun buildTextView(ctx: Context, text: String): TextView {
        return TextView(ctx).apply {
            this.text = text
        }
    }

    fun addLeftView(view: View, index: Int = 0) {
        leftContainer.addView(view, index)
    }

    fun addCenterView(view: View, index: Int = 0) {
        centerContainer.addView(view, index)
    }

    fun addRightView(view: View, index: Int = 0) {
        rightContainer.addView(view, index)
    }

    fun hideLeft() {
        leftContainer.visibility = View.GONE
    }

    fun hideCenter() {
        centerContainer.visibility = View.GONE
    }

    fun hideRight() {
        rightContainer.visibility = View.GONE
    }

    fun showLeft() {
        leftContainer.visibility = View.VISIBLE
    }

    fun showCenter() {
        leftContainer.visibility = View.VISIBLE
    }

    fun showRight() {
        leftContainer.visibility = View.VISIBLE
    }

    fun addViewPattern(pattern: ViewPattern) {
        when (pattern.direction) {
            Gravity.START -> {
                leftContainer.removeAllViews()
                pattern.childs.forEach {
                    leftContainer.addView(it)
                }
            }
            Gravity.CENTER -> {
                centerContainer.removeAllViews()
                pattern.childs.forEach {
                    centerContainer.addView(it)
                }
            }
            Gravity.END -> {
                rightContainer.removeAllViews()
                pattern.childs.forEach {
                    rightContainer.addView(it)
                }
            }

            else -> {

            }
        }
    }

    fun config(config: ToolBarConfig) {
        addViewPatterns(config.patterns)
    }

    fun addViewPatterns(patterns: MutableList<ViewPattern>) {
        patterns.forEach {
            addViewPattern(it)
        }
    }

    class ViewPattern {
        var direction: Int = Gravity.NO_GRAVITY
        var childs: MutableList<View> = mutableListOf()

        fun addView(child: View, index: Int = 0) {
            childs.add(index, child)
        }
    }

    class ToolBarConfig {
        val patterns = mutableListOf<ViewPattern>()

        class Builder {

            private var leftPattern = ViewPattern().apply {
                direction = Gravity.START
            }

            private val centerPattern = ViewPattern().apply {
                direction = Gravity.CENTER
            }

            private val rightPattern = ViewPattern().apply {
                direction = Gravity.END
            }

            fun addLeftView(view: View, index: Int = 0): Builder {
                leftPattern.addView(view, index)
                return this
            }

            fun addCenterView(view: View, index: Int = 0): Builder {
                centerPattern.addView(view, index)
                return this
            }

            fun addRightView(view: View, index: Int = 0): Builder {
                rightPattern.addView(view, index)
                return this
            }


            fun build(): ToolBarConfig {
                return ToolBarConfig().apply {
                    patterns.add(leftPattern)
                    patterns.add(centerPattern)
                    patterns.add(rightPattern)
                }
            }
        }
    }

}
