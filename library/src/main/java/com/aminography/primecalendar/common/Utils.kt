package com.aminography.primecalendar.common

import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.*

/**
 * @author aminography
 */

internal const val delimiter = "/"

internal fun normalize(locale: Locale, number: Int): String = when (locale.language) {
    PersianCalendar.DEFAULT_LOCALE, PersianCalendar.FARSI_AFGHAN_LOCALE,
        PersianCalendar.PASHTO_LOCALE, PersianCalendar.KURDISH_LOCALE ->
        (if (number <= 9) "0$number" else "$number").withPersianDigits
    HijriCalendar.DEFAULT_LOCALE -> (if (number <= 9) "0$number" else "$number").withArabicDigits
    else -> if (number <= 9) "0$number" else "$number"
}

internal fun comma(locale: Locale): String = when (locale.language) {
    PersianCalendar.DEFAULT_LOCALE, PersianCalendar.FARSI_AFGHAN_LOCALE,
        PersianCalendar.PASHTO_LOCALE, PersianCalendar.KURDISH_LOCALE -> "،"
    HijriCalendar.DEFAULT_LOCALE -> "،"
    else -> ","
}

fun Number.localizeDigits(locale: Locale): String =
    "$this".localizeDigits(locale)

fun String.localizeDigits(locale: Locale): String =
    when (locale.language) {
        PersianCalendar.DEFAULT_LOCALE, PersianCalendar.FARSI_AFGHAN_LOCALE,
            PersianCalendar.PASHTO_LOCALE, PersianCalendar.KURDISH_LOCALE -> withPersianDigits
        HijriCalendar.DEFAULT_LOCALE -> withArabicDigits
        else -> this
    }

val Number.withPersianDigits: String
    get() = "$this".withPersianDigits

val String.withPersianDigits: String
    get() = StringBuilder().also { builder ->
        toCharArray().forEach {
            builder.append(
                when {
                    Character.isDigit(it) -> PERSIAN_DIGITS["$it".toInt()]
                    it == '.' -> "/"
                    else -> it
                }
            )
        }
    }.toString()

val Number.withArabicDigits: String
    get() = "$this".withArabicDigits

val String.withArabicDigits: String
    get() = StringBuilder().also { builder ->
        toCharArray().forEach {
            builder.append(
                when {
                    Character.isDigit(it) -> ARABIC_DIGITS["$it".toInt()]
                    it == '.' -> "/"
                    else -> it
                }
            )
        }
    }.toString()

private val PERSIAN_DIGITS = charArrayOf(
    '0' + 1728,
    '1' + 1728,
    '2' + 1728,
    '3' + 1728,
    '4' + 1728,
    '5' + 1728,
    '6' + 1728,
    '7' + 1728,
    '8' + 1728,
    '9' + 1728
)

private val ARABIC_DIGITS = charArrayOf(
    '0' + 1584,
    '1' + 1584,
    '2' + 1584,
    '3' + 1584,
    '4' + 1584,
    '5' + 1584,
    '6' + 1584,
    '7' + 1584,
    '8' + 1584,
    '9' + 1584
)
