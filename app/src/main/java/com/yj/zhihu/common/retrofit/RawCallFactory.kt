package com.yj.zhihu.common.retrofit

import android.content.Context
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author yuanjian 17/10/31.
 */
class RawCallFactory private constructor() {

    companion object {
        @Volatile
        var factory: Call.Factory? = null
        private val DEFAULT_TIMEOUT = 30L

        fun getInstance(context: Context): Call.Factory {
            if (factory == null) {
                synchronized(RawCallFactory::class.java) {
                    val builder = OkHttpClient.Builder()
                    builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    builder.cache(Cache(File(context.filesDir, "responses"), 10 * 1024 * 1024))
                    // todo common cache
                    factory = builder.build();
                }
            }
            return factory!!
        }
    }
}