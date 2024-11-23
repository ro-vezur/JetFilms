package com.example.jetfilms.Components.Lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.jetfilms.Components.Cards.SerialCard
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Screens.MoreSerialsScreenRoute
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun SerialsCategoryList(
    category: String,
    selectSerial: (movie: SimplifiedSerialObject) -> Unit,
    serialsList: List<SimplifiedSerialObject>,
    navController: NavController,
    onSeeAllClick: () -> Unit,
    topPadding: Dp = 0.sdp,
    bottomPadding: Dp = 0.sdp,
    showSeeAllButton: Boolean = true,
    imageModifier: Modifier = Modifier,
) {
    val typography = MaterialTheme.typography
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = topPadding)
            .fillMaxWidth()
    ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(start = 10.sdp, end = 12.sdp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = category,
                    style = typography.titleLarge,
                    color = Color.White,
                    fontSize = 25f.ssp,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.weight(1f))

                if(showSeeAllButton){
                    Box(
                        modifier = Modifier
                            .width(58.sdp)
                            .height(22.sdp)
                            .clip(RoundedCornerShape(8.sdp))
                            .clickable {
                                onSeeAllClick()
                                navController.navigate(MoreSerialsScreenRoute(category))
                            }
                    ) {
                        Text(
                            text = "See All",
                            style = typography.bodyMedium.copy(
                                brush = blueHorizontalGradient,
                                fontWeight = FontWeight.Normal
                            ),
                            fontSize = 20.5f.ssp,
                            modifier = Modifier
                                .align(Alignment.Center)

                        )
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(9.sdp),
                modifier = Modifier
                    .padding(start = 15.sdp, top = 14.sdp, bottom = bottomPadding)
            ) {
                items(items = serialsList) { serial ->

                    SerialCard(
                        modifier = imageModifier
                            .clickable { selectSerial(serial) },
                        serial = serial
                    )

                }
            }
        }

}