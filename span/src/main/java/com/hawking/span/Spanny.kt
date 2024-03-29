package com.hawking.span

import android.graphics.*
import android.graphics.BlurMaskFilter.Blur
import android.graphics.drawable.Drawable
import android.net.Uri
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
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * @Description: utils about span
 * @Author: jianhao
 * @Date:   2021/4/13 17:12
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
class Spanny() {

  @IntDef(ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP)
  @Retention(RetentionPolicy.SOURCE)
  annotation class Align

  private val mBuilder: SpannableStringBuilder = SpannableStringBuilder()

  private var mTextView: TextView? = null
  private var mText: CharSequence
  private var flag = 0
  private var foregroundColor = 0
  private var backgroundColor = 0
  private var lineHeight = 0
  private var alignLine = 0
  private var quoteColor = 0
  private var stripeWidth = 0
  private var quoteGapWidth = 0
  private var first = 0
  private var rest = 0
  private var bulletColor = 0
  private var bulletRadius = 0
  private var bulletGapWidth = 0
  private var fontSize = 0
  private var fontSizeIsDp = false
  private var proportion = 0f
  private var xProportion = 0f
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
  private var blurRadius = 0f
  private var style: Blur? = null
  private var shader: Shader? = null
  private var shadowRadius = 0f
  private var shadowDx = 0f
  private var shadowDy = 0f
  private var shadowColor = 0
  private var spans: Array<out Any>? = null
  private var imageBitmap: Bitmap? = null
  private var imageDrawable: Drawable? = null
  private var imageUri: Uri? = null
  private var imageResourceId = 0
  private var alignImage = 0
  private var spaceSize = 0
  private var spaceColor = 0
  private var isCreated = false
  private var mType: Int
  private val mTypeCharSequence = 0
  private val mTypeImage = 1
  private val mTypeSpace = 2

  private constructor(textView: TextView) : this() {
    mTextView = textView
  }

  private fun setDefault() {
    flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    foregroundColor = COLOR_DEFAULT
    backgroundColor = COLOR_DEFAULT
    lineHeight = -1
    quoteColor = COLOR_DEFAULT
    first = -1
    bulletColor = COLOR_DEFAULT
    fontSize = -1
    proportion = -1f
    xProportion = -1f
    isStrikethrough = false
    isUnderline = false
    isSuperscript = false
    isSubscript = false
    isBold = false
    isItalic = false
    isBoldItalic = false
    fontFamily = null
    typeface = null
    alignment = null
    clickSpan = null
    url = null
    blurRadius = -1f
    shader = null
    shadowRadius = -1f
    spans = null
    imageBitmap = null
    imageDrawable = null
    imageUri = null
    imageResourceId = -1
    spaceSize = -1
  }

  /**
   * Set the span of flag.
   *
   * @param flag The flag.
   *
   *  * [Spanned.SPAN_INCLUSIVE_EXCLUSIVE]
   *  * [Spanned.SPAN_INCLUSIVE_INCLUSIVE]
   *  * [Spanned.SPAN_EXCLUSIVE_EXCLUSIVE]
   *  * [Spanned.SPAN_EXCLUSIVE_INCLUSIVE]
   *
   * @return the single [Spanny] instance
   */
  fun setFlag(flag: Int): Spanny {
    this.flag = flag
    return this
  }

  /**
   * Set the span of foreground's color.
   *
   * @param color The color of foreground
   * @return the single [Spanny] instance
   */
  fun setForegroundColor(@ColorInt color: Int): Spanny {
    foregroundColor = color
    return this
  }

  /**
   * Set the span of background's color.
   *
   * @param color The color of background
   * @return the single [Spanny] instance
   */
  fun setBackgroundColor(@ColorInt color: Int): Spanny {
    backgroundColor = color
    return this
  }

  /**
   * Set the span of line height.
   *
   * @param lineHeight The line height, in pixel.
   * @return the single [Spanny] instance
   */
  fun setLineHeight(@IntRange(from = 0) lineHeight: Int): Spanny {
    return setLineHeight(lineHeight, ALIGN_CENTER)
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
   * @return the single [Spanny] instance
   */
  fun setLineHeight(@IntRange(from = 0) lineHeight: Int,
                    @Align align: Int): Spanny {
    this.lineHeight = lineHeight
    alignLine = align
    return this
  }

  /**
   * Set the span of quote's color.
   *
   * @param color The color of quote
   * @return the single [Spanny] instance
   */
  fun setQuoteColor(@ColorInt color: Int): Spanny {
    return setQuoteColor(color, 2, 2)
  }

  /**
   * Set the span of quote's color.
   *
   * @param color       The color of quote.
   * @param stripeWidth The width of stripe, in pixel.
   * @param gapWidth    The width of gap, in pixel.
   * @return the single [Spanny] instance
   */
  fun setQuoteColor(@ColorInt color: Int,
                    @IntRange(from = 1) stripeWidth: Int,
                    @IntRange(from = 0) gapWidth: Int): Spanny {
    quoteColor = color
    this.stripeWidth = stripeWidth
    quoteGapWidth = gapWidth
    return this
  }

  /**
   * Set the span of leading margin.
   *
   * @param first The indent for the first line of the paragraph.
   * @param rest  The indent for the remaining lines of the paragraph.
   * @return the single [Spanny] instance
   */
  fun setLeadingMargin(@IntRange(from = 0) first: Int,
                       @IntRange(from = 0) rest: Int): Spanny {
    this.first = first
    this.rest = rest
    return this
  }

  /**
   * Set the span of bullet.
   *
   * @param gapWidth The width of gap, in pixel.
   * @return the single [Spanny] instance
   */
  fun setBullet(@IntRange(from = 0) gapWidth: Int): Spanny {
    return setBullet(0, 3, gapWidth)
  }

  /**
   * Set the span of bullet.
   *
   * @param color    The color of bullet.
   * @param radius   The radius of bullet, in pixel.
   * @param gapWidth The width of gap, in pixel.
   * @return the single [Spanny] instance
   */
  fun setBullet(@ColorInt color: Int,
                @IntRange(from = 0) radius: Int,
                @IntRange(from = 0) gapWidth: Int): Spanny {
    bulletColor = color
    bulletRadius = radius
    bulletGapWidth = gapWidth
    return this
  }

  /**
   * Set the span of font's size.
   *
   * @param size The size of font.
   * @return the single [Spanny] instance
   */
  fun setFontSize(@IntRange(from = 0) size: Int): Spanny {
    return setFontSize(size, false)
  }

  /**
   * Set the span of size of font.
   *
   * @param size The size of font.
   * @param isSp True to use sp, false to use pixel.
   * @return the single [Spanny] instance
   */
  fun setFontSize(@IntRange(from = 0) size: Int, isSp: Boolean): Spanny {
    fontSize = size
    fontSizeIsDp = isSp
    return this
  }

  /**
   * Set the span of proportion of font.
   *
   * @param proportion The proportion of font.
   * @return the single [Spanny] instance
   */
  fun setFontProportion(proportion: Float): Spanny {
    this.proportion = proportion
    return this
  }

  /**
   * Set the span of transverse proportion of font.
   *
   * @param proportion The transverse proportion of font.
   * @return the single [Spanny] instance
   */
  fun setFontXProportion(proportion: Float): Spanny {
    xProportion = proportion
    return this
  }

  /**
   * Set the span of strikethrough.
   *
   * @return the single [Spanny] instance
   */
  fun setStrikethrough(): Spanny {
    isStrikethrough = true
    return this
  }

  /**
   * Set the span of underline.
   *
   * @return the single [Spanny] instance
   */
  fun setUnderline(): Spanny {
    isUnderline = true
    return this
  }

  /**
   * Set the span of superscript.
   *
   * @return the single [Spanny] instance
   */
  fun setSuperscript(): Spanny {
    isSuperscript = true
    return this
  }

  /**
   * Set the span of subscript.
   *
   * @return the single [Spanny] instance
   */
  fun setSubscript(): Spanny {
    isSubscript = true
    return this
  }

  /**
   * Set the span of bold.
   *
   * @return the single [Spanny] instance
   */
  fun setBold(): Spanny {
    isBold = true
    return this
  }

  /**
   * Set the span of italic.
   *
   * @return the single [Spanny] instance
   */
  fun setItalic(): Spanny {
    isItalic = true
    return this
  }

  /**
   * Set the span of bold italic.
   *
   * @return the single [Spanny] instance
   */
  fun setBoldItalic(): Spanny {
    isBoldItalic = true
    return this
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
   * @return the single [Spanny] instance
   */
  fun setFontFamily(fontFamily: String): Spanny {
    this.fontFamily = fontFamily
    return this
  }

  /**
   * Set the span of typeface.
   *
   * @param typeface The typeface.
   * @return the single [Spanny] instance
   */
  fun setTypeface(typeface: Typeface): Spanny {
    this.typeface = typeface
    return this
  }

  /**
   * Set the span of horizontal alignment.
   *
   * @param alignment The alignment.
   *
   *  * [Alignment.ALIGN_NORMAL]
   *  * [Alignment.ALIGN_OPPOSITE]
   *  * [Alignment.ALIGN_CENTER]
   *
   * @return the single [Spanny] instance
   */
  fun setHorizontalAlign(alignment: Layout.Alignment): Spanny {
    this.alignment = alignment
    return this
  }

  /**
   * Set the span of click.
   *
   * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
   *
   * @param clickSpan The span of click.
   * @return the single [Spanny] instance
   */
  fun setClickSpan(clickSpan: ClickableSpan): Spanny {
    if (mTextView != null && mTextView!!.movementMethod == null) {
      mTextView!!.movementMethod = LinkMovementMethod.getInstance()
    }
    this.clickSpan = clickSpan
    return this
  }

  /**
   * Set the span of click.
   *
   * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
   *
   * @param color         The color of click span.
   * @param underlineText True to support underline, false otherwise.
   * @param listener      The listener of click span.
   * @return the single [Spanny] instance
   */
  fun setClickSpan(@ColorInt color: Int,
                   underlineText: Boolean,
                   listener: View.OnClickListener?): Spanny {
    if (mTextView != null && mTextView!!.movementMethod == null) {
      mTextView!!.movementMethod = LinkMovementMethod.getInstance()
    }
    clickSpan = object : ClickableSpan() {
      override fun updateDrawState(paint: TextPaint) {
        paint.color = color
        paint.isUnderlineText = underlineText
      }

      override fun onClick(widget: View) {
        listener?.onClick(widget)
      }
    }
    return this
  }

  /**
   * Set the span of url.
   *
   * Must set `view.setMovementMethod(LinkMovementMethod.getInstance())`
   *
   * @param url The url.
   * @return the single [Spanny] instance
   */
  fun setUrl(url: String): Spanny {
    if (mTextView != null && mTextView!!.movementMethod == null) {
      mTextView!!.movementMethod = LinkMovementMethod.getInstance()
    }
    this.url = url
    return this
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
   * @return the single [Spanny] instance
   */
  fun setBlur(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
              style: Blur?): Spanny {
    blurRadius = radius
    this.style = style
    return this
  }

  /**
   * Set the span of shader.
   *
   * @param shader The shader.
   * @return the single [Spanny] instance
   */
  fun setShader(shader: Shader): Spanny {
    this.shader = shader
    return this
  }

  /**
   * Set the span of shadow.
   *
   * @param radius      The radius of shadow.
   * @param dx          X-axis offset, in pixel.
   * @param dy          Y-axis offset, in pixel.
   * @param shadowColor The color of shadow.
   * @return the single [Spanny] instance
   */
  fun setShadow(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
                dx: Float,
                dy: Float,
                shadowColor: Int): Spanny {
    shadowRadius = radius
    shadowDx = dx
    shadowDy = dy
    this.shadowColor = shadowColor
    return this
  }

  /**
   * Set the spans.
   *
   * @param spans The spans.
   * @return the single [Spanny] instance
   */
  fun setSpans(vararg spans: Any): Spanny {
    if (spans.isNotEmpty()) {
      this.spans = spans
    }
    return this
  }

  /**
   * Append the text text.
   *
   * @param text The text.
   * @return the single [Spanny] instance
   */
  fun append(text: CharSequence): Spanny {
    apply(mTypeCharSequence)
    mText = text
    return this
  }

  /**
   * Append one line.
   *
   * @return the single [Spanny] instance
   */
  fun appendLine(): Spanny {
    apply(mTypeCharSequence)
    mText = LINE_SEPARATOR
    return this
  }

  /**
   * Append text and one line.
   *
   * @return the single [Spanny] instance
   */
  fun appendLine(text: CharSequence): Spanny {
    apply(mTypeCharSequence)
    mText = text.toString() + LINE_SEPARATOR
    return this
  }


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
   * @return the single [Spanny] instance
   */
  /**
   * Append one image.
   *
   * @param drawable The drawable of image.
   * @return the single [Spanny] instance
   */
  @JvmOverloads
  fun appendImage(drawable: Drawable, @Align align: Int = ALIGN_BOTTOM): Spanny {
    apply(mTypeImage)
    imageDrawable = drawable
    alignImage = align
    return this
  }

  /**
   * Append space.
   *
   * @param size  The size of space.
   * @param color The color of space.
   * @return the single [Spanny] instance
   */
  /**
   * Append space.
   *
   * @param size The size of space.
   * @return the single [Spanny] instance
   */
  @JvmOverloads
  fun appendSpace(@IntRange(from = 0) size: Int, @ColorInt color: Int = Color.TRANSPARENT): Spanny {
    apply(mTypeSpace)
    spaceSize = size
    spaceColor = color
    return this
  }

  private fun apply(type: Int) {
    applyLast()
    mType = type
  }

  fun get(): SpannableStringBuilder {
    return mBuilder
  }

  /**
   * Create the span string.
   *
   * @return the span string
   */
  fun create(): SpannableStringBuilder {
    applyLast()
    if (mTextView != null) {
      mTextView!!.text = mBuilder
    }
    isCreated = true
    return mBuilder
  }

  private fun applyLast() {
    if (isCreated) {
      return
    }
    when (mType) {
      mTypeCharSequence -> {
        updateCharCharSequence()
      }
      mTypeImage -> {
        updateImage()
      }
      mTypeSpace -> {
        updateSpace()
      }
    }
    setDefault()
  }

  private fun updateCharCharSequence() {
    if (mText.isEmpty()) return
    var start = mBuilder.length
    if (start == 0 && lineHeight != -1) { // bug of LineHeightSpan when first line
      mBuilder.append(2.toChar().toString())
          .append("\n")
          .setSpan(AbsoluteSizeSpan(0), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      start = 2
    }
    mBuilder.append(mText)
    val end = mBuilder.length

    if (foregroundColor != COLOR_DEFAULT) {
      mBuilder.setSpan(ForegroundColorSpan(foregroundColor), start, end, flag)
    }
    if (backgroundColor != COLOR_DEFAULT) {
      mBuilder.setSpan(BackgroundColorSpan(backgroundColor), start, end, flag)
    }
    if (first != -1) {
      mBuilder.setSpan(LeadingMarginSpan.Standard(first, rest), start, end, flag)
    }
    if (quoteColor != COLOR_DEFAULT) {
      mBuilder.setSpan(
          CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth),
          start,
          end,
          flag
      )
    }
    if (bulletColor != COLOR_DEFAULT) {
      mBuilder.setSpan(
          CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth),
          start,
          end,
          flag
      )
    }
    if (fontSize != -1) {
      mBuilder.setSpan(AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag)
    }
    if (proportion != -1f) {
      mBuilder.setSpan(RelativeSizeSpan(proportion), start, end, flag)
    }
    if (xProportion != -1f) {
      mBuilder.setSpan(ScaleXSpan(xProportion), start, end, flag)
    }
    if (lineHeight != -1) {
      mBuilder.setSpan(CustomLineHeightSpan(lineHeight, alignLine), start, end, flag)
    }
    if (isStrikethrough) {
      mBuilder.setSpan(StrikethroughSpan(), start, end, flag)
    }
    if (isUnderline) {
      mBuilder.setSpan(UnderlineSpan(), start, end, flag)
    }
    if (isSuperscript) {
      mBuilder.setSpan(SuperscriptSpan(), start, end, flag)
    }
    if (isSubscript) {
      mBuilder.setSpan(SubscriptSpan(), start, end, flag)
    }
    if (isBold) {
      mBuilder.setSpan(StyleSpan(Typeface.BOLD), start, end, flag)
    }
    if (isItalic) {
      mBuilder.setSpan(StyleSpan(Typeface.ITALIC), start, end, flag)
    }
    if (isBoldItalic) {
      mBuilder.setSpan(StyleSpan(Typeface.BOLD_ITALIC), start, end, flag)
    }
    if (fontFamily != null) {
      mBuilder.setSpan(TypefaceSpan(fontFamily), start, end, flag)
    }
    if (typeface != null) {
      mBuilder.setSpan(CustomTypefaceSpan(typeface!!), start, end, flag)
    }
    if (alignment != null) {
      mBuilder.setSpan(AlignmentSpan.Standard(alignment!!), start, end, flag)
    }
    if (clickSpan != null) {
      mBuilder.setSpan(clickSpan, start, end, flag)
    }
    if (url != null) {
      mBuilder.setSpan(URLSpan(url), start, end, flag)
    }
    if (blurRadius != -1f) {
      mBuilder.setSpan(
          MaskFilterSpan(BlurMaskFilter(blurRadius, style)),
          start,
          end,
          flag
      )
    }
    if (shader != null) {
      mBuilder.setSpan(ShaderSpan(shader!!), start, end, flag)
    }
    if (shadowRadius != -1f) {
      mBuilder.setSpan(
          ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor),
          start,
          end,
          flag
      )
    }
    if (spans != null) {
      for (span in spans!!) {
        mBuilder.setSpan(span, start, end, flag)
      }
    }
  }

  private fun updateImage() {
    val start = mBuilder.length
    mText = "<img>"
    updateCharCharSequence()
    val end = mBuilder.length
    imageDrawable?.let {
      mBuilder.setSpan(CustomImageSpan(it, alignImage), start, end, flag)
    }
  }

  private fun updateSpace() {
    val start = mBuilder.length
    mText = "< >"
    updateCharCharSequence()
    val end = mBuilder.length
    mBuilder.setSpan(SpaceSpan(spaceSize, spaceColor), start, end, flag)
  }

  companion object {

    private const val COLOR_DEFAULT = -0x1000001
    const val ALIGN_BOTTOM = 0
    const val ALIGN_BASELINE = 1
    const val ALIGN_CENTER = 2
    const val ALIGN_TOP = 3

    private val LINE_SEPARATOR = System.getProperty("line.separator")

    fun with(textView: TextView): Spanny {
      return Spanny(textView)
    }
  }

  init {
    mText = ""
    mType = -1
    setDefault()
  }

}