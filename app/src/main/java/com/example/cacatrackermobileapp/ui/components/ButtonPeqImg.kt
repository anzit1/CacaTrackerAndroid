package com.example.cacatrackermobileapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonPQImg(
    size: Int? = null,
    text: String,
    onClickFunction1: () -> Unit,
    icon: ImageVector
) {
    val width = (size ?: 280).dp
    val height = 42.dp
    val cornerRadius = 12.dp

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(6.dp, RoundedCornerShape(cornerRadius))
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
            onClick = { onClickFunction1() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = PaddingValues(),
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(icon, contentDescription = text, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = if (size == null) 18.sp else 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun ButtonPQCameraPreview() {
    ButtonPQImg(200, "Tomar Foto", {}, Icons.Default.AddCircle)
}

@Preview
@Composable
fun ButtonPQFolderPreview() {
    Row {
        ButtonPQImg(
            160,
            "Buscar Foto",
            { },
            Icons.Default.Search
        )
        Spacer(modifier = Modifier.width(24.dp))
        ButtonPQImg(
            160,
            "Tomar Foto",
            { },
            Icons.Default.AddCircle
        )
    }
}