package com.example.kolejka.view.ui.components.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kolejka.models.News
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.util.PostType

@Composable
fun NewsComposable(
    news: News?,
    onNewsClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingMedium)
            .clip(RoundedCornerShape(Space8))
            .background(MaterialTheme.colors.onBackground)
            .clickable { onNewsClick() },
        elevation = Space12,
        shape = RoundedCornerShape(Space8),
    ) {
        Box(Modifier.fillMaxSize().padding(PaddingSmall)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingSmall),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = news?.title ?: "",
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.primary,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.size(Space8))
                Divider(color = DarkGray)
                Spacer(modifier = Modifier.size(Space8))

                Text(
                    text = news?.description ?: "",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondaryVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.size(Space24))
            }

            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = news?.formattedTime ?: "",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}