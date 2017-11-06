package com.yj.zhihu

import android.app.Application
import com.yj.zhihu.common.retrofit.YjRetrofit
import com.yj.zhihu.common.utils.Ln

/**
 * @author yuanjian 17/10/31.
 */
class ZHApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Ln.init(this)
        YjRetrofit.initContext(this)
    }
}