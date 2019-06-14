package com.aminography.primecalendar.persian

import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.persian.PersianCalendar.Companion.DEFAULT_LOCALE
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
object PersianCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val gregorianMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val normalMonthLength = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)
    private val leapYearMonthLength = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30)

    private val normalMonthLengthAggregated = intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 365)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 366)

    // Symbol Name Arrays --------------------------------------------------------------------------

    internal val monthNames = arrayOf(
            "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646", // Farvardin
            "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a", // Ordibehesht
            "\u062e\u0631\u062f\u0627\u062f", // Khordad
            "\u062a\u06cc\u0631", // Tir
            "\u0645\u0631\u062f\u0627\u062f", // Mordad
            "\u0634\u0647\u0631\u06cc\u0648\u0631", // Shahrivar
            "\u0645\u0647\u0631", // Mehr
            "\u0622\u0628\u0627\u0646", // Aban
            "\u0622\u0630\u0631", // Azar
            "\u062f\u06cc", // Dey
            "\u0628\u0647\u0645\u0646", // Bahman
            "\u0627\u0633\u0641\u0646\u062f" // Esfand
    )

    internal val weekDays = arrayOf(
            "\u0634\u0646\u0628\u0647", // Shanbeh
            "\u06cc\u06a9\u200c\u0634\u0646\u0628\u0647", // Yekshanbeh
            "\u062f\u0648\u0634\u0646\u0628\u0647", // Doshanbeh
            "\u0633\u0647\u200c\u0634\u0646\u0628\u0647", // Sehshanbeh
            "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647", // Chaharshanbeh
            "\u067e\u0646\u062c\u200c\u0634\u0646\u0628\u0647", // Panjshanbeh
            "\u062c\u0645\u0639\u0647" // jom'e
    )

    internal val eras = arrayOf(
            "\u0642\u0628\u0644\u0020\u0627\u0632\u0020\u0645\u06cc\u0644\u0627\u062f", // AD
            "\u0628\u0639\u062f\u0020\u0627\u0632\u0020\u0645\u06cc\u0644\u0627\u062f" // BC
    )

    internal val amPm = arrayOf(
            "\u0642\u0628\u0644\u0020\u0627\u0632\u0020\u0638\u0647\u0631", // AM
            "\u0628\u0639\u062f\u0020\u0627\u0632\u0020\u0638\u0647\u0631" // PM
    )

    internal val shortMonthNames = arrayOf(
            "\u0641\u0631", // Farvardin
            "\u0627\u0631\u062f", // Ordibehesht
            "\u062e\u0631\u062f", // Khordad
            "\u062a\u06cc\u0631", // Tir
            "\u0645\u0631", // Mordad
            "\u0634\u0647\u0631", // Shahrivar
            "\u0645\u0647\u0631", // Mehr
            "\u0622\u0628", // Aban
            "\u0622\u0630\u0631", // Azar
            "\u062f\u06cc", // Dey
            "\u0628\u0647", // Bahman
            "\u0627\u0633" // Esfand
    )

    internal val shortWeekDays = arrayOf(
            "\u0634", // Shanbeh
            "\u06cc", // Yekshanbeh
            "\u062f", // Doshanbeh
            "\u0633", // Sehshanbeh
            "\u0686", // Chaharshanbeh
            "\u067e", // Panjshanbeh
            "\u062c" // jom'e
    )

    internal val monthNamesEn = arrayOf(
            "Farvardin", // Farvardin
            "Ordibehesht", // Ordibehesht
            "Khordad", // Khordad
            "Tir", // Tir
            "Mordad", // Mordad
            "Shahrivar", // Shahrivar
            "Mehr", // Mehr
            "Aban", // Aban
            "Azar", // Azar
            "Dey", // Dey
            "Bahman", // Bahman
            "Esfand" // Esfand
    )

    internal val weekDaysEn = arrayOf(
            "Saturday", // Shanbeh
            "Sunday", // Yekshanbeh
            "Monday", // Doshanbeh
            "Tuesday", // Sehshanbeh
            "Wednesday", // Chaharshanbeh
            "Thursday", // Panjshanbeh
            "Friday" // jom'e
    )

    internal val erasEn = arrayOf(
            "AD", // AD
            "BC" // BC
    )

    internal val amPmEn = arrayOf(
            "AM", // AM
            "PM" // PM
    )

    internal val shortMonthNamesEn = arrayOf(
            "Far", // Farvardin
            "Ord", // Ordibehesht
            "Kho", // Khordad
            "Tir", // Tir
            "Mor", // Mordad
            "Sha", // Shahrivar
            "Meh", // Mehr
            "Aba", // Aban
            "Aza", // Azar
            "Dey", // Dey
            "Bah", // Bahman
            "Esf" // Esfand
    )

    internal val shortWeekDaysEn = arrayOf(
            "Sa", // Shanbeh
            "Su", // Yekshanbeh
            "Mo", // Doshanbeh
            "Tu", // Sehshanbeh
            "We", // Chaharshanbeh
            "Th", // Panjshanbeh
            "Fr" // jom'e
    )

    fun monthName(month: Int, locale: Locale): String {
        return when (locale.language) {
            DEFAULT_LOCALE -> monthNames[month]
            else -> monthNamesEn[month]
        }
    }

    fun shortMonthName(month: Int, locale: Locale): String {
        return when (locale.language) {
            DEFAULT_LOCALE -> shortMonthNames[month]
            else -> shortMonthNamesEn[month]
        }
    }

    fun weekDayName(weekDay: Int, locale: Locale): String {
        val array = when (locale.language) {
            DEFAULT_LOCALE -> weekDays
            else -> weekDaysEn
        }
        return when (weekDay) {
            SATURDAY -> array[0]
            SUNDAY -> array[1]
            MONDAY -> array[2]
            TUESDAY -> array[3]
            WEDNESDAY -> array[4]
            THURSDAY -> array[5]
            FRIDAY -> array[6]
            else -> throw IllegalArgumentException()
        }
    }

    fun shortWeekDayName(weekDay: Int, locale: Locale): String {
        val array = when (locale.language) {
            DEFAULT_LOCALE -> shortWeekDays
            else -> shortWeekDaysEn
        }
        return when (weekDay) {
            SATURDAY -> array[0]
            SUNDAY -> array[1]
            MONDAY -> array[2]
            TUESDAY -> array[3]
            WEDNESDAY -> array[4]
            THURSDAY -> array[5]
            FRIDAY -> array[6]
            else -> throw IllegalArgumentException()
        }
    }

    // Internal Calculation Methods ----------------------------------------------------------------

    fun monthLength(year: Int, month: Int): Int =
            if (isPersianLeapYear(year))
                leapYearMonthLength[month]
            else normalMonthLength[month]

    fun yearLength(year: Int): Int =
            if (isPersianLeapYear(year))
                leapYearMonthLengthAggregated[12]
            else normalMonthLengthAggregated[12]

    internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
            if (isPersianLeapYear(year))
                leapYearMonthLengthAggregated[month] + dayOfMonth
            else normalMonthLengthAggregated[month] + dayOfMonth

    internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
        val monthLengthAggregated = if (isPersianLeapYear(year))
            leapYearMonthLengthAggregated
        else normalMonthLengthAggregated

        var month = 0
        for (i in 0 until monthLengthAggregated.size) {
            if (dayOfYear > monthLengthAggregated[i] && dayOfYear <= monthLengthAggregated[i + 1]) {
                month = i
            }
        }
        val dayOfMonth = dayOfYear - monthLengthAggregated[month]
        return DateHolder(year, month, dayOfMonth)
    }

    internal fun isPersianLeapYear(persianYear: Int): Boolean =
            ceil((38.0 + (ceil((persianYear - 474L).toDouble(), 2820.0) + 474L)) * 682.0, 2816.0) < 682L

    private fun ceil(double1: Double, double2: Double): Long {
        return (double1 - double2 * Math.floor(double1 / double2)).toLong()
    }

    // Conversion Methods --------------------------------------------------------------------------

    @Suppress("ReplaceWithOperatorAssignment", "JoinDeclarationAndAssignment")
    internal fun gregorianToPersian(gregorian: DateHolder): DateHolder {

        if (gregorian.month > 11 || gregorian.month < -11) {
            throw IllegalArgumentException()
        }

        var persianYear: Int
        val persianMonth: Int
        val persianDay: Int

        var gregorianDayNo: Int
        var persianDayNo: Int
        val persianNP: Int
        var i: Int

        gregorian.year = gregorian.year - 1600
        gregorian.dayOfMonth = gregorian.dayOfMonth - 1

        gregorianDayNo = 365 * gregorian.year + Math.floor(((gregorian.year + 3) / 4).toDouble()).toInt() - Math.floor(((gregorian.year + 99) / 100).toDouble()).toInt() + Math.floor(((gregorian.year + 399) / 400).toDouble()).toInt()
        i = 0
        while (i < gregorian.month) {
            gregorianDayNo += gregorianMonthLength[i]
            ++i
        }

        if (gregorian.month > 1 && (gregorian.year % 4 == 0 && gregorian.year % 100 != 0 || gregorian.year % 400 == 0)) {
            ++gregorianDayNo
        }

        gregorianDayNo += gregorian.dayOfMonth

        persianDayNo = gregorianDayNo - 79

        persianNP = Math.floor((persianDayNo / 12053).toDouble()).toInt()
        persianDayNo = persianDayNo % 12053

        persianYear = 979 + 33 * persianNP + 4 * (persianDayNo / 1461)
        persianDayNo = persianDayNo % 1461

        if (persianDayNo >= 366) {
            persianYear += Math.floor(((persianDayNo - 1) / 365).toDouble()).toInt()
            persianDayNo = (persianDayNo - 1) % 365
        }

        i = 0
        while (i < 11 && persianDayNo >= normalMonthLength[i]) {
            persianDayNo -= normalMonthLength[i]
            ++i
        }
        persianMonth = i
        persianDay = persianDayNo + 1

        return DateHolder(persianYear, persianMonth, persianDay)
    }

    @Suppress("ReplaceWithOperatorAssignment", "JoinDeclarationAndAssignment")
    internal fun persianToGregorian(persian: DateHolder): DateHolder {

        if (persian.month > 11 || persian.month < -11) {
            throw IllegalArgumentException()
        }

        var gregorianYear: Int
        val gregorianMonth: Int
        val gregorianDay: Int

        var gregorianDayNo: Int
        var persianDayNo: Int
        var leap: Int

        var i: Int
        persian.year = persian.year - 979
        persian.dayOfMonth = persian.dayOfMonth - 1

        persianDayNo = 365 * persian.year + (persian.year / 33) * 8 + Math.floor(((persian.year % 33 + 3) / 4).toDouble()).toInt()
        i = 0
        while (i < persian.month) {
            persianDayNo += normalMonthLength[i]
            ++i
        }

        persianDayNo += persian.dayOfMonth

        gregorianDayNo = persianDayNo + 79

        gregorianYear = 1600 + 400 * Math.floor((gregorianDayNo / 146097).toDouble()).toInt() /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        gregorianDayNo = gregorianDayNo % 146097

        leap = 1
        if (gregorianDayNo >= 36525)
        /* 36525 = 365*100 + 100/4 */ {
            gregorianDayNo--
            gregorianYear += 100 * Math.floor((gregorianDayNo / 36524).toDouble()).toInt() /* 36524 = 365*100 + 100/4 - 100/100 */
            gregorianDayNo = gregorianDayNo % 36524

            if (gregorianDayNo >= 365) {
                gregorianDayNo++
            } else {
                leap = 0
            }
        }

        gregorianYear += 4 * Math.floor((gregorianDayNo / 1461).toDouble()).toInt() /* 1461 = 365*4 + 4/4 */
        gregorianDayNo = gregorianDayNo % 1461

        if (gregorianDayNo >= 366) {
            leap = 0

            gregorianDayNo--
            gregorianYear += Math.floor((gregorianDayNo / 365).toDouble()).toInt()
            gregorianDayNo = gregorianDayNo % 365
        }

        i = 0
        while (gregorianDayNo >= gregorianMonthLength[i] + if (i == 1 && leap == 1) i else 0) {
            gregorianDayNo -= gregorianMonthLength[i] + if (i == 1 && leap == 1) i else 0
            i++
        }
        gregorianMonth = i
        gregorianDay = gregorianDayNo + 1

        return DateHolder(gregorianYear, gregorianMonth, gregorianDay)
    }

}