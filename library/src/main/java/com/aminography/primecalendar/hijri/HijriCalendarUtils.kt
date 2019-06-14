package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.common.DateHolder
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.HijrahDate
import org.threeten.bp.temporal.ChronoField

/**
 * @author aminography
 */
internal object HijriCalendarUtils {

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
            "\u0627\u0644\u0633\u0628\u062a", // as-Sabt
            "\u0627\u0644\u0623\u062d\u062f", // al-Ahad
            "\u0627\u0644\u0625\u062b\u0646\u064a\u0646", // al-Ithnayn
            "\u0627\u0644\u062b\u0644\u0627\u062b\u0627\u0621", // ath-Thulatha'
            "\u0627\u0644\u0623\u0631\u0628\u0639\u0627\u0621", // al-Arba'a'
            "\u0627\u0644\u062e\u0645\u064a\u0633", // al-Khamis
            "\u0627\u0644\u062c\u0645\u0639\u0629" // al-Jumu'ah
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

    // Internal Calculation Methods ----------------------------------------------------------------

    internal fun monthLength(year: Int, month: Int): Int =
            if (isHijriLeapYear(year))
                leapYearMonthLength[month]
            else normalMonthLength[month]

    internal fun yearLength(year: Int): Int =
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