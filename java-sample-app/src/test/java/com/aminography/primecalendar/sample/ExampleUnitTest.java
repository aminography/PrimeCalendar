package com.aminography.primecalendar.sample;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

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
    public void rollingWeekOfYear() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        System.out.println(longDateString(calendar));

        calendar.roll(Calendar.WEEK_OF_YEAR, +1);

        System.out.println(longDateString(calendar));
    }

    private String longDateString(Calendar calendar) {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) +
                ",  " +
                calendar.get(Calendar.DAY_OF_MONTH) +
                "  " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) +
                "  " + calendar.get(Calendar.YEAR);
    }

}