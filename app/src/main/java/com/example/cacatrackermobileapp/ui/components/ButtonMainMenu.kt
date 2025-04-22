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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonCT(
    size: Int?,
    text: String,
    onClickFunction: () -> Unit
) {
    if (size == null) {
        Button(
            onClick = onClickFunction,
            modifier = Modifier
                .width(280.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            ),
            shape = RoundedCornerShape(8.dp)

        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Button(
            onClick = onClickFunction,
            modifier = Modifier.width(size.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            ),
            shape = RoundedCornerShape(8.dp)

        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
