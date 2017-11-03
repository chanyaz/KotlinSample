package com.yj.zhihu.common.mvp

import android.support.annotation.StringRes

/**
 * @author yuanjian 17/11/3.
 */
interface AndroidView {
    fun getStringByRes(@StringRes resId: Int): String
}