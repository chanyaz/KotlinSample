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
        MainPageRepository.loadTopNews()
                .compose(view.viewAvoidStateLoss())
                .subscribe({ result ->
                    nextDay = DateTimeUtils.getCurrentTimeMillis()
                    list.clear()
                    if (result.stories != null) {
                        view.setState(Consts.STATE_OK)
                        list.add(0, view.getStringByRes(R.string.yj_today_news))
                        list.addAll(result.stories!!)
                    } else {
                        view.setState(Consts.STATE_EMPTY)
                    }
                    view.showTopView(list)
                    view.stopRefresh()
                }, {})
    }

    override fun loadTopData() {
        view.setState(Consts.STATE_LOADING)
        MainPageRepository.loadTopNews()
                .compose(view.viewAvoidStateLoss())
                .subscribe({ newsResult ->
                    list.clear()
                    if (newsResult.stories != null) {
                        view.setState(Consts.STATE_OK)
                        list.add(0, view.getStringByRes(R.string.yj_today_news))
                        list.addAll(newsResult.stories!!)
                    } else {
                        view.setState(Consts.STATE_EMPTY)
                    }
                    view.showTopView(list)
                }, { view.setState(Consts.STATE_ERROR) })
    }

    override fun loadNextDay() {
        view.setState(Consts.STATE_LOADING_NEXT)
        MainPageRepository.loadNextDayNews(DateTimeUtils.getDayString(nextDay))
                .compose(view.viewAvoidStateLoss())
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