package com.aminography.primecalendar.japanese

import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.hijri.HijriCalendar.Companion.DEFAULT_LOCALE
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.HijrahDate
import org.threeten.bp.temporal.ChronoField
import java.util.*
import java.util.Calendar.*

/**
 * @author aminography
 */
object JapaneseCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val normalMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29)
    private val leapYearMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30)

    private val normalMonthLengthAggregated = intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 354)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 355)

    // Symbol Name Arrays --------------------------------------------------------------------------

    internal val monthNames = arrayOf(
            "\u0645\u062D\u0631\u0645", // Muharram
            "\u0635\u0641\u0631", // Safar
            "\u0631\u0628\u064A\u0639\u0020\u0627\u0644\u0623\u0648\u0644", // Rabiʿ al-Awwal
            "\u0631\u0628\u064A\u0639\u0020\u0627\u0644\u062B\u0627\u0646\u064A", // Rabiʿ ath-Thani
            "\u062C\u0645\u0627\u062F\u0649\u0020\u0627\u0644\u0623\u0648\u0644\u0649", // Jumada al-Ula
            "\u062C\u0645\u0627\u062F\u0649\u0020\u0627\u0644\u0622\u062E\u0631\u0629", // Jumada al-Akhirah
            "\u0631\u062C\u0628", // Rajab
            "\u0634\u0639\u0628\u0627\u0646", // Sha'ban
            "\u0631\u0645\u0636\u0627\u0646", // Ramadan
            "\u0634\u0648\u0627\u0644", // Shawwal
            "\u0630\u0648\u0020\u0627\u0644\u0642\u0639\u062F\u0629", // Dhu al-Qa'dah
            "\u0630\u0648\u0020\u0627\u0644\u062D\u062C\u0629" // Dhu al-Hijjah
    )

    internal val weekDays = arrayOf(
            "日曜日", // nichiyōbi
            "月曜日", // getsuyōbi
            "火曜日", // kayōbi
            "水曜日", // suiyōbi
            "木曜日", // mokuyōbi
            "金曜日", // kin'yōbi
            "土曜日" // doyōbi
    )

    internal val eras = arrayOf(
            "\u0628\u0639\u062f\u0020\u0627\u0644\u0645\u064a\u0644\u0627\u062f", // AD
            "\u0642\u0628\u0644\u0020\u0627\u0644\u0645\u064a\u0644\u0627\u062f" // BC
    )

    internal val amPm = arrayOf(
            "\u0642\u0628\u0644\u0020\u0627\u0644\u0638\u0647\u0631", // AM
            "\u0628\u0639\u062f\u0020\u0627\u0644\u0638\u0647\u0631" // PM
    )

    internal val shortMonthNames = arrayOf(
            "\u0645\u062D", // Muharram
            "\u0635\u0641", // Safar
            "\u0631\u0020\u0623\u0648", // Rabiʿ al-Awwal
            "\u0631\u0020\u062B\u0627", // Rabiʿ ath-Thani
            "\u062C\u0020\u0623\u0648", // Jumada al-Ula
            "\u062C\u0020\u0622\u062E", // Jumada al-Akhirah
            "\u0631\u062C", // Rajab
            "\u0634\u0639", // Sha'ban
            "\u0631\u0645", // Ramadan
            "\u0634\u0648", // Shawwal
            "\u0630\u0642\u0639", // Dhu al-Qa'dah
            "\u0630\u062D\u062C" // Dhu al-Hijjah
    )

    internal val shortWeekDays = arrayOf(
            "\u0633\u0628", // as-Sabt
            "\u0623\u062d", // al-Ahad
            "\u0625\u062b", // al-Ithnayn
            "\u062b\u0644", // ath-Thulatha'
            "\u0623\u0631", // al-Arba'a'
            "\u062e\u0645", // al-Khamis
            "\u062c\u0645" // al-Jumu'ah
    )

    internal val monthNamesEn = arrayOf(
            "Muharram", // Muharram
            "Safar", // Safar
            "Rabiʿ al-Awwal", // Rabiʿ al-Awwal
            "Rabiʿ ath-Thani", // Rabiʿ ath-Thani
            "Jumada al-Ula", // Jumada al-Ula
            "Jumada al-Akhirah", // Jumada al-Akhirah
            "Rajab", // Rajab
            "Sha'ban", // Sha'ban
            "Ramadan", // Ramadan
            "Shawwal", // Shawwal
            "Dhu al-Qa'dah", // Dhu al-Qa'dah
            "Dhu al-Hijjah" // Dhu al-Hijjah
    )

    internal val weekDaysEn = arrayOf(
            "Saturday", // as-Sabt
            "Sunday", // al-Ahad
            "Monday", // al-Ithnayn
            "Tuesday", // ath-Thulatha'
            "Wednesday", // al-Arba'a'
            "Thursday", // al-Khamis
            "Friday" // al-Jumu'ah
    )

    internal val erasEn = arrayOf(
            "\u0628\u0639\u062f\u0020\u0627\u0644\u0645\u064a\u0644\u0627\u062f", // AD
            "\u0642\u0628\u0644\u0020\u0627\u0644\u0645\u064a\u0644\u0627\u062f" // BC
    )

    internal val amPmEn = arrayOf(
            "AM", // AM
            "PM" // PM
    )

    internal val shortMonthNamesEn = arrayOf(
            "Muh", // Muharram
            "Saf", // Safar
            "R Aw", // Rabiʿ al-Awwal
            "R Th", // Rabiʿ ath-Thani
            "J Ul", // Jumada al-Ula
            "J Ak", // Jumada al-Akhirah
            "Raj", // Rajab
            "Shb", // Sha'ban
            "Ram", // Ramadan
            "Shw", // Shawwal
            "D Qa", // Dhu al-Qa'dah
            "D Hj" // Dhu al-Hijjah
    )

    internal val shortWeekDaysEn = arrayOf(
            "Sa", // as-Sabt
            "Su", // al-Ahad
            "Mo", // al-Ithnayn
            "Tu", // ath-Thulatha'
            "We", // al-Arba'a'
            "Th", // al-Khamis
            "Fr" // al-Jumu'ah
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
            if (isHijriLeapYear(year))
                leapYearMonthLength[month]
            else normalMonthLength[month]

    fun yearLength(year: Int): Int =
            if (isHijriLeapYear(year))
                leapYearMonthLengthAggregated[12]
            else normalMonthLengthAggregated[12]

    internal fun dayOfYear(year: Int, month: Int, dayOfMonth: Int): Int =
            if (isHijriLeapYear(year))
                leapYearMonthLengthAggregated[month] + dayOfMonth
            else normalMonthLengthAggregated[month] + dayOfMonth

    internal fun dayOfYear(year: Int, dayOfYear: Int): DateHolder {
        val monthLengthAggregated = if (isHijriLeapYear(year))
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

    internal fun isHijriLeapYear(year: Int): Boolean =
            (14 + 11 * if (year > 0) year else -year) % 30 < 11

    // Conversion Methods --------------------------------------------------------------------------

    internal fun gregorianToHijri(gregorian: DateHolder): DateHolder {
        val gregorianDate = LocalDate.of(gregorian.year, gregorian.month + 1, gregorian.dayOfMonth)
        val hijriDate = HijrahDate.from(gregorianDate)
        return DateHolder(hijriDate.get(ChronoField.YEAR), hijriDate.get(ChronoField.MONTH_OF_YEAR) - 1, hijriDate.get(ChronoField.DAY_OF_MONTH))
    }

    internal fun hijriToGregorian(hijri: DateHolder): DateHolder {
        val hijriDate = HijrahDate.of(hijri.year, hijri.month + 1, hijri.dayOfMonth)
        val gregorianDate = LocalDate.from(hijriDate)
        return DateHolder(gregorianDate.get(ChronoField.YEAR), gregorianDate.get(ChronoField.MONTH_OF_YEAR) - 1, gregorianDate.get(ChronoField.DAY_OF_MONTH))
    }

}