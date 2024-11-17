package com.example.jetfilms.Screens.Start

import com.example.jetfilms.CustomComposables.Text.Highlight
import com.example.jetfilms.CustomComposables.Text.HighlightedText
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.jetfilms.CustomComposables.Text.BaseTextFieldColors
import com.example.jetfilms.CustomComposables.Text.CustomTextField
import com.example.jetfilms.CustomComposables.Buttons.TextButton
import com.example.jetfilms.CustomComposables.Buttons.TurnBackButton
import com.example.jetfilms.baseButtonHeight
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SignUpScreen(
    stepsNavController:NavController,
) {
    val context = LocalContext.current
    val typography = MaterialTheme.typography

    var firstName by remember{ mutableStateOf("") }
    var lastName by remember{ mutableStateOf("") }
    var emailText by remember{ mutableStateOf("") }
    var passwordText by remember{ mutableStateOf("") }
    var passwordConfirmText by remember{ mutableStateOf("") }

    var showPassword by remember{ mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TurnBackButton(
            onClick = { stepsNavController.navigate(WelcomeScreenRoute) },
            background = Color.LightGray.copy(0.6f),
            iconColor = Color.White,
            modifier = Modifier
                .padding(start = 16.sdp, top = 34.sdp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
          //  verticalArrangement = Arrangement.spacedBy(13.sdp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.sdp)
        ) {
            Text(
                text = "Sign up",
                style = typography.titleLarge.copy(
                    fontSize = typography.titleLarge.fontSize * 1.15,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 30.sdp)
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
                    text = firstName,
                    height = (baseButtonHeight + 1).sdp,
                    onTextChange = { value ->
                        firstName = value
                    },
                    placeHolder = "First Name",
                    leadingIcon = Icons.Filled.Person,
                    modifier = Modifier
                )

                CustomTextField(
                    colors = BaseTextFieldColors(),
                    text = lastName,
                    height = (baseButtonHeight + 1).sdp,
                    onTextChange = { value ->
                        lastName = value
                    },
                    placeHolder = "Last Name",
                    leadingIcon = Icons.Filled.Person,
                    modifier = Modifier
                )

                CustomTextField(
                    colors = BaseTextFieldColors(),
                    text = emailText,
                    height = (baseButtonHeight + 1).sdp,
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
                    height = (baseButtonHeight + 1).sdp,
                    onTextChange = { value ->
                        passwordText = value
                    },
                    placeHolder = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "password",
                            tint = Color.LightGray.copy(0.9f),
                            modifier = Modifier
                                .padding(end = 11.sdp)
                                .clip(CircleShape)
                                .size(20.sdp)
                                .clickable {
                                    showPassword = !showPassword
                                }
                        )
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                )

                CustomTextField(
                    colors = BaseTextFieldColors(),
                    text = passwordConfirmText,
                    height = (baseButtonHeight + 1).sdp,
                    onTextChange = { value ->
                        passwordConfirmText = value
                    },
                    placeHolder = "Password Confirm",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "password",
                            tint = Color.LightGray.copy(0.9f),
                            modifier = Modifier
                                .padding(end = 11.sdp)
                                .clip(CircleShape)
                                .size(20.sdp)
                                .clickable {
                                    showPassword = !showPassword
                                }
                        )
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                )
            }

            TextButton(
                onClick = {
                    stepsNavController.navigate(SelectMediaFormatScreenRoute)
                },
                text = "Sign up",
                corners = RoundedCornerShape(12.sdp),
                gradient = Brush.horizontalGradient(
                    listOf(buttonsColor1, buttonsColor2)
                ),
                modifier = Modifier
                    .padding(bottom = 87.sdp)
            )

            HighlightedText(
                text = "Already have an account? Log in",
                highlights = listOf(
                    Highlight(
                        text = "Log in",
                        data = "",
                        onClick = { link ->
                            stepsNavController.navigate(LogInScreenRoute)
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