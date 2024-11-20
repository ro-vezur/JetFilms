package com.example.jetfilms.Helpers

import java.math.RoundingMode

fun removeNumbersAfterDecimal(number: Float,numbersAfterDecimal: Int): Double{
    return number.toBigDecimal().setScale(numbersAfterDecimal, RoundingMode.UP).toDouble()
}