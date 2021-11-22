package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kolejka.R
import com.example.kolejka.view.theme.DarkGray
import com.example.kolejka.view.theme.ExtraLightGray
import com.example.kolejka.view.theme.LightGray

@Composable
fun PasswordTextField(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityChange: () -> Unit
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
        colors = TextFieldDefaults.textFieldColors(backgroundColor = ExtraLightGray),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                Icon(
                    imageVector = if (isPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff,
                    contentDescription = stringResource(R.string.change_pwd_visibility)
                )
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
    )
}