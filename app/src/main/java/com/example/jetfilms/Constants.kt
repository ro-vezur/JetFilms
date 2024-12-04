package com.example.jetfilms

import androidx.compose.ui.graphics.Brush
import com.example.jetfilms.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.whiteColor

const val BASE_BUTTON_WIDTH = 250
const val BASE_BUTTON_HEIGHT = 40

const val BOTTOM_NAVIGATION_BAR_HEIGHT = 50
const val BOTTOM_NAVIGATION_ITEM_SIZE = 28

const val FILTER_TOP_BAR_HEIGHT = 45

val blueGradientColors = listOf(buttonsColor1, buttonsColor2)
val whiteGradientColors = listOf(whiteColor, whiteColor)

val blueHorizontalGradient = Brush.horizontalGradient(blueGradientColors)
val blueVerticalGradient = Brush.horizontalGradient(blueGradientColors)
val whiteGradient = Brush.horizontalGradient(whiteGradientColors)

const val PAGE_SIZE = 20

const val HAZE_STATE_BLUR = 28

val BASE_MEDIA_GENRES = MediaGenres.entries.drop(1).sorted()

val infoTabs = listOf(
    "Trailers",
    "More Like This",
    "About"
)

const val BASE_API_URL = "https://api.themoviedb.org/3/"
const val BASE_IMAGE_API_URL = "https://image.tmdb.org/t/p/w500"
const val API_KEY = ""

const val BASE_YOUTUBE_IMAGES_URL = "https://img.youtube.com/vi/"
const val YOUTUBE_IMAGE_THUMBNAIL_TYPE = "/mqdefault.jpg"