package com.aminography.primecalendar.hijri

import com.aminography.primecalendar.common.DateHolder
import com.aminography.primecalendar.hijri.HijriCalendar.Companion.DEFAULT_LOCALE
import com.aminography.primecalendar.hijri.HijriCalendar.Companion.shiftDate
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.HijrahDate
import org.threeten.bp.temporal.ChronoField
import java.util.Calendar.FRIDAY
import java.util.Calendar.MONDAY
import java.util.Calendar.SATURDAY
import java.util.Calendar.SUNDAY
import java.util.Calendar.THURSDAY
import java.util.Calendar.TUESDAY
import java.util.Calendar.WEDNESDAY
import java.util.Locale

/**
 * @author aminography
 */
@Suppress("SpellCheckingInspection")
object HijriCalendarUtils {

    // Month Length Arrays -------------------------------------------------------------------------

    private val normalMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29)
    private val leapYearMonthLength = intArrayOf(30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30)

    private val normalMonthLengthAggregated = intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 354)
    private val leapYearMonthLengthAggregated = intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 355)

    // Symbol Name Arrays --------------------------------------------------------------------------

    internal val monthNames = arrayOf(
        "محرم", // Muharram
        "صفر", // Safar
        "ربيع الأول", // Rabiʿ al-Awwal
        "ربيع الثاني", // Rabiʿ ath-Thani
        "جمادى الأولى", // Jumada al-Ula
        "جمادى الآخرة", // Jumada al-Akhirah
        "رجب", // Rajab
        "شعبان", // Sha'ban
        "رمضان", // Ramadan
        "شوال", // Shawwal
        "ذو القعدة", // Dhu al-Qa'dah
        "ذو الحجة" // Dhu al-Hijjah
    )

    internal val weekDays = arrayOf(
        "السبت", // as-Sabt
        "الأحد", // al-Ahad
        "الإثنين", // al-Ithnayn
        "الثلاثاء", // ath-Thulatha'
        "الأربعاء", // al-Arba'a'
        "الخميس", // al-Khamis
        "الجمعة" // al-Jumu'ah
    )

    internal val eras = arrayOf(
        "بعد الميلاد", // AD
        "قبل الميلاد" // BC
    )

    internal val amPm = arrayOf(
        "قبل الظهر", // AM
        "بعد الظهر" // PM
    )

    internal val shortMonthNames = arrayOf(
        "مح", // Muharram
        "صف", // Safar
        "رب١", // Rabiʿ al-Awwal
        "رب٢", // Rabiʿ ath-Thani
        "جم١", // Jumada al-Ula
        "جم٢", // Jumada al-Akhirah
        "رج", // Rajab
        "شع", // Sha'ban
        "رم", // Ramadan
        "شو", // Shawwal
        "ذقع", // Dhu al-Qa'dah
        "ذحج" // Dhu al-Hijjah
    )

    internal val shortWeekDays = arrayOf(
        "سب", // as-Sabt
        "أح", // al-Ahad
        "إث", // al-Ithnayn
        "ثل", // ath-Thulatha'
        "أر", // al-Arba'a'
        "خم", // al-Khamis
        "جم" // al-Jumu'ah
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
        "AD", // AD
        "BC" // BC
    )

    internal val amPmEn = arrayOf(
        "AM", // AM
        "PM" // PM
    )

    internal val shortMonthNamesEn = arrayOf(
        "Muh", // Muharram
        "Saf", // Safar
        "Ra1", // Rabiʿ al-Awwal
        "Ra2", // Rabiʿ ath-Thani
        "Ja1", // Jumada al-Ula
        "Ja2", // Jumada al-Akhirah
        "Raj", // Rajab
        "Shb", // Sha'ban
        "Ram", // Ramadan
        "Shw", // Shawwal
        "DQa", // Dhu al-Qa'dah
        "DHj" // Dhu al-Hijjah
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
        for (i in monthLengthAggregated.indices) {
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
            .run { plusDays(shiftDate.toLong()) }
        val hijriDate = HijrahDate.from(gregorianDate)
        return DateHolder(hijriDate.get(ChronoField.YEAR), hijriDate.get(ChronoField.MONTH_OF_YEAR) - 1, hijriDate.get(ChronoField.DAY_OF_MONTH))
    }

    internal fun hijriToGregorian(hijri: DateHolder): DateHolder {
        val hijriDate = HijrahDate.of(hijri.year, hijri.month + 1, hijri.dayOfMonth)
        val gregorianDate = LocalDate.from(hijriDate)
            .run { minusDays(shiftDate.toLong()) }
        return DateHolder(gregorianDate.get(ChronoField.YEAR), gregorianDate.get(ChronoField.MONTH_OF_YEAR) - 1, gregorianDate.get(ChronoField.DAY_OF_MONTH))
    }
}
