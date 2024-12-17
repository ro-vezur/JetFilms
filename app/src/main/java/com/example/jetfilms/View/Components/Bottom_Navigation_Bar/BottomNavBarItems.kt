package com.example.jetfilms.View.Components.Bottom_Navigation_Bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.View.Screens.AccountScreenNavHost
import com.example.jetfilms.View.Screens.ExploreNavigationHost
import com.example.jetfilms.View.Screens.FavoriteRoute
import com.example.jetfilms.View.Screens.HomeRoute

enum class BottomNavItems(val title: String,val icon:ImageVector,val route:Any){
    HOME("Home",Icons.Filled.Home, HomeRoute),
    EXPLORE("Explore",Icons.Filled.Search, ExploreNavigationHost.SearchRoute),
    FAVORITE("Favorite",Icons.Outlined.BookmarkBorder, FavoriteRoute),
    ACCOUNT("Account",Icons.Filled.Person, AccountScreenNavHost)
}