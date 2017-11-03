package com.yj.zhihu.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author yuanjian 17/11/1.
 */
object DateTimeUtils {

    val GMT_8 = "GMT+08:00"
    val ONE_SECOND = 1000L
    val ONE_MINUTE = 60 * ONE_SECOND
    val ONE_HOUR = 60 * ONE_MINUTE
    val HALF_DAY = 12 * ONE_HOUR
    val ONE_DAY = 24 * ONE_HOUR
    val ONE_WEEK = 7 * ONE_DAY
    val DAY_FORMAT = "yyyyMMdd"

    fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    fun buildSimpleDateFormat(format: String): SimpleDateFormat {
        return SimpleDateFormat(format, Locale.CHINA).apply {
            timeZone = TimeZone.getTimeZone(GMT_8)
        }
    }

    fun getDateByString(time: String): Date {
        return try {
            buildSimpleDateFormat(DAY_FORMAT).parse(time)
        } catch (e: ParseException) {
            Date(getCurrentTimeMillis())
        }
    }

    fun getDayString(timeInMillis: Long): String {
        return buildSimpleDateFormat(DAY_FORMAT).format(timeInMillis)
    }

//    fun getNextDayStr(today: String): String {
//        val date = getDateByString(today)
//        return buildSimpleDateFormat(DAY_FORMAT).format(date.time + ONE_DAY)
//    }
//
//    fun getPreDayString(today: String): String {
//        val date = getDateByString(today)
//        return buildSimpleDateFormat(DAY_FORMAT).format(date.time - ONE_DAY)
//    }
}