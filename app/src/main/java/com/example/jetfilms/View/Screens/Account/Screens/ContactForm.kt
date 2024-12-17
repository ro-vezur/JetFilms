package com.example.jetfilms.View.Screens.Account.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.TEXT_FIELD_MAX_LENGTH
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.InputFields.TextInPutField.TextInputField
import com.example.jetfilms.View.Components.InputFields.TextInPutField.TextInputFieldStatusPosition
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.ViewModels.ValidationViewModels.SubmitContactFormValidationViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.primaryColor

@Composable
fun ContactFormScreen(
    turnBack: () -> Unit,
    user: User,
    submitContactFormValidationViewModel: SubmitContactFormValidationViewModel = hiltViewModel(),
) {
    val usernameValidationResult by submitContactFormValidationViewModel.usernameValidation.collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("${user.firstName} ${user.lastName}") }
    val userError = usernameValidationResult == ValidationResult.ERROR

    var email by remember { mutableStateOf(user.email) }
    val emailError by remember{ mutableStateOf(false) }

    val feedbackValidationResult by submitContactFormValidationViewModel.feedBackValidation.collectAsStateWithLifecycle()
    var feedback by remember { mutableStateOf("") }
    val feedbackError = feedbackValidationResult == ValidationResult.ERROR

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
            modifier = Modifier,
            headerText = "Contact Form",
            turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.sdp),
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding() + 34.sdp)
                    .fillMaxSize()
            ) {
                TextInputField(
                    text = username,
                    isError = userError,
                    height = (BASE_BUTTON_HEIGHT + 4).sdp,
                    onTextChange = { value ->
                        username = value
                        submitContactFormValidationViewModel.setUsernameValidationResult(ValidationResult.NONE)
                    },
                    placeHolder = "User name",
                    leadingIcon = Icons.Filled.Person,
                    trailingIcon = {
                        if(usernameValidationResult != ValidationResult.NONE){
                            Icon(
                                imageVector = usernameValidationResult.icon,
                                contentDescription = "check icon",
                                tint = usernameValidationResult.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
                    modifier = Modifier
                )

                TextInputField(
                    text = email,
                    isError = emailError,
                    height = (BASE_BUTTON_HEIGHT + 4).sdp,
                    onTextChange = { value ->
                        email = value
                    },
                    readOnly = true,
                    placeHolder = "Email",
                    leadingIcon = Icons.Filled.Email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                )

                TextInputField(
                    text = feedback,
                    onTextChange = { value ->
                        feedback = value
                        submitContactFormValidationViewModel.setFeedbackValidationResult(ValidationResult.NONE)
                        if(value.length > TEXT_FIELD_MAX_LENGTH) {
                            submitContactFormValidationViewModel.setFeedbackValidationResult(ValidationResult.ERROR)
                        }
                    },
                    height = (BASE_BUTTON_HEIGHT + 4).sdp,
                    maxHeight = 200.sdp,
                    statusPosition = TextInputFieldStatusPosition.OUT,
                    showTextLength = true,
                    isError = feedbackError,
                    textAlign = Alignment.TopStart,
                    textPadding = PaddingValues(vertical = 10.sdp, horizontal = 2.sdp),
                    maxLines = 8,
                    singleLine = false,
                    placeHolder = "Provide Feedback",
                    trailingIcon = {
                        if(feedbackValidationResult != ValidationResult.NONE){
                            Icon(
                                imageVector = feedbackValidationResult.icon,
                                contentDescription = "check icon",
                                tint = feedbackValidationResult.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
                    modifier = Modifier
                )
            }

            TextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = BOTTOM_NAVIGATION_BAR_HEIGHT.sdp + 20.sdp),
                onClick = {
                    val valid = submitContactFormValidationViewModel.validation(
                        name = username,
                        feedback = feedback,
                        maxFeedbackLength = TEXT_FIELD_MAX_LENGTH
                    )

                    if(valid){

                    }
                },
                text = "Submit",
                corners = RoundedCornerShape(12.sdp),
                gradient = blueHorizontalGradient,
            )
        }
    }
}