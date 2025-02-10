package com.example.jetfilms

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetfilms.View.Screens.MainScreen
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ui.theme.JetsFilmsTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = this as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val localSearchHistoryViewModel = staticCompositionLocalOf<SearchHistoryViewModel> {
                error("No Search History VM provided")
            }
            val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()

            JetsFilmsTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                CompositionLocalProvider(localSearchHistoryViewModel provides searchHistoryViewModel) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetsFilmsTheme(dynamicColor = false) {
        MainScreen()
    }
}