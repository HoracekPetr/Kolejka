package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.ExtraLightGray

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    placeholderTextColor: Color,
    textStyle: TextStyle,
    singleLine: Boolean = true,
    placeholderTextStyle: TextStyle,
    placeholderTextAlignment: TextAlign = TextAlign.Start,
    textfieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(backgroundColor = ExtraLightGray),
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        value = text,
        textStyle = textStyle,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = hint, style = placeholderTextStyle, color = placeholderTextColor, textAlign = placeholderTextAlignment )
        },
        singleLine = singleLine,
        colors = textfieldColors,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions
    )
}