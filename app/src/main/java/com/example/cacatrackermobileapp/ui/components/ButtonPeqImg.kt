package com.example.cacatrackermobileapp.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Button(
        onClick = {
            onClickFunction1()
        },
        modifier = Modifier
            .width((size ?: 280).dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(icon,text)
            Text(
                text = text,
                color = Color.White,
                fontSize = if (size == null) 18.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ButtonPQCameraPreview() {
    ButtonPQImg(150, "Tomar Foto", {}, Icons.Default.AddCircle)
}

@Preview
@Composable
fun ButtonPQFolderPreview() {
    ButtonPQImg(200, "Seleccionar Foto", {}, Icons.Default.AccountBox)
}