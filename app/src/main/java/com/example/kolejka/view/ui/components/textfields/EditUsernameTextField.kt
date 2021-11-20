package com.example.kolejka.view.ui.components.textfields

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.DarkGray
import com.example.kolejka.view.theme.LightBackgroundWhite

@Composable
fun EditUsernameTextField(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        textStyle = MaterialTheme.typography.body1,
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = hint, style = MaterialTheme.typography.body1, color = DarkGray)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = LightBackgroundWhite),
        maxLines = 1,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}