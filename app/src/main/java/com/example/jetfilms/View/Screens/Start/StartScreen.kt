package com.example.jetfilms.View.Screens.Start

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.R
import com.example.jetfilms.View.Screens.Start.Select_genres.SelectMediaGenresScreen
import com.example.jetfilms.View.Screens.Start.Select_type.SelectMediaCategoriesScreen
import com.example.jetfilms.ViewModels.UserViewModel.UserViewModel

@Composable
fun StartScreen(
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()

    val colors = MaterialTheme.colorScheme

    val context = LocalContext.current

    var thirdShadowPoint by remember{ mutableStateOf(1f) }
    var fourthShadowPoint by remember{ mutableStateOf(.98f) }
    var fifthShadowPoint by remember{ mutableStateOf(.78f) }
    var sixthShadowPoint by remember{ mutableStateOf(.52f) }
    var seventhShadowPoint by remember{ mutableStateOf(.5f) }

    val backgroundAnimationDelay = 20

    Box(
        modifier = Modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.welcomebackground) ,
            contentDescription = "welcome background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.primary.copy(
                                animateFloatAsState(
                                    targetValue = seventhShadowPoint,
                                    animationSpec = tween(delayMillis = backgroundAnimationDelay * 5)
                                ).value
                            ),
                            colors.primary.copy(
                                animateFloatAsState(
                                    targetValue = sixthShadowPoint,
                                    animationSpec = tween(delayMillis = backgroundAnimationDelay * 4)
                                ).value
                            ),
                            colors.primary.copy(
                                animateFloatAsState(
                                    targetValue = fifthShadowPoint,
                                    animationSpec = tween(delayMillis = backgroundAnimationDelay * 3)
                                ).value
                            ),
                            colors.primary.copy(
                                animateFloatAsState(
                                    targetValue = fourthShadowPoint,
                                    animationSpec = tween(delayMillis = backgroundAnimationDelay * 2)
                                ).value
                            ),
                            colors.primary.copy(
                                animateFloatAsState(
                                    targetValue = thirdShadowPoint,
                                    animationSpec = tween(delayMillis = backgroundAnimationDelay)
                                ).value
                            ),
                            colors.primary,
                            colors.primary,
                        )
                    )
                )
        )

        NavHost(
            navController = navController,
            startDestination = WelcomeScreenRoute,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable<WelcomeScreenRoute> {
                thirdShadowPoint = 1f
                fourthShadowPoint = 0.88f
                fifthShadowPoint = 0.6f
                sixthShadowPoint = 0.5f
                seventhShadowPoint = 0.5f

                userViewModel.setUser(null)

                WelcomeScreen(
                    navController = navController,
                    logInWithGoogle = {

                          userViewModel.logInWithGoogle()

                    }
                )
            }


            composable<LogInScreenRoute> {
                thirdShadowPoint = 1f
                fourthShadowPoint = 1f
                fifthShadowPoint = 0.58f
                sixthShadowPoint = 0.5f
                seventhShadowPoint = 0.5f

                LogInScreen(
                    stepsNavController = navController,
                    logIn = { email, password ->  userViewModel.logIn(email, password)}
                )
            }

            composable<SignUpScreenRoute>{
                val user by userViewModel.user.collectAsStateWithLifecycle()

                thirdShadowPoint = 1f
                fourthShadowPoint = 1f
                fifthShadowPoint = 1f
                sixthShadowPoint = 0.7f
                seventhShadowPoint = 0.5f

                SignUpScreen(
                    stepsNavController = navController,
                    user = user,
                    setUser = { newUser -> userViewModel.setUser(newUser) }
                )
            }

            composable<SelectMediaFormatScreenRoute> {
                val user = userViewModel.user.collectAsStateWithLifecycle()

                user.value?.let{
                    SelectMediaCategoriesScreen(
                        stepsNavController = navController,
                        user = it,
                        setUser = {newUser -> userViewModel.setUser(newUser)}
                    )
                }
            }

            composable<SelectMediaGenresScreenRoute> {
                val user = userViewModel.user.collectAsStateWithLifecycle()

                user.value?.let{
                    SelectMediaGenresScreen(
                        stepsNavController = navController,
                        user = it,
                        setUser = { newUser -> userViewModel.setUser(newUser) },
                        signUp = { userToAdd ->
                            userViewModel.signUp(
                                user = userToAdd,
                                onSuccess = { uid -> userViewModel.addOrUpdateUser(userToAdd.copy(id = uid))}
                            )
                        }
                    )
                }
            }
        }
    }
}