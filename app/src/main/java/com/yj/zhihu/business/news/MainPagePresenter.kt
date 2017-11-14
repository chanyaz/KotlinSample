package com.yj.zhihu.business.news

import com.yj.zhihu.R
import com.yj.zhihu.common.utils.Consts
import com.yj.zhihu.common.utils.DateTimeUtils

/**
 * @author yuanjian 17/11/1.
 */
class MainPagePresenter(private var view: MainPageContract.View) : MainPageContract.Presenter {

    private var nextDay: Long = DateTimeUtils.getCurrentTimeMillis()
    private val list = ArrayList<Any>()

    override fun refresh() {
        loadTop(true)
    }

    override fun loadTopData() {
        view.setState(Consts.STATE_LOADING)
        loadTop(false)
    }

    private fun loadTop(refresh: Boolean) {
        MainPageRepository.loadTopNews()
                .compose(view.bindDestroy())
                .subscribe({ newsResult ->
                    list.clear()
                    if (newsResult.stories != null) {
                        view.setState(Consts.STATE_OK)
                        list.add(newsResult.topStories!!)
                        list.add(view.getStringByRes(R.string.yj_today_news))
                        list.addAll(newsResult.stories!!)
                    } else {
                        view.setState(Consts.STATE_EMPTY)
                    }
                    view.showTopView(list)
                    if (refresh) {
                        view.stopRefresh()
                    }
                }, {
                    if (refresh) {
                        view.stopRefresh()
                    }
                    view.setState(Consts.STATE_ERROR)
                })
    }

    override fun loadNextDay() {
        view.setState(Consts.STATE_LOADING_NEXT)
        MainPageRepository.loadNextDayNews(DateTimeUtils.getDayString(nextDay))
                .compose(view.bindDestroy())
                .subscribe({ result ->
                    nextDay -= DateTimeUtils.ONE_DAY
                    if (result.stories != null) {
                        list.add(DateTimeUtils.getDayString(nextDay))
                        list.addAll(result.stories!!)
                        view.addList(list.size, result.stories!!.size)
                    }
                }, {})
    }
}