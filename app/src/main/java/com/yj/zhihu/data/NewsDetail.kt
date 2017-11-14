package com.yj.zhihu.data

import android.support.annotation.Keep

/**
 * @author yuanjian 17/11/9.
 */
@Keep
data class NewsDetail(
        var body: String,
        var image_source: String,
        var title: String,
        var image: String?,
        //js:[]
        var ga_prefix: String,
        var images: List<String>,
        var type: Int,
        var id: Long,
        var css: List<String>
) {
    fun getShowImage(): String {
        return image ?: images[0]
    }
}