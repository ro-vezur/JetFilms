package com.example.jetfilms.View.Screens.Favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.Cards.UnifiedCard
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.View.states.rememberForeverScrollState
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun FavoriteMainScreen(
    navController: NavController,
    searchedHistoryFlow: List<UnifiedMedia>,
    searchedHistoryInDb: List<SearchedMedia>,
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit
) {
    val typography = typography()
    val scrollState = rememberForeverScrollState(key = "favorite screen")

    Scaffold(
        containerColor = primaryColor,
        modifier = Modifier
            .fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(16.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 12.sdp)
                    .fillMaxSize()
            ) {
                Divider(
                    color = Color.DarkGray.copy(0.5f),
                    thickness = 2.sdp,
                    modifier = Modifier
                        .padding(top = 32.sdp)
                        .fillMaxWidth()
                        .clip(CircleShape)
                )

                CategoryCard(
                    text = "Favorite Movies and Series",
                    onClick = {navController.navigate("Favorite Movies and Series",)},
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 25.sdp,start = 12.sdp,end = 12.sdp)
            ) {
                Text(
                    text = "History",
                    style = typography.bodyLarge,
                    color = whiteColor
                )

                Text(
                    text = "The last 10 media you viewed will be here",
                    style = typography().bodySmall,
                    fontWeight = FontWeight.W400,
                    color = Color.LightGray.copy(0.9f),
                    modifier = Modifier
                        .padding(top = 6.sdp)
                )
            }

            if(searchedHistoryFlow.isNotEmpty()){
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.sdp),
                    modifier = Modifier
                        .padding(top = 16.sdp)
                        .fillMaxWidth()
                ) {
                    item{ Spacer(modifier = Modifier.width(2.sdp)) }

                    items(searchedHistoryFlow) { unifiedMedia ->

                        val searchedMediaInDb = searchedHistoryInDb.find { it.id == "${unifiedMedia.id}${unifiedMedia.mediaCategory.format}"}

                        searchedMediaInDb?.run {
                            Column(

                            ) {
                                UnifiedCard(
                                    unifiedMedia = unifiedMedia,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.sdp))
                                        .height(155.sdp)
                                        .width(110.sdp)
                                        .clickable { selectMedia(unifiedMedia) }
                                )

                                Text(
                                    text = "View ${
                                        DateFormats.getDateFromMillis(searchedMediaInDb.viewedDateMillis)
                                            .replace("/", ".")
                                    }",
                                    style = typography().bodySmall,
                                    fontWeight = FontWeight.W400,
                                    color = Color.LightGray.copy(0.85f),
                                    modifier = Modifier
                                        .padding(top = 9.sdp)
                                )
                            }
                        }
                    }
                    item{ Spacer(modifier = Modifier.width(2.sdp)) }
                }
            }else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 16.sdp)
                        .fillMaxWidth()
                        .height(115.sdp)
                ){
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "history",
                        tint = whiteColor,
                        modifier = Modifier
                            .size(55.sdp)
                    )

                    Text(
                        text = "Your Search History is Empty",
                        style = typography().bodyMedium,
                        fontWeight = FontWeight.W400,
                        color = whiteColor,
                        modifier = Modifier
                            .padding(top = 8.sdp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(
    text: String,
    onClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.sdp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.sdp))
            .clickable { onClick() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
               // .padding(horizontal = 5.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                style = typography().bodyMedium,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "forward",
                tint = Color.DarkGray.copy(0.5f),
                modifier = Modifier
                    .size(22.sdp)
            )
        }

        Divider(
            color = Color.DarkGray.copy(0.5f),
            thickness = 2.sdp,
            modifier = Modifier
                .padding(bottom = 6.sdp)
                .fillMaxWidth()
                .clip(CircleShape)
        )
    }
}