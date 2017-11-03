package com.yj.zhihu.common.base

import com.trello.rxlifecycle2.components.RxFragment
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author yuanjian 17/10/31.
 */
abstract class BaseFragment : RxFragment() {
    fun <T> bindDestroy(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
        }
    }
}