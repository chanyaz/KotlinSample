package com.yj.zhihu.common.mvp

/**
 * @author yuanjian 17/11/1.
 */
interface BaseView<in T> : AndroidView, LifecycleView {

    fun setState(state: Int)

}