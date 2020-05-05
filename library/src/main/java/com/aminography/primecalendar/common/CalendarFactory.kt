package com.aminography.primecalendar.common

import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.japanese.JapaneseCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.*

/**
 * A factory class which creates calendar objects based on desired type.
 *
 * @author aminography
 */
@Suppress("unused")
object CalendarFactory {

    @JvmStatic
    fun <T : PrimeCalendar> newInstance(clazz: Class<T>): T = clazz.getDeclaredConstructor().newInstance()

    @JvmStatic
    fun newInstance(type: CalendarType): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar()
            CalendarType.PERSIAN -> PersianCalendar()
            CalendarType.HIJRI -> HijriCalendar()
            CalendarType.JAPANESE -> JapaneseCalendar()
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, locale: Locale): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(locale = locale)
            CalendarType.PERSIAN -> PersianCalendar(locale = locale)
            CalendarType.HIJRI -> HijriCalendar(locale = locale)
            CalendarType.JAPANESE -> JapaneseCalendar(locale = locale)
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, timeZone: TimeZone): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(timeZone)
            CalendarType.PERSIAN -> PersianCalendar(timeZone)
            CalendarType.HIJRI -> HijriCalendar(timeZone)
            CalendarType.JAPANESE -> JapaneseCalendar(timeZone)
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, timeZone: TimeZone, locale: Locale): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(timeZone, locale)
            CalendarType.PERSIAN -> PersianCalendar(timeZone, locale)
            CalendarType.HIJRI -> HijriCalendar(timeZone, locale)
            CalendarType.JAPANESE -> JapaneseCalendar(timeZone, locale)
        }
    }

}
