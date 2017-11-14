package com.yj.zhihu.common.extensions

import android.view.View

/**
 * @author yuanjian 17/11/10.
 */

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.isInShow(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInGone(): Boolean {
    return visibility == View.GONE
}
