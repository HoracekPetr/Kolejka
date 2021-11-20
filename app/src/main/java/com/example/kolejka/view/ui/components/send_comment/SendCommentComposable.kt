package com.example.kolejka.view.ui.components.send_comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kolejka.R
import com.example.kolejka.view.theme.*

@Composable
fun SendCommentComposable(
        modifier: Modifier = Modifier,
        onSendClick: () -> Unit
) {
    Button(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Space12),
            onClick = onSendClick,
            contentPadding = PaddingValues(PaddingSmall),
            colors = ButtonDefaults.buttonColors(backgroundColor = DarkPurple),
            elevation = ButtonDefaults.elevation(Space4)
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(modifier = Modifier.size(18.dp),imageVector = Icons.Filled.Send, contentDescription = stringResource(R.string.send_comment), tint = Color.White)
            Text(text = stringResource(R.string.send), style = Typography.caption)
        }
    }
}