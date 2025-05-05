package com.example.cacatrackermobileapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonPQ(
    sizeW: Int? = null,
    sizeH:Int? = null,
    text: String,
    onClickFunction1: () -> Unit,
    onClickFunction2: (() -> Unit)? = null
) {

    val width = (sizeW ?: 280).dp
    val height = (sizeH ?: 35).dp
    val cornerRadius = 12.dp

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(8.dp, RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB0B0B0),
                        Color(0xFF707070)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onClickFunction1()
                onClickFunction2?.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = if (sizeW == null) 18.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ButtonPQPreview1(){
    ButtonPQ(95,null,"Borrar",{},{})
}

@Preview
@Composable
fun ButtonPQPreview2(){
    ButtonPQ(155,null,"Codigo Postal",{},{})
}