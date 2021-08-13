package com.aminography.primecalendar.persian

import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.persian.PersianCalendar.Companion.DARI_AF
import com.aminography.primecalendar.persian.PersianCalendar.Companion.DEFAULT_LOCALE
import com.aminography.primecalendar.persian.PersianCalendar.Companion.FARSI_AF
import java.util.*
import java.util.Calendar.*
import kotlin.math.floor

/**
 * @author aminography
 */
@Suppress("SpellCheckingInspection")
object PersianCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val gregorianMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val normalMonthLength = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)
    private val leapYearMonthLength = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30)

    private val normalMonthLengthAggregated = intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 365)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 366)

    // Symbol Name Arrays --------------------------------------------------------------------------

    internal val monthNames = arrayOf(
        "فروردین", // Farvardin
        "اردیبهشت", // Ordibehesht
        "خرداد", // Khordad
        "تیر", // Tir
        "مرداد", // Mordad
        "شهریور", // Shahrivar
        "مهر", // Mehr
        "آبان", // Aban
        "آذر", // Azar
        "دی", // Dey
        "بهمن", // Bahman
        "اسفند" // Esfand
    )

    internal val weekDays = arrayOf(
        "شنبه", // Shanbeh
        "یکشنبه", // Yekshanbeh
        "دوشنبه", // Doshanbeh
        "سه\u200Cشنبه", // Sehshanbeh
        "چهارشنبه", // Chaharshanbeh
        "پنجشنبه", // Panjshanbeh
        "جمعه" // jom'e
    )

    internal val eras = arrayOf(
        "قبل از میلاد", // AD
        "بعد از میلاد" // BC
    )

    internal val amPm = arrayOf(
        "قبل از ظهر", // AM
        "بعد از ظهر" // PM
    )

    internal val shortMonthNames = arrayOf(
        "فر", // Farvardin
        "ارد", // Ordibehesht
        "خرد", // Khordad
        "تیر", // Tir
        "مر", // Mordad
        "شهر", // Shahrivar
        "مهر", // Mehr
        "آب", // Aban
        "آذر", // Azar
        "دی", // Dey
        "به", // Bahman
        "اس" // Esfand
    )

    internal val shortWeekDays = arrayOf(
        "ش", // Shanbeh
        "ی", // Yekshanbeh
        "د", // Doshanbeh
        "س", // Sehshanbeh
        "چ", // Chaharshanbeh
        "پ", // Panjshanbeh
        "ج" // jom'e
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


    internal val monthNamesAf = arrayOf(
        "حمل", // Farvardin
        "ثور", // Ordibehesht
        "جوزا", // Khordad
        "سرطان", // Tir
        "اسد", // Mordad
        "سنبله", // Shahrivar
        "میزان", // Mehr
        "عقرب", // Aban
        "قوس", // Azar
        "جدی", // Dey
        "دلو", // Bahman
        "حوت" // Esfand
    )

    fun monthName(month: Int, locale: Locale): String {
        return when (locale.language) {
            DEFAULT_LOCALE -> monthNames[month]
            FARSI_AF, DARI_AF -> monthNamesAf[month]
            else -> monthNamesEn[month]
        }
    }

    fun shortMonthName(month: Int, locale: Locale): String {
        return when (locale.language) {
            DEFAULT_LOCALE -> shortMonthNames[month]
            FARSI_AF, DARI_AF -> monthNamesAf[month]
            else -> shortMonthNamesEn[month]
        }
    }

    fun weekDayName(weekDay: Int, locale: Locale): String {
        val array = when (locale.language) {
            DEFAULT_LOCALE, FARSI_AF, DARI_AF -> weekDays
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
            DEFAULT_LOCALE, FARSI_AF, DARI_AF  -> shortWeekDays
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
        for (i in monthLengthAggregated.indices) {
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
        return (double1 - double2 * floor(double1 / double2)).toLong()
    }

    // Conversion Methods --------------------------------------------------------------------------
    // Based on: Calendrical Calculations by Edward M. Reingold

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

        gregorianDayNo = 365 * gregorian.year + floor(((gregorian.year + 3) / 4).toDouble()).toInt() - floor(((gregorian.year + 99) / 100).toDouble()).toInt() + floor(((gregorian.year + 399) / 400).toDouble()).toInt()
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

        persianNP = floor((persianDayNo / 12053).toDouble()).toInt()
        persianDayNo = persianDayNo % 12053

        persianYear = 979 + 33 * persianNP + 4 * (persianDayNo / 1461)
        persianDayNo = persianDayNo % 1461

        if (persianDayNo >= 366) {
            persianYear += floor(((persianDayNo - 1) / 365).toDouble()).toInt()
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

        persianDayNo = 365 * persian.year + (persian.year / 33) * 8 + floor(((persian.year % 33 + 3) / 4).toDouble()).toInt()
        i = 0
        while (i < persian.month) {
            persianDayNo += normalMonthLength[i]
            ++i
        }

        persianDayNo += persian.dayOfMonth

        gregorianDayNo = persianDayNo + 79

        gregorianYear = 1600 + 400 * floor((gregorianDayNo / 146097).toDouble()).toInt() /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        gregorianDayNo = gregorianDayNo % 146097

        leap = 1
        if (gregorianDayNo >= 36525)
        /* 36525 = 365*100 + 100/4 */ {
            gregorianDayNo--
            gregorianYear += 100 * floor((gregorianDayNo / 36524).toDouble()).toInt() /* 36524 = 365*100 + 100/4 - 100/100 */
            gregorianDayNo = gregorianDayNo % 36524

            if (gregorianDayNo >= 365) {
                gregorianDayNo++
            } else {
                leap = 0
            }
        }

        gregorianYear += 4 * floor((gregorianDayNo / 1461).toDouble()).toInt() /* 1461 = 365*4 + 4/4 */
        gregorianDayNo = gregorianDayNo % 1461

        if (gregorianDayNo >= 366) {
            leap = 0

            gregorianDayNo--
            gregorianYear += floor((gregorianDayNo / 365).toDouble()).toInt()
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