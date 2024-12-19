package com.example.jetfilms.View.Components.InputFields.TextInPutField

import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_BUTTON_WIDTH
import com.example.jetfilms.TEXT_FIELD_MAX_LENGTH
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.errorColor
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.secondaryColor
import com.example.jetfilms.ui.theme.typography

@Composable
fun TextInputField(
    width: Dp = BASE_BUTTON_WIDTH.sdp,
    height: Dp = (BASE_BUTTON_HEIGHT).sdp,
    maxHeight: Dp = height,
    statusPosition: TextInputFieldStatusPosition = TextInputFieldStatusPosition.IN,
    showTextLength: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(20.sdp),
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    text: String,
    textStyle: TextStyle = typography().bodySmall,
    textAlign: Alignment = Alignment.Center,
    textPadding: PaddingValues = PaddingValues(),
    isError: Boolean = false,
    onTextChange: (value: String) -> Unit,
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp, Color.LightGray.copy(0.6f)),
    focusedBorder: BorderStroke = BorderStroke(1.sdp, blueHorizontalGradient),
    errorBorder: BorderStroke = BorderStroke(1.sdp, errorColor),
    leadingIcon: ImageVector? = null,
    placeHolder: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier,
) {
    val typography = MaterialTheme.typography
    typography.bodySmall
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val border = if (isError) errorBorder
    else { if (isFocused && !readOnly) focusedBorder else unfocusedBorder }

    val background = Brush.Companion.horizontalGradient(
        if (isFocused && !readOnly)
            listOf(secondaryColor.copy(0.52f), secondaryColor.copy(0.38f))
        else listOf(primaryColor, primaryColor)
    )

    val textColor = if(!readOnly) {
        if (isError) errorColor else {
            if (isFocused) Color.White else Color.LightGray.copy(0.82f)
        }
    } else Color.White

    val leadingIconColor = if(isError) errorColor else Color.LightGray.copy(0.9f)

    Column(
        modifier = Modifier
    ){
        Box(
            contentAlignment = textAlign,
            modifier = modifier
                .focusable(isFocused, interactionSource)
                .width(width)
                .heightIn(height, maxHeight)
                .clip(shape)
                .background(background)
                .border(border, shape)
                .focusRequester(focusRequester)
                .clickable {
                    focusRequester.requestFocus()
                }
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(textPadding)
                    .focusRequester(focusRequester)
                    .onKeyEvent { event ->
                        if( event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                            focusManager.clearFocus()
                            true
                        } else {
                            false
                        }
                    },
                value = text,
                onValueChange = onTextChange,
                maxLines = maxLines,
                singleLine = singleLine,
                readOnly = readOnly,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                cursorBrush = SolidColor(Color.White),
                textStyle = textStyle.copy(
                    fontWeight = FontWeight.Normal,
                    color = textColor,
                    fontSize = textStyle.fontSize * if (visualTransformation == PasswordVisualTransformation()) (1.18f) else (1f),
                    letterSpacing = if (visualTransformation == PasswordVisualTransformation()) 1.ssp else 0.ssp
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .heightIn(20.sdp, maxHeight),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (leadingIcon != null) {
                            Icon(
                                imageVector = leadingIcon,
                                contentDescription = placeHolder,
                                tint = leadingIconColor,
                                modifier = Modifier
                                    .padding(start = 12.sdp)
                                    .size(20.sdp)
                            )
                        }

                        Box(
                            Modifier
                                .padding(horizontal = 10.sdp)
                                .weight(1f)
                        ) {
                            if (text.isBlank()) {
                                Text(
                                    text = text.ifBlank { placeHolder },
                                    style = typography.bodySmall.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = textColor
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                )
                            }

                            innerTextField()
                        }
                        if (trailingIcon != null && statusPosition == TextInputFieldStatusPosition.IN) {
                            trailingIcon()
                        }
                    }
                }
            )
        }

        if(showTextLength || trailingIcon != null && statusPosition == TextInputFieldStatusPosition.OUT) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.sdp),
                modifier = Modifier
                    .padding(top = 6.sdp, start = 7.sdp)
                    .height(20.sdp)
            ) {

                if (showTextLength) {
                    Text(
                        text = "${text.length}/$TEXT_FIELD_MAX_LENGTH",
                        style = typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = textColor
                        ),
                        modifier = Modifier
                    )
                }

                if (trailingIcon != null && statusPosition == TextInputFieldStatusPosition.OUT) {
                    trailingIcon()
                }
            }
        }
    }
}


@Preview
@Composable
private fun afsa(
) {
    TextInputField(
        text = "asf",
        onTextChange = {}
    )
}
