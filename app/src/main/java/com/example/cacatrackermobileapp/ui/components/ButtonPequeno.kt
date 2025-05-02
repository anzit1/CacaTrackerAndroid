package com.example.cacatrackermobileapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonPQ(
    size: Int? = null,
    text: String,
    onClickFunction1: () -> Unit,
    onClickFunction2: (() -> Unit)? = null
) {
    Button(
        onClick = {
            onClickFunction1()
            onClickFunction2?.invoke()
        },
        modifier = Modifier
            .width((size ?: 280).dp)
            .height(35.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = if (size == null) 18.sp else 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun ButtonPQPreview1(){
    ButtonPQ(95,"Borrar",{},{})
}

@Preview
@Composable
fun ButtonPQPreview2(){
    ButtonPQ(155,"Codigo Postal",{},{})
}