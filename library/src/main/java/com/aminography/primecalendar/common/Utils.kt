package com.aminography.primecalendar.common

import java.util.*

/**
 * @author aminography
 */

internal const val delimiter = "/"
internal const val FA = "fa"
internal const val AR = "ar"

internal fun normalize(locale: Locale, number: Int): String = when (locale.language) {
    FA -> (if (number <= 9) "0$number" else "$number").withPersianDigits
    AR -> (if (number <= 9) "0$number" else "$number").withArabicDigits
    else -> if (number <= 9) "0$number" else "$number"
}

internal fun comma(locale: Locale): String = when (locale.language) {
    FA -> "،"
    AR -> "،"
    else -> ","
}

internal fun localizeNumber(locale: Locale, number: Int): String = when (locale.language) {
    FA -> number.withPersianDigits
    AR -> number.withArabicDigits
    else -> "$number"
}

internal val Number.withPersianDigits: String
    get() = "$this".withPersianDigits

internal val String.withPersianDigits: String
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

internal val Number.withArabicDigits: String
    get() = "$this".withArabicDigits

internal val String.withArabicDigits: String
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
