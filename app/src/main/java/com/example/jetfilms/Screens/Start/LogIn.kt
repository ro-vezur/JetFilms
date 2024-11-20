package com.example.jetfilms.Screens.Start

import com.example.jetfilms.Components.Text.Highlight
import com.example.jetfilms.Components.Text.HighlightedText
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.jetfilms.Components.Text.BaseTextFieldColors
import com.example.jetfilms.Components.Text.CustomTextField
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun LogInScreen(
    stepsNavController: NavController
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    var passwordText by remember{ mutableStateOf("") }
    var showPassword by remember{ mutableStateOf(false) }

    var emailText by remember{ mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        TurnBackButton(
            onClick = { stepsNavController.navigate(WelcomeScreenRoute) },
            background = Color.LightGray.copy(0.6f),
            iconColor = Color.White,
            modifier = Modifier
                .padding(start = 16.sdp,top = 34.sdp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.sdp)
        ) {
            Text(
                text = "Log in",
                style = typography.titleLarge.copy(
                    fontSize = typography.titleLarge.fontSize * 1.15,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth(0.8f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(17f.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth()
            ) {
                CustomTextField(
                    colors = BaseTextFieldColors(),
                    text = emailText,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        emailText = value
                    },
                    placeHolder = "Email",
                    leadingIcon = Icons.Filled.Email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                )

                CustomTextField(
                    colors = BaseTextFieldColors(),
                    text = passwordText,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        passwordText = value
                    },
                    placeHolder = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = { modifier ->
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "password",
                            tint = Color.LightGray.copy(0.9f),
                            modifier = modifier
                                .clickable {
                                    showPassword = !showPassword
                                }
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(15.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 58.sdp)
                    .fillMaxWidth()
            ) {

                TextButton(
                    onClick = {

                    },
                    text = "Log in",
                    corners = RoundedCornerShape(12.sdp),
                    gradient = Brush.horizontalGradient(
                        listOf(buttonsColor1, buttonsColor2)
                    ),
                )

                Text(
                    text = "Forgot the password?",
                    style = typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = typography.bodySmall.fontSize / 1.02
                    )
                )
            }

            HighlightedText(
                text = "Don't have an account? Sign up",
                highlights = listOf(
                    Highlight(
                        text = "Sign up",
                        data = "",
                        onClick = { link ->
                            stepsNavController.navigate(SignUpScreenRoute)
                        }
                    ),
                ),
                textStyle = typography.bodySmall.copy(color = Color.LightGray.copy(0.77f)),
                highLightedTextStyle = typography.bodySmall.copy(
                    color = buttonsColor2,
                    fontSize = (typography.bodySmall.fontSize * 1.1)
                ),
            )
        }
    }
}