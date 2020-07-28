package com.hewking.uikit.textview

import android.content.Context
import androidx.core.util.PatternsCompat
import androidx.appcompat.widget.AppCompatTextView
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/22 0022
 * 修改人员：hewking
 * 修改时间：2018/11/22 0022
 * 修改备注：
 * Version: 1.0.0
 */
class GCLinkTextView(ctx: Context, attrs: AttributeSet)
    : AppCompatTextView(ctx, attrs) {

    private var mOriginText: CharSequence? = null
    private var regionList: MutableList<MatchRegion> = mutableListOf()

    init {
        movementMethod = LinkMovementMethod()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        //1.通过正则表达式匹配出 text 的url
        //2.把匹配出的url 通过spannable 的方式，下划线标出来 并且设置点击事件
        //3.在设置行数 如果超过 n 行，则 spannable 的位置截取 并且最后设置 三个点 more
        // 没设置 text 之前肯定是没有layout 设置过值的，所以也没有 Linecount，所以匹配出的 url 都要保存一下
        mOriginText = text
        if (regionList == null) {
            regionList = mutableListOf()
        }
        var maxLength = 0
        for (filter in filters) {
            if (filter is InputFilter.LengthFilter) {
                val field = filter.javaClass.getDeclaredField("mMax")
                field.isAccessible = true
                maxLength = Math.max(maxLength, field.get(filter) as Int)
            }
        }
        val matcher = PatternsCompat.WEB_URL.matcher(text)
        val offset = getMoreString().length
        while (matcher.find()) {
            var end = matcher.end()
            if (end >= maxLength - 1) {
                end = maxLength
                val start = matcher.start()
                var group = matcher.group()
//                val subLen = maxLength - start - offset
//                group = group.substring(0, subLen) + getMoreString()
                val region = MatchRegion(start, end, group)
                regionList.add(region)
                break
            }
            val region = MatchRegion(matcher.start(), end, matcher.group())
            regionList.add(region)
        }

        var text = mOriginText
        if (mOriginText?.length ?: 0 > maxLength) {
            text = mOriginText?.substring(0, maxLength - offset) + getMoreString()
        }

        val span = SpannableStringBuilder(text)
        for (region in regionList) {
            span.setSpan(object : URLSpan(region.text) {
            }, region.start, region.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        super.setText(span, type)
    }

    data class MatchRegion(val start: Int, val end: Int, val text: String)

    fun getMoreString(): String {
        return "..."
    }

}