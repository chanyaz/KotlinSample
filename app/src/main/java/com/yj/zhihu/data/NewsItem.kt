package com.yj.zhihu.data

import android.support.annotation.Keep
import com.yj.zhihu.common.extensions.isNullOrEmpty

/**
 * @author yuanjian 17/11/1.
 */
@Keep
data class NewsItem(
        var image: String?,
        var images: List<String>?,
        var type: Int,
        var id: Long,
        var title: String
) {
    fun getShowFirstFromImages(): String? {
        return if (images.isNullOrEmpty()) null else images!![0]
    }
}