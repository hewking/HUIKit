package com.hewking.uikit.textview;

import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/17 0017
 * 修改人员：hewking
 * 修改时间：2018/10/17 0017
 * 修改备注：
 * Version: 1.0.0
 */
public class AutoLinkSpan extends URLSpan {

    public AutoLinkSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(View widget) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.getColor());
        ds.setUnderlineText(false);
    }
}
