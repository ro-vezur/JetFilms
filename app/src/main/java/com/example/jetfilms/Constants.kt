package com.example.jetfilms

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2

const val baseButtonWidth = 250
const val baseButtonHeight = 40

const val bottomNavBarHeight = 54
const val bottomNavItemSize = 28

val blueGradient = Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2))
val whiteGradient = Brush.horizontalGradient(listOf(Color.White.copy(0.82f), Color.White.copy(0.82f)))

const val baseAPIUrl = "https://api.themoviedb.org/3/"
const val baseImageUrl = "https://image.tmdb.org/t/p/w500"
const val APIKEY = ""

val minutesInHour = 60