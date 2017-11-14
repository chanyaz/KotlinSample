package com.yj.zhihu.data

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author yuanjian 17/11/14.
 */
@Keep
data class NewsExtra(
        @SerializedName("long_comments")
        var longComments: Int,
        var popularity: Int,
        @SerializedName("short_comments")
        var shortComments: Int,
        var comments: Int
)