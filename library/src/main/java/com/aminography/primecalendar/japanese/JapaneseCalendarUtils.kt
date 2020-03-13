package com.aminography.primecalendar.japanese

import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.japanese.JapaneseCalendar.Companion.DEFAULT_LOCALE
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.JapaneseDate
import org.threeten.bp.temporal.ChronoField
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
@Suppress("SpellCheckingInspection")
object JapaneseCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val normalMonthLength = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val leapYearMonthLength = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private val normalMonthLengthAggregated = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366)

    // Symbol Name Arrays --------------------------------------------------------------------------

    internal val monthNames = arrayOf(
        "1月", // Ichigatsu
        "2月", // Nigatsu
        "3月", // Sangatsu
        "4月", // Shigatsu
        "5月", // Gogatsu
        "6月", // Rokugatsu
        "7月", // Shichigatsu
        "8月", // Hachigatsu
        "9月", // Kugatsu
        "10月", // Jūgatsu
        "11月", // Jūichigatsu
        "12月" // Jūnigatsu
    )

    internal val weekDays = arrayOf(
        "土曜日", // Doyōbi
        "日曜日", // Nichiyōbi
        "月曜日", // Getsuyōbi
        "火曜日", // Kayōbi
        "水曜日", // Suiyōbi
        "木曜日", // Mokuyōbi
        "金曜日" // Kin'yōbi
    )

    internal val eras = arrayOf(
        "西暦", // AD
        "紀元前" // BC
    )

    internal val amPm = arrayOf(
        "午前", // AM
        "午後" // PM
    )

    internal val shortMonthNames = arrayOf(
        "1月", // Ichigatsu
        "2月", // Nigatsu
        "3月", // Sangatsu
        "4月", // Shigatsu
        "5月", // Gogatsu
        "6月", // Rokugatsu
        "7月", // Shichigatsu
        "8月", // Hachigatsu
        "9月", // Kugatsu
        "10月", // Jūgatsu
        "11月", // Jūichigatsu
        "12月" // Jūnigatsu
    )

    internal val shortWeekDays = arrayOf(
        "土", // Doyōbi
        "日", // Nichiyōbi
        "月", // Getsuyōbi
        "火", // Kayōbi
        "水", // Suiyōbi
        "木", // Mokuyōbi
        "金" // Kin'yōbi
    )

    internal val monthNamesEn = arrayOf(
        "Ichigatsu", // Ichigatsu
        "Nigatsu", // Nigatsu
        "Sangatsu", // Sangatsu
        "Shigatsu", // Shigatsu
        "Gogatsu", // Gogatsu
        "Rokugatsu", // Rokugatsu
        "Shichigatsu", // Shichigatsu
        "Hachigatsu", // Hachigatsu
        "Kugatsu", // Kugatsu
        "Jūgatsu", // Jūgatsu
        "Jūichigatsu", // Jūichigatsu
        "Jūnigatsu" // Jūnigatsu
    )

    internal val weekDaysEn = arrayOf(
        "Saturday", // Doyōbi
        "Sunday", // Nichiyōbi
        "Monday", // Getsuyōbi
        "Tuesday", // Kayōbi
        "Wednesday", // Suiyōbi
        "Thursday", // Mokuyōbi
        "Friday" // Kin'yōbi
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
        "Ichi", // Ichigatsu
        "Ni", // Nigatsu
        "San", // Sangatsu
        "Shi", // Shigatsu
        "Go", // Gogatsu
        "Roku", // Rokugatsu
        "Shichi", // Shichigatsu
        "Hachi", // Hachigatsu
        "Ku", // Kugatsu
        "Jū", // Jūgatsu
        "Jūichi", // Jūichigatsu
        "Jūni" // Jūnigatsu
    )

    internal val shortWeekDaysEn = arrayOf(
        "Sa", // Doyōbi
        "Su", // Nichiyōbi
        "Mo", // Getsuyōbi
        "Tu", // Kayōbi
        "We", // Suiyōbi
        "Th", // Mokuyōbi
        "Fr" // Kin'yōbi
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
        if (isJapaneseLeapYear(year))
            leapYearMonthLength[month]
        else normalMonthLength[month]

    fun yearLength(year: Int): Int =
        if (isJapaneseLeapYear(year))
            leapYearMonthLengthAggregated[12]
        else normalMonthLengthAggregated[12]

    internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
        if (isJapaneseLeapYear(year))
            leapYearMonthLengthAggregated[month] + dayOfMonth
        else normalMonthLengthAggregated[month] + dayOfMonth

    internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
        val monthLengthAggregated = if (isJapaneseLeapYear(year))
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

    internal fun isJapaneseLeapYear(year: Int): Boolean =
        ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

    // Conversion Methods --------------------------------------------------------------------------

    internal fun gregorianToJapanese(gregorian: DateHolder): DateHolder {
        val gregorianDate = LocalDate.of(gregorian.year, gregorian.month + 1, gregorian.dayOfMonth)
        val japaneseDate = JapaneseDate.from(gregorianDate)
        return DateHolder(japaneseDate.get(ChronoField.YEAR), japaneseDate.get(ChronoField.MONTH_OF_YEAR) - 1, japaneseDate.get(ChronoField.DAY_OF_MONTH))
    }

    internal fun japaneseToGregorian(japanese: DateHolder): DateHolder {
        val japaneseDate = JapaneseDate.of(japanese.year, japanese.month + 1, japanese.dayOfMonth)
        val gregorianDate = LocalDate.from(japaneseDate)
        return DateHolder(gregorianDate.get(ChronoField.YEAR), gregorianDate.get(ChronoField.MONTH_OF_YEAR) - 1, gregorianDate.get(ChronoField.DAY_OF_MONTH))
    }

}