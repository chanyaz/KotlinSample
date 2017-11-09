package com.yj.zhihu.business.news.adpter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yj.zhihu.R
import com.yj.zhihu.common.utils.ImageUtils
import com.yj.zhihu.data.NewsItem
import kotlinx.android.synthetic.main.yj_layout_banner_item.view.*

/**
 * @author yuanjian 17/11/3.
 */
class BannerPagerAdapter(val context: Context, private var items: List<NewsItem>, private val callback: ((NewsItem) -> Unit)) : PagerAdapter() {

    private val layoutInflater = LayoutInflater.from(context)


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.yj_layout_banner_item, container, false)
        ImageUtils.loadImage(context, items[position].image,  view.banner_img, true)
        view.desc.text = items[position].title
        container?.addView(view)
        view.setOnClickListener {
            callback.invoke(items[position]) }
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return `object` == view
    }

    override fun getCount(): Int {
        return items.size
    }
}