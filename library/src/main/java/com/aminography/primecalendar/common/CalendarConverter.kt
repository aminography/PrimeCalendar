package com.aminography.primecalendar.common

import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar

/**
 * Defines methods providing conversion between calendar types.
 *
 * @author aminography
 */

/**
 * Returns an instance of [CivilCalendar] which is equivalent to the time of the current calendar.
 */
fun PrimeCalendar.toCivil(): CivilCalendar =
        CivilCalendar().also { it.timeInMillis = timeInMillis }

/**
 * Returns an instance of [PersianCalendar] which is equivalent to the time of the current calendar.
 */
fun PrimeCalendar.toPersian(): PersianCalendar =
        PersianCalendar().also { it.timeInMillis = timeInMillis }

/**
 * Returns an instance of [HijriCalendar] which is equivalent to the time of the current calendar.
 */
fun PrimeCalendar.toHijri(): HijriCalendar =
        HijriCalendar().also { it.timeInMillis = timeInMillis }
