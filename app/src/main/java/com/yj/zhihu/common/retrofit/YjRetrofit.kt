package com.yj.zhihu.common.retrofit

import android.annotation.SuppressLint
import android.content.Context
import com.yj.zhihu.data.NewsResult
import com.yj.zhihu.data.ThemeResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
/**
 * @author yuanjian 17/10/31.
 */
object YjRetrofit : YjService {

    private lateinit var applicationContext: Context
    private var retrofit: Retrofit? = null

    private val HOST = "https://news-at.zhihu.com/"

    fun initContext(context: Context) {
        applicationContext = context.applicationContext
    }

    private fun build(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(RawCallFactory.getInstance(applicationContext))
                .build()
    }

    private fun createService(): YjService {
        if (retrofit == null) {
            retrofit = build(HOST)
        }
        return retrofit!!.create(YjService::class.java)
    }


    override fun getTheme(): Observable<ThemeResult> {
        return createService().getTheme()
    }

    override fun getLatestNews(): Observable<NewsResult> {
        return createService().getLatestNews()
    }

    override fun getHistoryNews(date: String): Observable<NewsResult> {
        return createService().getHistoryNews(date)
    }
}