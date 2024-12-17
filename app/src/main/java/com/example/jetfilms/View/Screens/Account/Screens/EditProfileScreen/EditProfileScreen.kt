package com.example.jetfilms.View.Screens.Account.Screens.EditProfileScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.InputFields.TextInPutField.TextInputField
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.ViewModels.ValidationViewModels.EditProfileValidationViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.errorColor
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.secondaryColor
import com.example.jetfilms.ui.theme.whiteColor
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    user: User,
    turnBack: () -> Unit,
    saveUserChanges: (newUser: User) -> Unit,
    updatePassword: (email: String,oldPassword: String,newPassword: String) -> Unit,
    editProfileValidationViewModel: EditProfileValidationViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val userImageSize = 85

    val usernameValidationResult by editProfileValidationViewModel.usernameValidation.collectAsStateWithLifecycle()
    var firstName by remember{ mutableStateOf(user.firstName) }
    var lastName by remember{ mutableStateOf(user.lastName) }
    val usernameError = usernameValidationResult == ValidationResult.ERROR

    val emailValidationResult by editProfileValidationViewModel.emailValidation.collectAsStateWithLifecycle()
    var emailText by remember{ mutableStateOf(user.email) }
    val emailError = emailValidationResult == ValidationResult.ERROR

    val oldPasswordValidationResult by editProfileValidationViewModel.passwordValidation.collectAsStateWithLifecycle()
    var oldPasswordText by remember{ mutableStateOf("") }
    val oldPasswordError = oldPasswordValidationResult == ValidationResult.ERROR

    val newPasswordValidationResult by editProfileValidationViewModel.passwordConfirmValidation.collectAsStateWithLifecycle()
    var newPasswordText by remember{ mutableStateOf("") }
    val newPasswordError = newPasswordValidationResult == ValidationResult.ERROR

    var showPassword by remember{ mutableStateOf(false)}

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier,
                headerText = "Edit Profile",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(17f.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 26.sdp)
                        .size(userImageSize.sdp)
                        .border(BorderStroke(2f.sdp, whiteColor), CircleShape)
                ){
                    AsyncImage(
                        model = "",
                        contentDescription = "user image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size((userImageSize - 2).sdp)
                            .clip(CircleShape)
                            .background(secondaryColor)
                    )
                }

                TextInputField(
                    text = firstName,
                    isError = usernameError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        firstName = value
                        editProfileValidationViewModel.setUsernameValidationResult(
                            ValidationResult.NONE)
                    },
                    placeHolder = "First Name",
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
                    modifier = Modifier
                        .padding(top = 25.sdp)
                )

                TextInputField(
                    text = lastName,
                    isError = usernameError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        lastName = value
                        editProfileValidationViewModel.setUsernameValidationResult(
                            ValidationResult.NONE)
                    },
                    placeHolder = "Last Name",
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
                    modifier = Modifier
                )

                TextInputField(
                    text = emailText,
                    isError = emailError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        emailText = value
                        editProfileValidationViewModel.setEmailValidationResult(ValidationResult.NONE)
                    },
                    placeHolder = "Email",
                    leadingIcon = Icons.Filled.Email,
                    trailingIcon = {
                        if(emailValidationResult != ValidationResult.NONE){
                            Icon(
                                imageVector = emailValidationResult.icon,
                                contentDescription = "check icon",
                                tint = emailValidationResult.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                )

                TextInputField(
                    text = oldPasswordText,
                    isError = oldPasswordError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        oldPasswordText = value
                        editProfileValidationViewModel.setOldPasswordValidationResult(
                            ValidationResult.NONE)
                    },
                    placeHolder = "Current Password",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.sdp)
                        ){
                            if(oldPasswordValidationResult != ValidationResult.NONE){
                                Icon(
                                    imageVector = oldPasswordValidationResult.icon,
                                    contentDescription = "check icon",
                                    tint = oldPasswordValidationResult.tint,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(20.sdp)
                                )
                            }

                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "password",
                                tint = if(oldPasswordError) errorColor else Color.LightGray.copy(0.9f) ,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                                    .clickable {
                                        showPassword = !showPassword
                                    }
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                )

                TextInputField(
                    text = newPasswordText,
                    isError = newPasswordError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        newPasswordText = value
                        editProfileValidationViewModel.setNewPasswordValidationResult(
                            ValidationResult.NONE)
                    },
                    placeHolder = "New Confirm",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.sdp)
                        ){
                            if(newPasswordValidationResult != ValidationResult.NONE){
                                Icon(
                                    imageVector = newPasswordValidationResult.icon,
                                    contentDescription = "check icon",
                                    tint = newPasswordValidationResult.tint,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(20.sdp)
                                )
                            }

                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "password",
                                tint = if(newPasswordError) errorColor else Color.LightGray.copy(0.9f) ,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                                    .clickable {
                                        showPassword = !showPassword
                                    }
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                )
            }

            TextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 25).sdp),
                text = "Save Changes",
                onClick = {
                    scope.launch {
                        val validateUsername = editProfileValidationViewModel.validateUsername(
                            name = "$firstName $lastName",
                            email = emailText,
                        )

                        if(validateUsername && oldPasswordText.isBlank() && newPasswordText.isBlank()) {
                            val newUser = User(
                                firstName = firstName,
                                lastName = lastName,
                                email = emailText,
                                password = user.password,
                            )

                            saveUserChanges(
                                newUser
                            )
                        }

                        if(oldPasswordText.isNotBlank() || newPasswordText.isNotBlank()){
                            val validatePassword = editProfileValidationViewModel.validatePassword(
                                oldPassword = user.password,
                                enteredPassword = oldPasswordText,
                                newPassword = newPasswordText
                            )

                            if (validatePassword) {
                                val newUser = User(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = emailText,
                                    password = newPasswordText,
                                )
                                saveUserChanges(newUser)
                                updatePassword(emailText,oldPasswordText,newPasswordText)
                            }
                        }
                    }
                },
                gradient = blueHorizontalGradient
            )
        }
    }
}