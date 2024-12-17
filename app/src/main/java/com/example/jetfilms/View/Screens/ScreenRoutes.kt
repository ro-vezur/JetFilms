package com.example.jetfilms.View.Screens

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
object StartScreen

@Serializable
object HomeRoute

@Serializable
object ExploreNavigationHost {
    @Serializable
    object SearchRoute
    @Serializable
    object FilteredResultsRoute
    @Serializable
    object FilterConfigurationNavigationHost {

        @Serializable
        object AcceptFiltersRoute

        @Serializable
        object FilterGenresRoute

        @Serializable
        object FilterCategoriesRoute

        @Serializable
        object FilterCountriesRoute

        @Serializable
        object FilterYearsRoute

    }
}

@Serializable
object FavoriteRoute

@Serializable
object AccountScreenNavHost{
    @Serializable
    object AccountInfoRoute {}

    @Serializable
    object EditAccountRoute

    @Serializable
    object ReChooseInterestNavHost {
        @Serializable
        object ChooseInterestsToChangeRoute

        @Serializable
        object MediaGenresRoute

        @Serializable
        object MediaTypesRoute
    }

    @Serializable
    object ContactFormRoute {}

    @Serializable
    object AboutAppRoute {}
}

@Parcelize
@Serializable
data class MoreMoviesScreenRoute(
    val category: String
): Parcelable

@Parcelize
@Serializable
data class MoreSerialsScreenRoute(
    val category: String
): Parcelable

