package com.example.jetfilms.CustomComposables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.baseButtonHeight
import com.example.jetfilms.baseButtonWidth
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.secondaryColor

@Composable
fun CustomTextField(
    width:Dp = baseButtonWidth.sdp,
    height:Dp = (baseButtonHeight).sdp,
    shape: RoundedCornerShape = RoundedCornerShape(20.sdp),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    singleLine:Boolean = true,
    text: String,
    onTextChange: (value: String) -> Unit,
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp, Color.LightGray.copy(0.6f)),
    focusedBorder: BorderStroke = BorderStroke(
        1.sdp, Brush.horizontalGradient(
            listOf(buttonsColor1, buttonsColor2)
        )
    ),
    leadingIcon: ImageVector? = null,
    placeHolder: String = "",
  //  leadingIcon:@Composable (() -> Unit)? = null,
    trailingIcon: @Composable ((modifier:Modifier) -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Box(
        modifier = modifier
            .width(width)
            .height(height / 1.01f)
            .clip(shape)
            .background(primaryColor)
            .border(if (isFocused) focusedBorder else unfocusedBorder, shape)
    ){
        BasicTextField(
            modifier = modifier
                .fillMaxSize()
                .background(
                    if (isFocused) Brush.horizontalGradient(
                        listOf(secondaryColor.copy(0.52f), secondaryColor.copy(0.38f))
                    ) else Brush.horizontalGradient(
                        listOf(primaryColor, primaryColor)
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
            textStyle = typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                color = if(isFocused) Color.White else Color.LightGray.copy(0.82f),
                fontSize = typography.bodySmall.fontSize * if(visualTransformation == PasswordVisualTransformation()) (1.18f) else(1f) ,
                letterSpacing = if(visualTransformation == PasswordVisualTransformation()) 1.6f.ssp else 0.ssp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) Icon(
                        imageVector = leadingIcon,
                        contentDescription = placeHolder,
                        tint = Color.LightGray.copy(0.9f),
                        modifier = Modifier
                            .padding(start = 12.sdp)
                            .size(20.sdp)
                    )

                    Box(Modifier.padding(start = 11.sdp).weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                text = text.ifBlank { placeHolder },
                                style = typography.bodySmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = if(isFocused) Color.White else Color.LightGray.copy(0.82f)
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon(
                        Modifier.padding(end = 18.sdp)
                            .clip(CircleShape)
                            .size(21.sdp)
                    )
                }
            }
        )

        /*OutlinedTextField(
            textStyle = TextStyle(
                fontSize = if(visualTransformation == PasswordVisualTransformation()) 14.ssp else 13.ssp,
                letterSpacing = if(visualTransformation == PasswordVisualTransformation()) 1f.ssp else 0.ssp
            ),
            singleLine = singleLine,
            shape = shape,
            colors = colors,
            value = text,
            onValueChange = onTextChange,
            interactionSource = interactionSource,
            placeholder = if(placeHolder.isBlank()) null
            else{
                {
                    Text(
                        text = placeHolder,
                        style = typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = typography.bodySmall.fontSize
                        )
                    )
                }
                },
            leadingIcon = if(leadingIcon == null) null
             else {{
                Icon(
                imageVector = leadingIcon,
                contentDescription = "email",
                tint = Color.LightGray.copy(0.9f),
                modifier = Modifier
                    .padding(start = 8.sdp)
                    .size(20.sdp)
            )
            }}
                  ,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = modifier
                .width(width)
                .height(height)
                .background(
                    if (isFocused) Brush.horizontalGradient(
                        listOf(secondaryColor.copy(0.58f), secondaryColor.copy(0.38f))
                    ) else Brush.horizontalGradient(
                        listOf(primaryColor, primaryColor)
                    )
                )
                .focusRequester(focusRequester)
        )*/
    }
}


@Preview
@Composable
private fun afsa(
) {
    CustomTextField(
        colors = BaseTextFieldColors(),
        text = "asf",
        onTextChange = {}
    )
}

@Composable
fun BaseTextFieldColors():TextFieldColors {
    val colors = MaterialTheme.colorScheme

    return TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLabelColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        cursorColor = Color.White,
        unfocusedPlaceholderColor = Color.LightGray.copy(0.82f),
        focusedPlaceholderColor = Color.White,
        unfocusedTextColor = Color.LightGray.copy(0.82f),
        focusedTextColor = Color.White,
    )
}