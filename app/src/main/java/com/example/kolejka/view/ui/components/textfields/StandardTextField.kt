package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    textfieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = ExtraLightGray,
        textColor = MaterialTheme.colors.secondaryVariant
    ),
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    error: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorBelow: Boolean = false
) {
    if (errorBelow) {
        Column {
            TextField(
                modifier = modifier
                    .clip(RoundedCornerShape(10.dp)),
                value = text,
                textStyle = textStyle,
                onValueChange = onTextChanged,
                isError = error != "",
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                placeholder = {
                    Text(
                        text = hint,
                        style = placeholderTextStyle,
                        color = placeholderTextColor,
                        textAlign = placeholderTextAlignment
                    )
                },
                singleLine = singleLine,
                colors = textfieldColors,
                maxLines = maxLines,
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation
            )

            if (error != "") {
                Text(
                    text = error,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    } else {
        TextField(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp)),
            value = text,
            textStyle = textStyle,
            onValueChange = onTextChanged,
            isError = error != "",
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = {
                Text(
                    text = hint,
                    style = placeholderTextStyle,
                    color = placeholderTextColor,
                    textAlign = placeholderTextAlignment
                )
            },
            singleLine = singleLine,
            colors = textfieldColors,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        if (error != "") {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}