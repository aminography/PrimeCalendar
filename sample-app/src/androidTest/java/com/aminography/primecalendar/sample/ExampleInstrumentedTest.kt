package com.aminography.primecalendar.sample

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.aminography.primecalendar.civil.CivilCalendar
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.aminography.primecalendar.sample", appContext.packageName)
    }

    @Test
    fun May31Test() {
        val civil = CivilCalendar()
        print(civil.longDateString)

        assertEquals(civil.year, 2019)
        assertEquals(civil.month, 4)
        assertEquals(civil.dayOfMonth, 31)
    }
}
