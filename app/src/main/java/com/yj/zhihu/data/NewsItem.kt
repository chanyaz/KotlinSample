package com.yj.zhihu.data

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.yj.zhihu.common.extensions.isNullOrEmpty
import java.io.Serializable

/**
 * @author yuanjian 17/11/1.
 */
@Keep
data class NewsItem(
        var image: String?,
        var images: List<String>?,
        var type: Int,
        var id: Long,
        var title: String,
        @SerializedName("multipic")
        var multiPic: Boolean
) : Serializable {
    fun getShowFirstFromImages(): String? {
        return if (images.isNullOrEmpty()) null else images!![0]
    }
}