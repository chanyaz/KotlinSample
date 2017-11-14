package com.yj.zhihu.common.mvp

import io.reactivex.ObservableTransformer

/**
 * @author yuanjian 17/11/14.
 */
interface LifecycleView {
    fun <T> bindDestroy(): ObservableTransformer<T, T>
}