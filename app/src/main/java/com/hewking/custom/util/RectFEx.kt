package com.hewking.custom.util

import android.graphics.RectF

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/12/25 0025
 * 修改人员：hewking
 * 修改时间：2018/12/25 0025
 * 修改备注：
 * Version: 1.0.0
 */

/**
 * 新值是之前值得scale倍数
 */
fun RectF.scaleF(scale: Float) {
    if (scale != 1.0f) {
        top += top * (1 - scale)
        left += left * (1 - scale)
        right -= right * (1 - scale)
        bottom -= bottom * (1 - scale)
    }

}