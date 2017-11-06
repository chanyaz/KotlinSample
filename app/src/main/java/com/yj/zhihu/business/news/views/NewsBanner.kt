package com.yj.zhihu.business.news.views

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import com.yj.zhihu.R
import com.yj.zhihu.common.views.LoopCircleIndicator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @author yuanjian 17/11/3.
 */
class NewsBanner : RelativeLayout {

    private lateinit var loopCircleIndicator: LoopCircleIndicator
    private lateinit var viewPager: ViewPager

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
        viewPager = findViewById(R.id.loop_view_pager)
        loopCircleIndicator = findViewById(R.id.lopper_indicator)
    }

    fun hasData(): Boolean {
        return viewPager.adapter != null
    }

    fun setData(pagerAdapter: PagerAdapter, currentItem: Int) {
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = currentItem
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                loopCircleIndicator.scrollIndicator(position, positionOffset)
            }
        })
        loopCircleIndicator.initIndicator(pagerAdapter.count)
        startAutoScroll()
    }

    private fun startAutoScroll() {
//        if (disposable == null || disposable!!.isDisposed) {
        Observable.interval(5, 5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.bindView(this))
                .subscribe({
                    viewPager.currentItem = (viewPager.currentItem + 1) % viewPager.adapter.count
                })
    }

}