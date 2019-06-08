package com.aminography.primecalendar.common

import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.hijri.HijriCalendar
import com.aminography.primecalendar.persian.PersianCalendar

/**
 * @author aminography
 */

internal fun convertCivilToPersian(civilCalendar: CivilCalendar): PersianCalendar {
    val persianCalendar = PersianCalendar()
    persianCalendar.timeInMillis = civilCalendar.timeInMillis
    return persianCalendar
}

internal fun convertCivilToHijri(civilCalendar: CivilCalendar): HijriCalendar {
    val hijriCalendar = HijriCalendar()
    hijriCalendar.timeInMillis = civilCalendar.timeInMillis
    return hijriCalendar
}

internal fun convertPersianToCivil(persianCalendar: PersianCalendar): CivilCalendar {
    val civilCalendar = CivilCalendar()
    civilCalendar.timeInMillis = persianCalendar.timeInMillis
    return civilCalendar
}

internal fun convertPersianToHijri(persianCalendar: PersianCalendar): HijriCalendar {
    val hijriCalendar = HijriCalendar()
    hijriCalendar.timeInMillis = persianCalendar.timeInMillis
    return hijriCalendar
}

internal fun convertHijriToCivil(hijriCalendar: HijriCalendar): CivilCalendar {
    val civilCalendar = CivilCalendar()
    civilCalendar.timeInMillis = hijriCalendar.timeInMillis
    return civilCalendar
}

internal fun convertHijriToPersian(hijriCalendar: HijriCalendar): PersianCalendar {
    val persianCalendar = PersianCalendar()
    persianCalendar.timeInMillis = hijriCalendar.timeInMillis
    return persianCalendar
}
