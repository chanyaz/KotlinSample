package com.yj.zhihu.business.news

import com.yj.zhihu.common.mvp.BaseView
import com.yj.zhihu.data.NewsResult
import io.reactivex.Observable

/**
 * @author yuanjian 17/11/1.
 */
interface MainPageContract {
    interface View : BaseView<Presenter> {
        fun showTopView(list: List<Any>)
        fun addList(startPosition: Int, size: Int)
        fun stopRefresh()
    }

    interface Presenter {
        fun refresh()
        fun loadTopData()
        fun loadNextDay()
    }

    interface Model {
        fun loadTopNews(): Observable<NewsResult>
        fun loadNextDayNews(day: String): Observable<NewsResult>
    }
}