package com.yj.zhihu.business.news

import com.yj.zhihu.common.retrofit.YjRetrofit
import com.yj.zhihu.data.NewsResult
import io.reactivex.Observable

/**
 * @author yuanjian 17/11/1.
 */
object MainPageRepository : MainPageContract.Model {
    override fun loadTopNews(): Observable<NewsResult> {
        return YjRetrofit.getLatestNews()
    }

    override fun loadNextDayNews(day: String): Observable<NewsResult> {
        return YjRetrofit.getHistoryNews(day)
    }


}