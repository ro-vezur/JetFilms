package com.example.jetfilms.Additional_functions

import java.math.RoundingMode

fun removeNumbersAfterDecimal(number: Float,numbersAfterDecimal: Int): Double{
    return number.toBigDecimal().setScale(numbersAfterDecimal, RoundingMode.UP).toDouble()
}