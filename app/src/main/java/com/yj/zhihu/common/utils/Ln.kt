package com.yj.zhihu.common.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log


/**
 * @author yuanjian 17/11/3.
 */
object Ln {
    var isDebug = false

    fun init(context: Context) {
        isDebug = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    fun d(s: Any) {
        if (isDebug) {
            Log.println(Log.DEBUG, getTag(), "  " + s.toString())
        }
    }

    fun e(s: Any) {
        Log.println(Log.ERROR, getTag(), "  " + s.toString())
    }

    private fun getTag(): String {
        val skipDepth = 4 // skip 6 stackframes to find the location where this was called
        if (isDebug) {
            val elements = Thread.currentThread().stackTrace
            val trace = elements[skipDepth]
            return trace.fileName + ":" + trace.lineNumber
        }
        return ""
    }
}