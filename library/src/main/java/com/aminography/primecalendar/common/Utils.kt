package com.aminography.primecalendar.common

import java.util.*

/**
 * @author aminography
 */

internal const val delimiter = "/"

internal fun normalize(locale: Locale, number: Int): String = when (locale.language) {
    "fa" -> toPersianDigits(if (number <= 9) "0$number" else "$number")
    "ar" -> toPersianDigits(if (number <= 9) "0$number" else "$number")
    else -> if (number <= 9) "0$number" else "$number"
}

internal fun comma(locale: Locale): String = when (locale.language) {
    "fa" -> "،"
    "ar" -> "،"
    else -> ","
}

internal fun localizeNumber(locale: Locale, number: Int): String = when (locale.language) {
    "fa" -> toPersianDigits(number)
    "ar" -> toPersianDigits(number)
    else -> "$number"
}

private fun toPersianDigits(number: String): String {
    val sb = StringBuilder()
    for (i in number.toCharArray()) {
        if (Character.isDigit(i)) {
            sb.append(PERSIAN_DIGITS[Integer.parseInt(i.toString())])
        } else {
            sb.append(i)
        }
    }
    return sb.toString()
}

private fun toPersianDigits(number: Int): String {
    val sb = StringBuilder()
    for (i in "$number".toCharArray()) {
        if (Character.isDigit(i)) {
            sb.append(PERSIAN_DIGITS[Integer.parseInt(i.toString())])
        } else {
            sb.append(i)
        }
    }
    return sb.toString()
}

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
