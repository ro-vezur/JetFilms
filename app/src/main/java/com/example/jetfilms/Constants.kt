package com.example.jetfilms

import androidx.compose.ui.graphics.Brush
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.whiteColor

const val BASE_BUTTON_WIDTH = 250
const val BASE_BUTTON_HEIGHT = 40

const val BOTTOM_NAVIGATION_BAR_HEIGHT = 50
const val BOTTOM_NAVIGATION_ITEM_SIZE = 26

const val FILTER_TOP_BAR_HEIGHT = 45

const val TEXT_FIELD_MAX_LENGTH = 300

val blueGradientColors = listOf(buttonsColor1, buttonsColor2)
val whiteGradientColors = listOf(whiteColor, whiteColor)

val blueHorizontalGradient = Brush.horizontalGradient(blueGradientColors)
val blueVerticalGradient = Brush.horizontalGradient(blueGradientColors)

const val PAGE_SIZE = 20

const val HAZE_STATE_BLUR = 28

val BASE_MEDIA_GENRES = MediaGenres.entries.drop(1).sorted()
val BASE_MEDIA_CATEGORIES = MediaCategories.entries.toList()

val mediaAboutTabs = listOf(
    "Trailers",
    "More Like This",
    "About"
)

const val BASE_API_URL = "https://api.themoviedb.org/3/"
const val BASE_IMAGE_API_URL = "https://image.tmdb.org/t/p/w500"
const val API_KEY = BuildConfig.apiKey

const val BASE_YOUTUBE_IMAGES_URL = "https://img.youtube.com/vi/"
const val YOUTUBE_IMAGE_THUMBNAIL_TYPE = "/mqdefault.jpg"

const val USERS_COLLECTION = "users"

const val PREFERENCES_NAME = "preferences"

const val smtpPassword = BuildConfig.smtpPassword
const val supportEmail = BuildConfig.supportEmail
const val SEND_EMAIL_RESET_TIME_MINUTES = 20

const val APP_DESCRIPTION = "Welcome to JetFilms, your ultimate app for discovering movies and TV shows!\n" +
        "\n" +
        "\uD83C\uDF1F Key Features:\n" +
        "\n" +
        "Explore a vast library of movies and TV series.\n" +
        "Save your favorites for later.\n" +
        "Watch trailers and learn about cast members.\n" +
        "Filter by genres, ratings, or release years.\n" +
        "Discover similar recommendations effortlessly.\n" +
        "\n" +
        "\uD83D\uDEE0 Version 1.0.0\n" +
        "\n" +
        "Whether you're into blockbusters or classics, JetFilms makes it easy to enjoy the world of cinema. Start your journey today! \uD83C\uDFA5"