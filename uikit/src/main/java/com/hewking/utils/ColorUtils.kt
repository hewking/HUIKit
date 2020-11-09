package com.hewking.utils

import androidx.annotation.ColorInt
import androidx.annotation.IntRange

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/9/11 14:37
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
fun setAlphaComponent(@ColorInt color: Int,
                      @IntRange(from = 0x0, to = 0xFF) alpha: Int): Int {
    require(!(alpha < 0 || alpha > 255)) { "alpha must be between 0 and 255." }
    return color and 0x00ffffff or (alpha shl 24)
}