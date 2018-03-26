package com.thebrodyaga.vkurse.common

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */

fun getDate(milli: Long): String {
    return when {
        isDeltaHour(milli, DateUtils.HOUR_IN_MILLIS * 3) -> DateUtils.getRelativeTimeSpanString(milli, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString()
        DateUtils.isToday(milli) -> SimpleDateFormat("'Сегодня в' HH:mm", Locale.getDefault()).format(Date(milli))
        isYesterday(milli) -> SimpleDateFormat("'Вчера в' HH:mm", Locale.getDefault()).format(Date(milli))
        else -> SimpleDateFormat("d MMM 'в' HH:mm", Locale.getDefault()).format(Date(milli))
    }
}

fun isYesterday(milli: Long): Boolean {
    val time = GregorianCalendar.getInstance()
    time.timeInMillis = milli
    val thenYear = time.get(GregorianCalendar.YEAR)
    val thenMonth = time.get(GregorianCalendar.MONTH)
    val thenMonthDay = time.get(GregorianCalendar.DAY_OF_MONTH)

    time.timeInMillis = (System.currentTimeMillis() - DateUtils.DAY_IN_MILLIS)
    return (thenYear == time.get(GregorianCalendar.YEAR)
            && thenMonth == time.get(GregorianCalendar.MONTH)
            && thenMonthDay == time.get(GregorianCalendar.DAY_OF_MONTH))
}

/**
 * Проверка вхождения в временной интервал
 */
fun isDeltaHour(milli: Long, delta: Long): Boolean {
    return milli > System.currentTimeMillis() - delta
}