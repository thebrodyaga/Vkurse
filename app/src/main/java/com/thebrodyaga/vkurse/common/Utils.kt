package com.thebrodyaga.vkurse.common

import android.app.Activity
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */

fun getDate(milli: Long): String =
        when {
            isDeltaHour(milli, DateUtils.HOUR_IN_MILLIS * 3) -> DateUtils.getRelativeTimeSpanString(milli, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString()
            DateUtils.isToday(milli) -> SimpleDateFormat("'Сегодня в' HH:mm", Locale.getDefault()).format(Date(milli))
            isYesterday(milli) -> SimpleDateFormat("'Вчера в' HH:mm", Locale.getDefault()).format(Date(milli))
            else -> SimpleDateFormat("d MMM 'в' HH:mm", Locale.getDefault()).format(Date(milli))
        }

/**
 * Сейчас не поддерживает разрешение static method Java class
 * https://youtrack.jetbrains.com/issue/KT-11968
 */
fun /*DateUtils.*/isYesterday(milli: Long)
        : Boolean = DateUtils.isToday(milli + DateUtils.DAY_IN_MILLIS)

/**
 * Проверка вхождения в временной интервал
 */
fun isDeltaHour(milli: Long, delta: Long): Boolean {
    return milli > System.currentTimeMillis() - delta
}

fun debugLogging(message: String) {
    Log.d(DEBUG_TAG, message)
}

fun hideKeyboardFrom(fragment: Fragment) {
    val context = fragment.context ?: return
    val view = fragment.activity?.currentFocus ?: return
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}