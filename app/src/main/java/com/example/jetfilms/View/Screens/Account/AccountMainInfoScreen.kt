package com.example.jetfilms.View.Screens.Account

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.jetfilms.BASE_BUTTON_WIDTH
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.R
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.InputFields.TextInputField
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.Purple40
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.darkerGreenColor
import com.example.jetfilms.ui.theme.purpleColor
import com.example.jetfilms.ui.theme.secondaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import com.primex.core.Orange

private val navigateButtonsDividerColor = Color(0xFF3e4b62)

@Composable
fun AccountMainInfoScreen(
    userViewModel: UserViewModel
) {
    val typography = typography()

    val userFlow by userViewModel.user.collectAsStateWithLifecycle()

    val userName = "${userFlow?.firstName?: "User"} ${userFlow?.lastName?: ""}"
    val userImageSize = 67
    val navigateButtonsColumnBackground = Color(0xFF313c50)


    Log.d("user flow",userFlow.toString())
    userFlow.let { user ->

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 32.sdp)
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

        Text(
            text = userFlow?.email?: "",
            style = typography.bodyMedium.copy(color = whiteColor, fontSize = typography.bodyMedium.fontSize/1.08),
            modifier = Modifier
                .padding(top = 11.sdp)
        )

        TextInputField(
            text = "ID: ${userFlow?.id?: " None"}",
            onTextChange = {},
            readOnly = true,
            modifier = Modifier
                .padding(top = 24.sdp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.sdp),
            modifier = Modifier
                .padding(top = 28.sdp)
                .width(BASE_BUTTON_WIDTH.sdp)
                .clip(RoundedCornerShape(10.sdp))
                .background(navigateButtonsColumnBackground)
        ) {
            Spacer(modifier = Modifier.height(5.sdp))
            
            NavigationButtonClass.entries.forEach { data ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.sdp),
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .clickable {  }
                ){
                    NavigationButtonCard(data = data)

                    if (NavigationButtonClass.entries.last() != data) {
                        Divider(
                            thickness = 2f.sdp,
                            color = navigateButtonsDividerColor,
                            modifier = Modifier
                                .padding(bottom = 3.sdp)
                                .fillMaxWidth(0.82f)
                                .clip(
                                    RoundedCornerShape(
                                        bottomStartPercent = 100,
                                        topStartPercent = 100
                                    )
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.sdp))
        }

        TextButton(
            onClick = {
                userViewModel.logOut()
            },
            text = "Log out",
            corners = RoundedCornerShape(12.sdp),
            background = secondaryColor,
            border = BorderStroke(
                1.45f.sdp, blueHorizontalGradient
            ),
            modifier = Modifier
                .padding(top = 34.sdp)
        )
    }
}


@Composable
private fun NavigationButtonCard(data: NavigationButtonClass) {
    val typography = typography()
    val iconSize = 27

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp)
            .clip(RoundedCornerShape(8.sdp))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 8.sdp)
                .size(iconSize.sdp)
                .clip(RoundedCornerShape(10.sdp))
                .background(data.color)
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = "navigate button icon",
                tint = whiteColor,
                modifier = Modifier
                    .size((iconSize-6).sdp))
        }

        Text(
            text = data.text,
            style = typography.bodyMedium.copy(color = whiteColor, fontWeight = FontWeight.W400),
            modifier = Modifier
                .padding(start = 9.sdp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "forward",
            tint = navigateButtonsDividerColor,
            modifier = Modifier
                .padding(end = 5.sdp)
                .size(17.sdp)
        )
    }
}

private enum class NavigationButtonClass(val text: String, val icon: ImageVector, val color: Color) {
    EDIT_PROFILE("Edit Profile",Icons.Default.Person,Color.Blue),
    SETTINGS("Settings",Icons.Default.Settings,Color.Orange),
    CONTACT("Contact JetFilms Support",Icons.Default.SupportAgent, purpleColor),
    ABOUT("About JetFilms", Icons.Default.Info, darkerGreenColor)
}

@Preview
@Composable
private fun prrrr() {
    AccountMainInfoScreen(userViewModel = hiltViewModel())
}