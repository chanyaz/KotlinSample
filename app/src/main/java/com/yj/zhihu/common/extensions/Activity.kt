package com.yj.zhihu.common.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.net.Uri
import com.yj.zhihu.common.utils.UriBuilder

/**
 * @author yuanjian 17/11/9.
 */
fun Activity.startActivity(url: String) {
    try {
        startActivity(UriBuilder(Uri.parse(url)).toIntent())
    } catch (e: ActivityNotFoundException) {
    }
}

fun Activity.startActivityByPath(path: String) {
    try {
        startActivity(UriBuilder(path).toIntent())
    } catch (e: ActivityNotFoundException) {
    }
}