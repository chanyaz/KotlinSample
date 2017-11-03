package com.yj.zhihu.common.mvp

import io.reactivex.ObservableTransformer

/**
 * @author yuanjian 17/11/1.
 */
interface BaseView<in T> : AndroidView {

    fun <T> viewAvoidStateLoss(): ObservableTransformer<T, T>


    fun setState(state: Int)

}