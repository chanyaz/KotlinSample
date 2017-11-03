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

//                    if (Ln.isDebugEnabled()) {
//                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//                        builder.interceptors().add(loggingInterceptor);
//                    }
//                    builder.cache(new Cache(new File(context.getFilesDir(), "responses"), CONST_10 * CONST_1024 * CONST_1024));
//                    if (DevSettingStore.isDevChannel()) {
//                        builder.interceptors().add(new DevUrlInterceptor(context));
//                    }
//                    builder.interceptors().add(new NetStatisticsInterceptor());
//                    // 无网络时，缓存一天
//                    builder.interceptors().add(new LocalCacheInterceptor(context, DEFAULT_MAX_LOCAL_CACHE_SECONDS));
//                    // 有网络时，缓存时间
//                    builder.networkInterceptors().add(0, new NetCacheInterceptor(DEFAULT_MAX_NET_CACHE_SECONDS));
                    factory = builder.build();
                }
            }
            return factory!!
        }
    }
}