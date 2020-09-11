package com.hewking.demo

import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Magnifier
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hewking.custom.R
import com.hewking.uikit.textview.AutoLinkSpan
import com.hewking.utils.L
import com.hewking.utils.SpannableStringUtil
import com.hewking.utils.v
import kotlinx.android.synthetic.main.fragment_htextview.*

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/4
 * 修改人员：hewking
 * 修改时间：2018/5/4
 * 修改备注：
 * Version: 1.0.0
 */
class HTextViewFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_htextview,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_input.addTextChangedListener(HexInputFilter(view.v<EditText>(R.id.et_input)))

        /*  et_input.clearFocus()
          et_text.clearFocus()*/

        et_input.isFocusable = false
        et_text.isFocusable = false

        val focused = et_input.isFocusable

        tv_image_span.text = SpannableStringUtils.getBuilder("xxxx")
                .setResourceId(R.mipmap.img_coindrop_icon)
                .setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("span image testfsdffffffffffffffffffffffff")
                .create()

        tv_image_span.setOnClickListener {
            et_input.isFocusable = true
            et_input.isFocusableInTouchMode = true
            et_text.isFocusable = true
            et_text.isFocusableInTouchMode = true
            toast("focus changed")
            et_input.requestFocus()

            L.d("HTextViewFragment", "et_input focus : ${et_input.isFocusable}  et_text focus ${et_text.isFocusable}")
            L.d("HTextViewFragment", "et_input focus : ${et_input.isFocused}  et_text focus ${et_text.isFocused}")
        }

        tv_click_span.apply {
            text = "gggggddgfddddddddddddddddddddddddddddddddddwww.baidu.com"
            setOnLongClickListener {
                toast("LongClickListener")
                true
            }
        }

        tv_text_span.setOnClickListener({
            L.d("HTextViewFragment","onclick")
        })

//        smart_tv.text = "tronbet.io sdfsd baidu.com"

//        TextViewCompat.setAutoSizeTextTypeWithDefaults(tv_auto_size,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
//        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv_auto_size,14,100,10,TypedValue.COMPLEX_UNIT_SP)
//        et_text.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//                tv_auto_size.text = s?.toString()
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//        })

        val spannable = tv_text_span.text as Spannable
       val spans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
        for (i in 0 until spans.size) {
            var url = spans[i].getURL();
            var index = spannable.toString().indexOf(url);
            var end = index + url.length
            if (index == -1) {
                if (url.contains("http://")) {
                    url = url.replace("http://", "");
                } else if (url.contains("https://")) {
                    url = url.replace("https://", "");
                } else if (url.contains("rtsp://")) {
                    url = url.replace("rtsp://", "");
                }
                index = spannable.toString().indexOf(url);
                end = index + url.length
            }
            if (index != -1) {
                spannable.removeSpan(spans[i]);
                spannable.setSpan(AutoLinkSpan(spans[i].getURL()), index
                        , end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            cb.text =  if (isChecked) "ischecked" else "nochecked"
        }

        // 放大镜查看文字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val magnifier = Magnifier(tv_auto_size)
            magnifier.show(tv_auto_size.width.div(2f),tv_auto_size.height.div(2f))
            tv_auto_size.setOnTouchListener { v, event ->
                when(event.actionMasked){
                    MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> {
                        val array = IntArray(2)
                        v.getLocationOnScreen(array)
                        magnifier.show(event.rawX - array[0],event.rawY - array[1])
                    }

                    MotionEvent.ACTION_UP ,MotionEvent.ACTION_CANCEL -> {
                        magnifier.dismiss()
                    }
                }
                false
            }
        }

        // dynamic programing set autosize config
//        TextViewCompat.setAutoSizeTextTypeWithDefaults(tv_auto_size, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
//        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv_auto_size, 8, 17, 2, TypedValue.COMPLEX_UNIT_PT)
//        tv_auto_size.text = "wo shi zhognwena  wo daodi xingbuxing a autosize"

//        tv_auto_size.viewTreeObserver.addOnGlobalLayoutListener {
//            // 这时候可以获取宽高
//            tv_auto_size.text = "wo shi zhognwena  wo daodi xingbuxing a autosize"
//        }

        tv_need_shadow.movementMethod = LinkMovementMethod.getInstance()
        tv_need_shadow.text = SpannableStringUtil.getBuilder(requireContext(),"我同意")
                .append("怎么不行？")
                .setClickSpan(object : ClickableSpan(){
                    override fun onClick(widget: View) {
                        toast("我同意啊")
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }

                })
                .append("丰巢用户协议")
                .create()
    }

    fun Fragment.toast(msg :String){
        Toast.makeText(DemoApplication.context,msg,Toast.LENGTH_SHORT).show()
    }

}