package com.yj.zhihu.business.news.views

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import com.yj.zhihu.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.yj_layout_view_banner.view.*
import java.util.concurrent.TimeUnit

/**
 * @author yuanjian 17/11/3.
 */
class NewsBanner : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.yj_layout_view_banner, this, true)
    }

    fun hasData(): Boolean {
        return loop_view_pager.adapter != null
    }

    fun setData(pagerAdapter: PagerAdapter, currentItem: Int) {
        loop_view_pager.adapter = pagerAdapter
        loop_view_pager.currentItem = currentItem
        loop_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                lopper_indicator.scrollIndicator(position, positionOffset)
            }
        })
        lopper_indicator.initIndicator(pagerAdapter.count)
        startAutoScroll()
    }

    private fun startAutoScroll() {
        Observable.interval(5, 5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.bindView(this))
                .subscribe({
                    loop_view_pager.currentItem = (loop_view_pager.currentItem + 1) % loop_view_pager.adapter.count
                })
    }

}