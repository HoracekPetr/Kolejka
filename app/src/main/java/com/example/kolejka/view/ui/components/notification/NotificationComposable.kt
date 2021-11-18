package com.example.kolejka.view.ui.components.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.kolejka.R
import com.example.kolejka.models.notification.Notification
import com.example.kolejka.models.notification.NotificationAction
import com.example.kolejka.view.theme.*

@Composable
fun NotificationComposable(
        notification: Notification,
        modifier: Modifier = Modifier,
) {
    Card(
            shape = RoundedCornerShape(Space8),
            backgroundColor = LightGray,
            elevation = Space4
    ) {
        Row(modifier = Modifier
                .fillMaxSize()
                .padding(PaddingSmall), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            val fillerText = when(notification.notificationType){
                is NotificationAction.JoinedEvent -> stringResource(id = R.string.joined)
                is NotificationAction.CalledDibs -> stringResource(id = R.string.wants)
            }

            val notificationText = when(notification.notificationType){
                is NotificationAction.JoinedEvent -> stringResource(id = R.string.your_event)
                is NotificationAction.CalledDibs -> stringResource(id = R.string.your_offer)
            }

            Text(text = buildAnnotatedString {
                val boldStyle = SpanStyle(fontWeight = FontWeight.Bold, color = DarkPurple)
                withStyle(boldStyle){
                    append(notification.username)
                }
                append(" $fillerText ")
                withStyle(boldStyle){
                    append(notificationText)
                }
            }, fontSize = 10.sp)
            
            Text(text = notification.formattedTime, textAlign = TextAlign.Right, fontSize = 10.sp)

        }

    }
    Spacer(modifier = Modifier.height(Space8))

}