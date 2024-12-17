package com.example.jetfilms.View.Components.Buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_BUTTON_WIDTH
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
    text:String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: Alignment = Alignment.Center,
    textColor: Color = whiteColor,
    height:Dp = BASE_BUTTON_HEIGHT.sdp,
    width:Dp = BASE_BUTTON_WIDTH.sdp,
    corners:RoundedCornerShape = RoundedCornerShape(12.sdp),
    background: Color = Color.White,
    border:BorderStroke = BorderStroke(1.sdp,Color.Transparent),
    image: @Composable (modifier:Modifier) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(width)
            .height(height)
            .clip(corners)
            .background(background)
            .border(border = border, corners)
            .clickable { onClick() }
    ){
        TextButtonsContent(
            image = image,
            text = text,
            textStyle = textStyle.copy(color = textColor),
            modifier = Modifier.align(textAlign)
        )
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
    text:String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = whiteColor,
    height:Dp = BASE_BUTTON_HEIGHT.sdp,
    width:Dp = BASE_BUTTON_WIDTH.sdp,
    textAlign: Alignment = Alignment.Center,
    corners:RoundedCornerShape = RoundedCornerShape(10.sdp),
    gradient: Brush,
    border:BorderStroke = BorderStroke(1.sdp,Color.Transparent),
    image: @Composable (modifier:Modifier) -> Unit = {}
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(width)
            .height(height)
            .clip(corners)
            .background(gradient)
            .border(border = border, corners)
            .clickable { onClick() }
    ){
        TextButtonsContent(
            image = image,
            text = text,
            textStyle = textStyle.copy(color = textColor),
            Modifier.align(textAlign)
        )
    }
}

@Composable
private fun TextButtonsContent(
    image:@Composable (modifier:Modifier) -> Unit,
    text:String,
    textStyle:TextStyle,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ){

        image(Modifier.align(Alignment.CenterStart).padding(start = 26.sdp))

        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}