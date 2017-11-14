package com.yj.zhihu.common.base

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.yj.zhihu.common.mvp.AndroidView
import com.yj.zhihu.common.mvp.LifecycleView
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author yuanjian 17/10/31.
 */
abstract class BaseActivity : RxAppCompatActivity(), AndroidView, LifecycleView {

    override fun <T> bindDestroy(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
        }
    }

    override fun getStringByRes(resId: Int): String {
        return getString(resId)
    }

}