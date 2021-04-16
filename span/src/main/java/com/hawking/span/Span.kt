package com.hawking.span

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Color
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import com.fcbox.screw.span.*

/**
 * @Description:
 *
 * SpannableStringBuilder 提供丰富易用的api 进行使用
 * 采用 `DSL` 的方式进行 `text` 文本的配置
 *
 * use case：
 *
 * ``` kotlin
 *
 * witSpan(textView)
 * .append("hello") {
 *  setFontSize()
 *  setBullet()
 * }
 * .appendLine("world") {
 *    setLeadingMargin()
 * }
 * .appendImage(drawable,BOTTOM)
 * .appendSpace(10.dp, Color.RED)
 * .create()
 *
 * ```
 * @Author: jianhao
 * @Date:   2021/4/14 16:21
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
class Span private constructor(
    private val textView: TextView
) {

  @IntDef(ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP)
  @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
  annotation class Align

  private val _builder = SpannableStringBuilder()

  var flag: Int = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

  /**
   * Append the text text.
   * [text] The text.
   * [config] The DSL config
   */
  fun append(
      text: CharSequence?,
      config: (TextSpanConfiguration.() -> Unit)? = null
  ): Span {
    if (config != null) {
      val configuration = TextSpanConfiguration().apply(config)
      val spans = configuration.createSpan(textView)
      applySpan(text, spans, configuration.getLineHeight())
    } else {
      applySpan(text, null)
    }

    return this
  }

  /**
   * Append text and one line.
   * [text] The text.
   * [config] The DSL config
   */
  fun appendLine(
      text: CharSequence? = "",
      config: (TextSpanConfiguration.() -> Unit)? = null
  ): Span {
    return append(text.toString() + LINE_SEPARATOR, config)
  }

  /**
   * Append one image.
   *
   * [drawable] The drawable of image.
   * [align]    The alignment.
   *
   *  * [Align.ALIGN_TOP]
   *  * [Align.ALIGN_CENTER]
   *  * [Align.ALIGN_BASELINE]
   *  * [Align.ALIGN_BOTTOM]
   **/
  fun appendImage(
      drawable: Drawable,
      @Align align: Int = ALIGN_BOTTOM
  ): Span {
    val span = CustomImageSpan(drawable, align)
    val text = "<img>"
    val start = _builder.length
    append(text)
    val end = _builder.length
    _builder.setSpan(span, start, end, flag)
    reset()
    return this
  }

  /**
   * Append space.
   *
   * [size]  The size of space.
   * [color] The color of space.
   */
  fun appendSpace(
      @IntRange(from = 0) size: Int,
      @ColorInt color: Int = Color.TRANSPARENT
  ): Span {
    val span = SpaceSpan(size, color)
    val text = "< >"
    val start = _builder.length
    append(text)
    val end = _builder.length
    _builder.setSpan(span, start, end, flag)
    reset()
    return this
  }

  private fun applySpan(
      text: CharSequence?,
      spans: List<Any>?,
      lineHeight: Int = -1
  ) {
    var start = _builder.length
    if (start == 0 && lineHeight != -1) { // bug of LineHeightSpan when first line
      _builder.append(2.toChar().toString())
          .append("\n")
          .setSpan(AbsoluteSizeSpan(0), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      start = 2
    }
    _builder.append(text)
    val end = _builder.length

    spans ?: return

    for (span in spans) {
      _builder.setSpan(span, start, end, flag)
    }
    reset()
  }

  private fun reset() {
    flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
  }

  fun create() {
    textView.text = _builder
  }

  class TextSpanConfiguration {

    private var foregroundColor = COLOR_DEFAULT
    private var backgroundColor = COLOR_DEFAULT
    private var lineHeight = -1
    private var alignLine = 0
    private var quoteColor = COLOR_DEFAULT
    private var stripeWidth = 0
    private var quoteGapWidth = 0
    private var first = -1
    private var rest = 0
    private var bulletColor = COLOR_DEFAULT
    private var bulletRadius = 0
    private var bulletGapWidth = 0
    private var fontSize = -1
    private var fontSizeIsDp = false
    private var proportion = -1f
    private var xProportion = -1f
    private var isStrikethrough = false
    private var isUnderline = false
    private var isSuperscript = false
    private var isSubscript = false
    private var isBold = false
    private var isItalic = false
    private var isBoldItalic = false
    private var fontFamily: String? = null
    private var typeface: Typeface? = null
    private var alignment: Layout.Alignment? = null
    private var clickSpan: ClickableSpan? = null
    private var url: String? = null
    private var blurRadius = -1f
    private var style: Blur? = null
    private var shader: Shader? = null
    private var shadowRadius = -1f
    private var shadowDx = -1f
    private var shadowDy = -1f
    private var shadowColor = -1
    private var spans: Array<out Any>? = null


    /**
     * Set the span of foreground's color.
     *
     * @param color The color of foreground
     */
    fun setForegroundColor(@ColorInt color: Int) {
      foregroundColor = color
    }

    /**
     * Set the span of background's color.
     *
     * @param color The color of background
     */
    fun setBackgroundColor(@ColorInt color: Int) {
      backgroundColor = color
    }

    /**
     * Set the span of line height.
     *
     * @param lineHeight The line height, in pixel.
     */
    fun setLineHeight(@IntRange(from = 0) lineHeight: Int) {
      setLineHeight(lineHeight, ALIGN_CENTER)
    }

    /**
     * get the span of line height.
     */
    fun getLineHeight(): Int {
      return lineHeight
    }

    /**
     * Set the span of line height.
     *
     * @param lineHeight The line height, in pixel.
     * @param align      The alignment.
     *
     *  * [Align.ALIGN_TOP]
     *  * [Align.ALIGN_CENTER]
     *  * [Align.ALIGN_BOTTOM]
     *
     */
    fun setLineHeight(@IntRange(from = 0) lineHeight: Int,
                      @Align align: Int) {
      this.lineHeight = lineHeight
      alignLine = align
    }

    /**
     * Set the span of quote's color.
     *
     * @param color The color of quote
     */
    fun setQuoteColor(@ColorInt color: Int) {
      setQuoteColor(color, 2, 2)
    }

    /**
     * Set the span of quote's color.
     *
     * @param color       The color of quote.
     * @param stripeWidth The width of stripe, in pixel.
     * @param gapWidth    The width of gap, in pixel.
     */
    fun setQuoteColor(@ColorInt color: Int,
                      @IntRange(from = 1) stripeWidth: Int,
                      @IntRange(from = 0) gapWidth: Int) {
      quoteColor = color
      this.stripeWidth = stripeWidth
      quoteGapWidth = gapWidth
    }

    /**
     * Set the span of leading margin.
     *
     * @param first The indent for the first line of the paragraph.
     * @param rest  The indent for the remaining lines of the paragraph.
     */
    fun setLeadingMargin(@IntRange(from = 0) first: Int,
                         @IntRange(from = 0) rest: Int) {
      this.first = first
      this.rest = rest
    }

    /**
     * Set the span of bullet.
     *
     * @param gapWidth The width of gap, in pixel.
     */
    fun setBullet(@IntRange(from = 0) gapWidth: Int) {
      setBullet(0, 3, gapWidth)
    }

    /**
     * Set the span of bullet.
     *
     * @param color    The color of bullet.
     * @param radius   The radius of bullet, in pixel.
     * @param gapWidth The width of gap, in pixel.
     */
    fun setBullet(@ColorInt color: Int,
                  @IntRange(from = 0) radius: Int,
                  @IntRange(from = 0) gapWidth: Int) {
      bulletColor = color
      bulletRadius = radius
      bulletGapWidth = gapWidth
    }

    /**
     * Set the span of font's size.
     *
     * @param size The size of font.
     */
    fun setFontSize(@IntRange(from = 0) size: Int) {
      setFontSize(size, false)
    }

    /**
     * Set the span of size of font.
     *
     * @param size The size of font.
     * @param isSp True to use sp, false to use pixel.
     */
    fun setFontSize(@IntRange(from = 0) size: Int, isSp: Boolean) {
      fontSize = size
      fontSizeIsDp = isSp
    }

    /**
     * Set the span of proportion of font.
     *
     * @param proportion The proportion of font.
     */
    fun setFontProportion(proportion: Float) {
      this.proportion = proportion
    }

    /**
     * Set the span of transverse proportion of font.
     *
     * @param proportion The transverse proportion of font.
     */
    fun setFontXProportion(proportion: Float) {
      xProportion = proportion
    }

    /**
     * Set the span of strikethrough.
     *
     */
    fun setStrikethrough() {
      isStrikethrough = true
    }

    /**
     * Set the span of underline.
     *
     */
    fun setUnderline() {
      isUnderline = true
    }

    /**
     * Set the span of superscript.
     */
    fun setSuperscript() {
      isSuperscript = true
    }

    /**
     * Set the span of subscript.
     *
     */
    fun setSubscript() {
      isSubscript = true
    }

    /**
     * Set the span of bold.
     */
    fun setBold() {
      isBold = true
    }

    /**
     * Set the span of italic.
     *
     */
    fun setItalic() {
      isItalic = true
    }

    /**
     * Set the span of bold italic.
     */
    fun setBoldItalic() {
      isBoldItalic = true
    }

    /**
     * Set the span of font family.
     *
     * @param fontFamily The font family.
     *
     *  * monospace
     *  * serif
     *  * sans-serif
     *
     */
    fun setFontFamily(fontFamily: String) {
      this.fontFamily = fontFamily
    }

    /**
     * Set the span of typeface.
     *
     * @param typeface The typeface.
     */
    fun setTypeface(typeface: Typeface) {
      this.typeface = typeface
    }

    /**
     * Set the span of horizontal alignment.
     *
     * @param alignment The alignment.
     *
     *  * [Alignment.ALIGN_NORMAL]
     *  * [Alignment.ALIGN_OPPOSITE]
     *  * [Alignment.ALIGN_CENTER]
     */
    fun setHorizontalAlign(alignment: Layout.Alignment) {
      this.alignment = alignment
    }

    /**
     * Set the span of click.
     *
     * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
     *
     * @param clickSpan The span of click.
     */
    fun setClickSpan(clickSpan: ClickableSpan) {
      this.clickSpan = clickSpan
    }

    /**
     * Set the span of click.
     *
     * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
     *
     * @param color         The color of click span.
     * @param underlineText True to support underline, false otherwise.
     * @param listener      The listener of click span.
     */
    fun setClickSpan(@ColorInt color: Int,
                     underlineText: Boolean,
                     listener: View.OnClickListener?) {
      clickSpan = object : ClickableSpan() {
        override fun updateDrawState(paint: TextPaint) {
          paint.color = color
          paint.isUnderlineText = underlineText
        }

        override fun onClick(widget: View) {
          listener?.onClick(widget)
        }
      }
    }

    /**
     * Set the span of url.
     *
     * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
     *
     * @param url The url.
     */
    fun setUrl(url: String) {
      this.url = url
    }

    /**
     * Set the span of blur.
     *
     * @param radius The radius of blur.
     * @param style  The style.
     *
     *  * [Blur.NORMAL]
     *  * [Blur.SOLID]
     *  * [Blur.OUTER]
     *  * [Blur.INNER]
     *
     */
    fun setBlur(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
                style: Blur?) {
      blurRadius = radius
      this.style = style
    }

    /**
     * Set the span of shader.
     *
     * @param shader The shader.
     */
    fun setShader(shader: Shader) {
      this.shader = shader
    }

    /**
     * Set the span of shadow.
     *
     * @param radius      The radius of shadow.
     * @param dx          X-axis offset, in pixel.
     * @param dy          Y-axis offset, in pixel.
     * @param shadowColor The color of shadow.
     */
    fun setShadow(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
                  dx: Float,
                  dy: Float,
                  shadowColor: Int) {
      shadowRadius = radius
      shadowDx = dx
      shadowDy = dy
      this.shadowColor = shadowColor
    }

    /**
     * Set the spans.
     *
     * @param spans The spans.
     */
    fun setSpans(vararg spans: Any) {
      if (spans.isNotEmpty()) {
        this.spans = spans
      }
    }

    fun createSpan(textView: TextView): List<Any> {
      val spanList = mutableListOf<Any>()

      if (foregroundColor != COLOR_DEFAULT) {
        spanList.add(ForegroundColorSpan(foregroundColor))
      }

      if (backgroundColor != COLOR_DEFAULT) {
        spanList.add(BackgroundColorSpan(backgroundColor))
      }

      if (first != -1) {
        spanList.add(LeadingMarginSpan.Standard(first, rest))
      }

      if (quoteColor != COLOR_DEFAULT) {
        spanList.add(CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth))
      }

      if (bulletColor != COLOR_DEFAULT) {
        spanList.add(CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth))
      }

      if (fontSize != -1) {
        spanList.add(AbsoluteSizeSpan(fontSize, fontSizeIsDp))
      }

      if (proportion != -1f) {
        spanList.add(RelativeSizeSpan(proportion))
      }

      if (xProportion != -1f) {
        spanList.add(ScaleXSpan(xProportion))
      }

      if (lineHeight != -1) {
        spanList.add(CustomLineHeightSpan(lineHeight, alignLine))
      }

      if (isStrikethrough) {
        spanList.add(StrikethroughSpan())
      }

      if (isUnderline) {
        spanList.add(UnderlineSpan())
      }

      if (isSuperscript) {
        spanList.add(SuperscriptSpan())
      }
      if (isSubscript) {
        spanList.add(SubscriptSpan())
      }

      if (isBold) {
        spanList.add(StyleSpan(Typeface.BOLD))
      }
      if (isItalic) {
        spanList.add(StyleSpan(Typeface.ITALIC))
      }
      if (isBoldItalic) {
        spanList.add(StyleSpan(Typeface.BOLD_ITALIC))
      }

      fontFamily?.let {
        spanList.add(TypefaceSpan(it))
      }

      typeface?.let {
        spanList.add(CustomTypefaceSpan(it))
      }

      alignment?.let {
        spanList.add(AlignmentSpan.Standard(it))
      }

      clickSpan?.let {
        if (textView.movementMethod == null) {
          textView.movementMethod = LinkMovementMethod.getInstance()
        }
        spanList.add(it)
      }

      if (!url.isNullOrEmpty()) {
        if (textView.movementMethod == null) {
          textView.movementMethod = LinkMovementMethod.getInstance()
        }
        spanList.add(URLSpan(url))
      }

      if (blurRadius != -1f) {
        spanList.add(MaskFilterSpan(BlurMaskFilter(blurRadius, style)))
      }

      shader?.let {
        spanList.add(ShaderSpan(it))
      }

      if (shadowRadius != -1f) {
        spanList.add(ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor))
      }

      spans?.let {
        for (span in it) {
          spanList.add(span)
        }
      }

      return spanList
    }

  }

  class DrawableSpanConfiguration {

    private var imageDrawable: Drawable? = null
    private var alignImage = 0

    /**
     * Append one image.
     *
     * @param drawable The drawable of image.
     * @param align    The alignment.
     *
     *  * [Align.ALIGN_TOP]
     *  * [Align.ALIGN_CENTER]
     *  * [Align.ALIGN_BASELINE]
     *  * [Align.ALIGN_BOTTOM]
     *
     */
    fun config(drawable: Drawable, @Align align: Int = ALIGN_BOTTOM) {
      imageDrawable = drawable
      alignImage = align
    }


    fun create(): Any? {
      return imageDrawable?.let {
        CustomImageSpan(it, alignImage)
      }
    }

  }

  companion object {

    private const val COLOR_DEFAULT = -0x1000001
    const val ALIGN_BOTTOM = 0
    const val ALIGN_BASELINE = 1
    const val ALIGN_CENTER = 2
    const val ALIGN_TOP = 3

    private val LINE_SEPARATOR = System.getProperty("line.separator")

    fun with(textView: TextView): Span {
      return Span(textView)
    }

  }

}

fun withSpan(textView: TextView): Span {
  return Span.with(textView)
}