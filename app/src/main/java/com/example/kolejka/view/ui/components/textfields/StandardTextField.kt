package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.DarkGray

@Composable
fun StandardTextField(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = text,
        textStyle = MaterialTheme.typography.h2,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = hint, style = MaterialTheme.typography.h2, color = DarkGray)
        },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.clip(RoundedCornerShape(10.dp)).fillMaxWidth()
    )
}