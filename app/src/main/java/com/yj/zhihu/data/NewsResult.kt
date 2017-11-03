package com.yj.zhihu.data

import android.support.annotation.Keep
import com.yj.zhihu.data.base.Page

/**
 * @author yuanjian 17/11/1.
 */
@Keep
data class NewsResult(
        var date: String,
        var stories: List<NewsItem>?,
        var top_stories: List<NewsItem>?
) : Page {
    override fun getPageCount(): Int {
        return stories?.size ?: 0
    }
}