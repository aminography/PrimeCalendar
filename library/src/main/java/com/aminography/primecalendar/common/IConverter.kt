package com.aminography.primecalendar.common

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar

/**
 * Defines methods to be implemented by calendar concrete classes that provides conversion between them.
 *
 * @author aminography
 */
internal interface IConverter {

    /**
     * Returns an instance of [CivilCalendar] which is equivalent to the time of the current calendar.
     */
    fun toCivil(): CivilCalendar

    /**
     * Returns an instance of [PersianCalendar] which is equivalent to the time of the current calendar.
     */
    fun toPersian(): PersianCalendar

    /**
     * Returns an instance of [HijriCalendar] which is equivalent to the time of the current calendar.
     */
    fun toHijri(): HijriCalendar
}