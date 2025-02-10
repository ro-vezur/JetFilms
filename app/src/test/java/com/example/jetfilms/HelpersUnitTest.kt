package com.example.jetfilms

import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.Helpers.fromMinutesToHours
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import org.junit.Assert.assertEquals
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class HelpersUnitTest {

    @Test
    fun testGetCurrentYear() {
        val result = DateFormats.getCurrentYear()
        assertEquals(2025, result)
    }

    @Test
    fun testGetYearFromDate() {
        val date = "2012-01-10"
        val result = DateFormats.getYear(date)
        assertEquals(2012,result)
    }

    @Test
    fun testMinutesToHours() {
        val minutes = 65
        val result = fromMinutesToHours(minutes)
        assertEquals(" 1h 05m",result)
    }

    @Test
    fun numbersAfterDecimal() {
        val number = 1.1284f
        val result = removeNumbersAfterDecimal(number,2)
        assertEquals(1.13,result,0.0)
    }

}