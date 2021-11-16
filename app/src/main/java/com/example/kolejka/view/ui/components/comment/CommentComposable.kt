package com.example.kolejka.view.ui.components.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.kolejka.R
import androidx.compose.ui.res.painterResource
import com.example.kolejka.models.Comment
import com.example.kolejka.view.theme.*

@Composable
fun CommentComposable(
        modifier: Modifier = Modifier,
        comment: Comment = Comment()
) {
    Card(
            modifier = modifier
                    .clip(RoundedCornerShape(Space8))
                    .padding(PaddingMedium),
            elevation = Space4,
            shape = RoundedCornerShape(Space8),
            backgroundColor = Color.White
    ) {
        Column(
                modifier = Modifier.fillMaxSize().padding(PaddingMedium)
        ) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
            ) {
                Image(
                        modifier = Modifier
                                .size(PostProfileSize)
                                .weight(1f, fill = false)
                                .clip(CircleShape),
                        painter = painterResource(id = R.drawable.petr),
                        contentDescription = null
                )
                Spacer(modifier = Modifier.width(Space8))
                Text(text = comment.username, style = Typography.subtitle2, color = DarkBackgroundGray)
            }
            Spacer(modifier = Modifier.size(Space8))
            Text(text = comment.comment, style = Typography.caption, color = DarkGray)
        }
    }

}