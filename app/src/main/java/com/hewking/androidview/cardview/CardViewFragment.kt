package com.hewking.androidview.cardview

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.shadow.ShadowDrawableWrapper
import com.hewking.custom.R
import com.hewking.custom.util.ReflectUtil
import com.hewking.custom.util.dp2px
import kotlinx.android.synthetic.main.fragment_cardview_demo.*

class CardViewFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cardview_demo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_shadow.viewTreeObserver.addOnGlobalLayoutListener {
                iv_shadow.outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline?.setRoundRect(0, 0, view?.width ?: 0, view?.height ?: 0, 6f)
                    }
                }
            }
        }

        // 模拟 FloatActionButtom 实现阴影
        val shadowDrawable = ShadowDrawableWrapper(activity, ContextCompat.getDrawable(context!!, R.drawable.asm_logo), 10f, 20f, 20f)

        ReflectUtil.setField(shadowDrawable, "shadowStartColor", ContextCompat.getColor(context!!, R.color.app_color_theme_6))

        iv_shadow2.background = shadowDrawable
        ViewCompat.setTranslationZ(iv_shadow3, dp2px(8f).toFloat())
//        val roundRectShadow = RoundRect
    }
}