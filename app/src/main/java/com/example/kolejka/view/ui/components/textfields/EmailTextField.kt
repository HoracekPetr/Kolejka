package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.*

@Composable
fun EmailTextField(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    isIncorrectEmail: Boolean
) {
    Column() {
        TextField(
            value = text,
            textStyle = MaterialTheme.typography.h2,
            onValueChange = onTextChanged,
            placeholder = {
                Text(text = hint, style = MaterialTheme.typography.h2, color = DarkGray)
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = ExtraLightGray),
            isError = isIncorrectEmail,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        )
        if (isIncorrectEmail) {
            Text(
                text = "Je třeba použít TUL email!",
                color = MaterialTheme.colors.error,
                style = Typography.caption,
                modifier = Modifier.padding(
                    PaddingSmall
                )
            )
        }
    }
}