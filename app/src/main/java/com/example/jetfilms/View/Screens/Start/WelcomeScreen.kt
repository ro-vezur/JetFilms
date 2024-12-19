package com.example.jetfilms.View.Screens.Start


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.R
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun WelcomeScreen(
    navController: NavController,
    logInWithGoogle: () -> Unit,
) {

    val colors = MaterialTheme.colorScheme

    val title = buildAnnotatedString {
        withStyle(style = typography().titleLarge.copy(letterSpacing = 1.ssp).toSpanStyle()) {
            append("Start Streaming Now with ")
        }

        withStyle(
            style = typography().titleLarge.copy(
                fontSize = 26.ssp,
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
                style = typography().titleLarge,
                color = whiteColor,
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
                        navController.navigate(LogInScreenRoute)
                    },
                    text = "Log in",
                    corners = RoundedCornerShape(12.sdp),
                    background = colors.secondary,
                    border = BorderStroke(1.sdp, blueHorizontalGradient),
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
                                    .background(whiteColor)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.facebook_logo_icon_facebook_icon_png_images_icons_and_png_backgrounds_1),
                                contentDescription = "facebook logo",
                                modifier = Modifier
                                    .size(30.sdp)
                            )
                        }
                    },
                    border = BorderStroke(1.sdp, blueHorizontalGradient)
                )

                TextButton(
                    onClick = { logInWithGoogle() },
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
                    border = BorderStroke(1.sdp, blueHorizontalGradient),
                )

                TextButton(
                    onClick = {
                        navController.navigate(SignUpScreenRoute)
                    },
                    text = "Sign up",
                    corners = RoundedCornerShape(12.sdp),
                    gradient = blueHorizontalGradient,
                )
            }
        }

}

@Preview
@Composable
private fun previ() {
   // WelcomeScreen()
}