package com.example.jetfilms

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Helpers.Validators.Results.EmailValidationResult
import com.example.jetfilms.Helpers.Validators.Results.PasswordValidationResult
import com.example.jetfilms.Helpers.Validators.Results.UsernameValidationResult
import com.example.jetfilms.Helpers.Validators.Validators.Email.EmailValidator
import com.example.jetfilms.Helpers.Validators.Validators.Password.PasswordValidator
import com.example.jetfilms.Helpers.Validators.Validators.Username.UsernameValidator
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun getCurrentYear() {
        val result = DateFormats.getCurrentYear()
        assertEquals(2024, result)
    }

    @Test
    fun getYearFromDate() {
        val result = DateFormats.getYear("2012-01-02")
        assertEquals(2012,result)
    }

    @Test
    fun isEmailValid() = runTest {
        assertTrue("is Email valid",
            EmailValidator().invoke("romavzr3011@gmail.com") == EmailValidationResult.CORRECT)
    }

    @Test
    fun isUsernameValid() = runTest {
        assertTrue(UsernameValidator().invoke("roma") == UsernameValidationResult.CORRECT)
    }

    @Test
    fun isPasswordNotValid() = runTest {
        assertFalse("is Password not valid",
            PasswordValidator().invoke("aa2") == PasswordValidationResult.CORRECT)
    }

    @Test
    fun isPasswordValid() = runTest {
        assertTrue("is Password valid",
            PasswordValidator().invoke("roMapoma2") == PasswordValidationResult.CORRECT)
    }
}