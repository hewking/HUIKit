package com.hewking.custom.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/26 0026
 * 修改人员：hewking
 * 修改时间：2018/12/26 0026
 * 修改备注：
 * Version: 1.0.0
 */
public class ResUtil {

    /**
     * 获取一个透明颜色
     *
     * @param color 原始颜色
     * @param alpha 透明度 [0,255] 值越小越透明
     * @return 透明后颜色
     */
    public static int getTranColor(@ColorInt int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

}
