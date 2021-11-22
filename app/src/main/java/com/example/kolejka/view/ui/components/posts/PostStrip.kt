package com.example.kolejka.view.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kolejka.R
import com.example.kolejka.models.Post
import com.example.kolejka.view.theme.*

@Composable
fun PostStrip(
    modifier: Modifier = Modifier,
    post: Post
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Space12),
        elevation = Space8,
        backgroundColor = if (post.isEvent) DarkPurple else LightPurple
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(PaddingMedium)),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(Space8))
                    .size(PostStripPicSize)
                    .weight(2f),
                painter = painterResource(id = R.drawable.petr),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(Space4))
            Column(modifier = Modifier.weight(4f),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = post.title, style = MaterialTheme.typography.body1, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    text = if (post.isEvent) stringResource(id = R.string.event) else stringResource(id = R.string.offer),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}