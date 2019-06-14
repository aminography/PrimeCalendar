package com.aminography.primecalendar.sample;

import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.aminography.primecalendar.hijri.HijriCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;

import org.junit.Test;

import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void aaaaa() {
//        PersianCalendar calendar = (PersianCalendar) CalendarFactory.newInstance(CalendarType.PERSIAN);
        PersianCalendar calendar = new PersianCalendar(new Locale("fa"));
//        PersianCalendar calendar = new PersianCalendar(TimeZone.getDefault());
        PersianCalendar persian = calendar.toPersian();
        HijriCalendar hijri = calendar.toHijri();
        CivilCalendar civil = calendar.toCivil();
    }


}