package com.aminography.primecalendar.common

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar

internal interface IConverter {

    fun toCivil(): CivilCalendar

    fun toPersian(): PersianCalendar

    fun toHijri(): HijriCalendar
}