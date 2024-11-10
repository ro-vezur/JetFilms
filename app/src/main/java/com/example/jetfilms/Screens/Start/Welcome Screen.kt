package com.example.jetfilms.Screens.Start


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.CustomComposables.TextButton
import com.example.jetfilms.R
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2

@Composable
fun WelcomeScreen(
    stepsNvController: NavController
) {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val title = buildAnnotatedString {
        withStyle(style = typography.titleLarge.copy(letterSpacing = 1.5f.ssp).toSpanStyle()) {
            append("Start Streaming Now with ")
        }

        withStyle(
            style = typography.titleLarge.copy(
                fontSize = (35f).ssp,
                fontWeight = FontWeight.ExtraBold
            ).toSpanStyle()) {
            append("JetFilms")
        }
    }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.sdp)
        ) {
            
            Text(
                text = title,
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth(0.8f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(13.sdp),
                modifier = Modifier
                    .padding(bottom = 16.sdp)
            ){


                TextButton(
                    onClick = {
                        stepsNvController.navigate(LogInScreenRoute)
                    },
                    text = "Log in",
                    corners = RoundedCornerShape(12.sdp),
                    background = colors.secondary,
                    border = BorderStroke(
                        1.45f.sdp, Brush.horizontalGradient(
                            listOf(buttonsColor1, buttonsColor2)
                        )
                    ),
                )

                TextButton(
                    onClick = {

                    },
                    text = "Log in via Facebook",
                    corners = RoundedCornerShape(12.sdp),
                    background = colors.secondary,
                    image = { modifier ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier

                        ) {
                            Box(
                                modifier = Modifier
                                    .size(25.sdp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.facebook_logo_icon_facebook_icon_png_images_icons_and_png_backgrounds_1),
                                contentDescription = "facebook logo",
                                modifier = Modifier
                                    .size(30.sdp)
                            )
                        }
                    },
                    border = BorderStroke(
                        1.45f.sdp, Brush.horizontalGradient(
                            listOf(buttonsColor1, buttonsColor2)
                        )
                    )
                )

                TextButton(
                    onClick = {

                    },
                    text = "Log in via Google",
                    corners = RoundedCornerShape(12.sdp),
                    background = colors.secondary,
                    image = { modifier ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.size(30.sdp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "google logo",
                                modifier = Modifier
                                    .size(24.sdp)
                            )
                        }
                    },
                    border = BorderStroke(
                        1.45f.sdp, Brush.horizontalGradient(
                            listOf(buttonsColor1, buttonsColor2)
                        )
                    ),
                )

                TextButton(
                    onClick = {
                        stepsNvController.navigate(SignUpScreenRoute)
                    },
                    text = "Sign up",
                    corners = RoundedCornerShape(12.sdp),
                    gradient = Brush.horizontalGradient(
                        listOf(buttonsColor1, buttonsColor2)
                    ),
                )
            }
        }

}

@Preview
@Composable
private fun previ() {
   // WelcomeScreen()
}