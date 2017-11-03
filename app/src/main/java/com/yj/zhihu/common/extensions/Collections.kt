package com.yj.zhihu.common.extensions

/**
 * @author yuanjian 17/11/3.
 */

fun <T> Collection<T>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()