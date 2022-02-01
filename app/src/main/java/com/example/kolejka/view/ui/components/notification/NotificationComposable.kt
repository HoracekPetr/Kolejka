package com.example.kolejka.view.ui.components.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.models.notification.Notification
import com.example.kolejka.models.notification.NotificationAction
import com.example.kolejka.models.notification.NotificationType
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.util.Screen

@Composable
fun NotificationComposable(
    notification: Notification?,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(Space8),
        backgroundColor = LightGray,
        elevation = Space4
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fillerText = when (notification?.notificationType) {
                is NotificationType.JoinedEvent -> stringResource(id = R.string.joined)
                is NotificationType.CalledDibs -> stringResource(id = R.string.wants)
                is NotificationType.CommentedOn -> stringResource(id = R.string.commented_on)
                else -> ""
            }

            val notificationText = when (notification?.notificationType) {
                is NotificationType.JoinedEvent -> stringResource(id = R.string.your_event)
                is NotificationType.CalledDibs -> stringResource(id = R.string.your_offer)
                is NotificationType.CommentedOn -> stringResource(id = R.string.your_post)
                else -> ""
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification?.username ?: "",
                    style = TextStyle(color = BlackAccent, fontWeight = FontWeight.Bold),
                    fontSize = 12.sp
                )

                Text(text = " $fillerText ", fontSize = 12.sp)

                Text(
                    modifier = Modifier.clickable {
                        notification?.let {
                            navController.navigate(Screen.PostDetailScreen.route + "?postId=${it.parentId}")
                        }
                    },
                    text = notificationText,
                    style = TextStyle(color = DarkPurple, fontWeight = FontWeight.Bold),
                    fontSize = 14.sp
                )
            }


            Text(
                text = notification?.formattedTime ?: "",
                textAlign = TextAlign.Right,
                fontSize = 10.sp
            )

        }

    }
    Spacer(modifier = Modifier.height(Space8))

}