package com.example.jetfilms

import com.example.jetfilms.Helpers.DateFormats.DateFormats
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

    @Mock
    private lateinit var mockedDateFormats: DateFormats

    @Test
    fun testGetCurrentYear() {
        val mockedDateFormats = mock(DateFormats::class.java)

        val result = mockedDateFormats.getCurrentYear()

        assertEquals(2025, result)

    }

}