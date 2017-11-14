package com.yj.zhihu.common.utils

import android.content.Intent
import android.net.Uri

/**
 * @author yuanjian 17/11/9.
 */
class UriBuilder {
    companion object {
        private val URI_SCHEME = "yj"
        private val URI_AUTHORITY = "zh"
        private val HOST = "$URI_SCHEME://$URI_AUTHORITY"
    }

    private var builder: Uri.Builder

    constructor(actionSuffix: String) {
        builder = Uri.Builder().scheme(URI_SCHEME).authority(URI_AUTHORITY).appendEncodedPath(actionSuffix)
    }

    constructor(uri: Uri) {
        builder = uri.buildUpon()
    }

    fun appendParam(param: String, value: Any): UriBuilder {
        builder.appendQueryParameter(param, value.toString())
        return this
    }

    fun build(): Uri {
        return builder.build()
    }

    fun toIntent(): Intent {
        return Intent().addCategory(Intent.CATEGORY_DEFAULT).setAction(Intent.ACTION_VIEW).setData(builder.build())
    }

}