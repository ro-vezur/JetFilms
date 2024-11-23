package com.example.jetfilms.Components.InputFields

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_BUTTON_WIDTH
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.secondaryColor

@Composable
fun SearchField(
    fillWidth: Float = 0.9f,
    height: Dp = (BASE_BUTTON_HEIGHT).sdp,
    shape: RoundedCornerShape = RoundedCornerShape(20.sdp),
    singleLine:Boolean = true,
    text: String,
    onTextChange: (value: String) -> Unit,
    onSearchClick: () -> Unit = {},
    clearText: () -> Unit = {},
    cancelRequest: () -> Unit = {},
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp, Color.Transparent),
    focusedBorder: BorderStroke = BorderStroke(
        1.sdp, Brush.horizontalGradient(
            listOf(buttonsColor1, buttonsColor2)
        )
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    requestSent: Boolean = false,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val isTextBlank = text.isBlank()

    var barWidth by remember{ mutableStateOf(fillWidth) }
    val animatedBarWidth = animateFloatAsState(targetValue = barWidth)
    val requestedBarWidth = fillWidth - .15f

    LaunchedEffect(requestSent) {
        barWidth = if(!requestSent){
            fillWidth
        } else{
            if(requestedBarWidth < 0){
                0f
            } else{
                requestedBarWidth
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedBarWidth.value)
                .height(height / 1.05f)
                .clip(shape)
                .background(secondaryColor)
                .border(if (isFocused) focusedBorder else unfocusedBorder, shape)
        ) {
            BasicTextField(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(secondaryColor.copy(0.78f), secondaryColor.copy(0.58f))
                        )
                    )
                    .focusRequester(focusRequester),
                value = text,
                onValueChange = onTextChange,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                cursorBrush = SolidColor(Color.White),
                textStyle = typography.bodyLarge.copy(
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.W500,
                    color = if (isFocused) Color.White else Color.LightGray.copy(0.78f),
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.LightGray.copy(0.95f),
                        modifier = Modifier
                            .padding(start = 12.sdp)
                            .size(22.sdp)
                            .clickable {
                                if (!isTextBlank) {
                                    onSearchClick()
                                }
                            }
                    )

                        Box(
                            Modifier
                                .padding(start = 11.sdp)
                                .weight(1f)) {
                            if (text.isEmpty()) {
                                Text(
                                    text = text.ifBlank { "Search" },
                                    style = typography.bodyMedium.copy(
                                        fontSize = 14.ssp,
                                        fontWeight = FontWeight.W400,
                                        color = if (isFocused) Color.White else Color.LightGray.copy(
                                            0.78f
                                        )
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                )
                            }
                            innerTextField()
                        }
                        if(!isTextBlank){
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(end = 10.sdp)
                                    .size(19.sdp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray.copy(0.35f))
                            ){
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "close",
                                    tint = Color.Black.copy(0.7f),
                                    modifier = modifier
                                        .size(15.sdp)
                                        .clickable { clearText() }
                                )
                            }
                        }
                    }
                }
            )
        }

        if(requestSent && animatedBarWidth.value <= requestedBarWidth){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 12.sdp)
                    .height(height)
                    .clickable { cancelRequest() }
            ){
                Text(
                    text = "Cancel",
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.ssp,
                        color = Color.White
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun previ() {
    SearchField(text = "niga", onTextChange = {}, requestSent = true)
}