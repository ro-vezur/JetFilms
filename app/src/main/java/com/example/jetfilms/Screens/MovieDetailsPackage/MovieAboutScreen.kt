package com.example.jetfilms.Screens.MovieDetailsPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.jetfilms.CustomComposables.Cards.MovieParticipantCard
import com.example.jetfilms.Data_Classes.MoviePackage.MovieDisplay
import com.example.jetfilms.Data_Classes.ParticipantPackage.SimplifiedMovieParticipant
import com.example.jetfilms.Date_formats.DateFormats
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.extensions.sdp
import java.util.Locale

@Composable
fun MovieAboutScreen(
    movieDisplay: MovieDisplay,
    navigateToSelectedParticipant: (participant: SimplifiedMovieParticipant) -> Unit
) {
    val typography = MaterialTheme.typography

    val movieResponse = movieDisplay.response

    Column(
        verticalArrangement = Arrangement.spacedBy(20.sdp),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(5.sdp),
            modifier = Modifier
                .padding(horizontal = 9.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Audio Track" + if(movieResponse.languages.size > 1) "'s" else "",
                style = typography.bodyMedium
            )

            Text(
                text = movieResponse.languages.mapIndexed { index, language ->
                    if(movieResponse.languages.lastIndexOf(language) == index){
                        language.english_name
                    }  else{
                        language.english_name +","
                    }
                }.toString().removePrefix("[").removeSuffix("]"),
                fontSize = typography.bodyMedium.fontSize / 1.1f,
                color = Color.LightGray.copy(0.84f),
                modifier = Modifier
                    .padding(start = 2.sdp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 9.sdp)
                .fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(5.sdp),
                modifier = Modifier
                    .padding(start = 1.sdp)
            ) {
                Text(
                    text = "Country",
                    style = typography.bodyMedium
                )

                Text(
                    text = movieResponse.originCountries.mapIndexed { index, country ->
                        if (movieResponse.originCountries.lastIndexOf(country) == index) {
                            Locale("", country).displayCountry
                        } else {
                            Locale("", country).displayCountry + ","
                        }
                    }.toString().removePrefix("[").removeSuffix("]"),
                    fontSize = typography.bodyMedium.fontSize / 1.1f,
                    color = Color.LightGray.copy(0.84f),
                    modifier = Modifier
                        .padding(start = 2.sdp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.sdp),
                modifier = Modifier
                    .padding(end = 65.sdp)

            ) {
                Text(
                    text = "Year",
                    style = typography.bodyMedium
                )

                Text(
                    text = DateFormats().year(movieResponse.releaseDate).toString(),
                    fontSize = typography.bodyMedium.fontSize / 1.1f,
                    color = Color.LightGray.copy(0.84f),
                    modifier = Modifier
                        .padding(start = 2.sdp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(9.sdp),
            modifier = Modifier
                .padding(start = 1.sdp)
        ) {
            Text(
                text = "Actors",
                style = typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 12.sdp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(9.sdp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {}

                items(movieDisplay.movieCast.cast.take(12)) { participant ->
                    MovieParticipantCard(
                        movieParticipant = participant,
                        modifier = Modifier
                            .height(205.sdp)
                            .width(112.sdp)
                            .clip(RoundedCornerShape(8.sdp))
                            .clickable {
                                navigateToSelectedParticipant(participant)
                            }
                    )
                }

                item {}
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(9.sdp),
            modifier = Modifier
                .padding(start = 1.sdp)
        ) {
            Text(
                text = "Photos",
                style = typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 12.sdp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {}

                items(movieDisplay.movieImages.backdrops.filter { it.iso_639_1 == null }
                    .take(3)) { image ->
                    AsyncImage(
                        model = baseImageUrl + image.file_path,
                        contentDescription = "movie image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(206.sdp)
                            .height(122.sdp)
                            .clip(RoundedCornerShape(8.sdp))
                    )
                }

                item {}
            }
        }
        
        Spacer(modifier = Modifier)
    }
}