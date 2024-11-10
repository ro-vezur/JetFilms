package com.example.jetfilms.Bottom_Navigation_Bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.Screens.AccountScreen
import com.example.jetfilms.Screens.ExploreScreen
import com.example.jetfilms.Screens.FavoriteScreen
import com.example.jetfilms.Screens.HomeScreen
import com.example.jetfilms.Screens.TVScreen
import kotlinx.serialization.descriptors.StructureKind

enum class BottomNavItems(val title: String,val icon:ImageVector,val route:Any){
    HOME("Home",Icons.Filled.Home,"HomeScreen"),
    EXPLORE("Explore",Icons.Filled.Search,"ExploreScreen"),
    TV("TV",Icons.Filled.LiveTv,"TVScreen"),
    FAVORITE("Favorite",Icons.Outlined.BookmarkBorder,"FavoriteScreen"),
    ACCOUNT("Account",Icons.Filled.Person,"AccountScreen")
}