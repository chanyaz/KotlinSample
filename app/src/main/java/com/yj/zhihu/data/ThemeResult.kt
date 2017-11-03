package com.yj.zhihu.data

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author yuanjian 17/10/31.
 */
@Keep
data class ThemeResult(
        @SerializedName("others")
        var themeList: List<Theme>
) {
    companion object {
        data class Theme(
                var color: Int,
                var thumbnail: String,
                var description: String,
                var id: Long,
                var name: String
        )
    }
}