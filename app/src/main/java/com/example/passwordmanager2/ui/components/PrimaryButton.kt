package com.example.passwordmanager2.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(

    text: String,

    enabled: Boolean = true,

    onClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Button(

        onClick = onClick,

        enabled = enabled,

        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7C90AA)
        )
    ) {

        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}