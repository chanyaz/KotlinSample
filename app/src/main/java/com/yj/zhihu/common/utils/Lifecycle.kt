package com.yj.zhihu.common.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author yuanjian 17/11/1.
 */
object Lifecycle {

    fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> schedulersIoTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { it.subscribeOn(Schedulers.io()) }
    }
}