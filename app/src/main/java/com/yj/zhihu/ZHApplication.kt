package com.yj.zhihu

import android.app.Application
import com.yj.zhihu.common.retrofit.YjRetrofit

/**
 * @author yuanjian 17/10/31.
 */
class ZHApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        YjRetrofit.initContext(this)
    }
}