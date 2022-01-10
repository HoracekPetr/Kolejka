package com.example.kolejka.view.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.models.Post
import com.example.kolejka.view.theme.*

@Composable
fun PostComposable(
        post: Post,
        onPostClick: () -> Unit
) {
    Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingMedium)
                .clip(RoundedCornerShape(Space12))
                .clickable { onPostClick() }
    ) {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(PaddingMedium),
                horizontalAlignment = Alignment.Start
        ) {
            Image(
                    modifier = Modifier
                            .clip(RoundedCornerShape(Space8))
                            .fillMaxWidth()
                            .height(PostHeight),
                    contentScale = ContentScale.FillBounds,
                    painter = rememberImagePainter(data = post.postPictureUrl),
                    contentDescription = "Post Image"
            )
            Spacer(modifier = Modifier.size(Space12))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        modifier = Modifier.weight(4f),
                        text = post.title,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                )
                Image(
                        modifier = Modifier
                                .size(PostProfileSize)
                                .weight(1f, fill = false)
                                .clip(CircleShape),
                        painter = painterResource(id = R.drawable.petr),
                        contentDescription = "Profile Image",
                )
            }
            Spacer(modifier = Modifier.size(Space8))
            Divider(color = DarkGray)
            Spacer(modifier = Modifier.size(Space8))
            Text(
                    text = post.description,
                    style = MaterialTheme.typography.caption,
                    color = DarkGray,
                    overflow = TextOverflow.Ellipsis
            )
        }
    }
}