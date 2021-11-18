package com.example.kolejka.view.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.DarkGray

@Composable
fun CommentTextField(
        text: String,
        hint: String,
        onTextChanged: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    TextField(
            value = text,
            textStyle = MaterialTheme.typography.caption,
            onValueChange = onTextChanged,
            placeholder = {
                Text(text = hint, style = MaterialTheme.typography.caption, color = DarkGray)
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            maxLines = 1,
            modifier = modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}