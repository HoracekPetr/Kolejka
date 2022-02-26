package com.example.kolejka.view.ui.components.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.kolejka.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.kolejka.models.Comment
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.post_detail.DeleteCommentDialog
import com.example.kolejka.view.ui.screens.post_detail_screen.PostDetailEvent

@Composable
fun CommentComposable(
    modifier: Modifier = Modifier,
    comment: Comment = Comment(),
    commentOwnerId: String,
    onDeleteCommentClick: () -> String,
    onConfirmDeleteClick: () -> Unit,
    onDismissDeleteClick: () -> String,
    deleteComment: Boolean = false
) {

    var commentId by remember { mutableStateOf("")}

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(Space8))
            .padding(PaddingMedium),
        elevation = Space4,
        shape = RoundedCornerShape(Space8),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Image(
                        modifier = Modifier
                            .size(PostProfileSize)
                            .weight(1f, fill = false)
                            .clip(CircleShape),
                        painter = rememberImagePainter(data = comment.profilePictureUrl),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Space8))
                    Text(
                        text = comment.username,
                        style = Typography.subtitle2,
                        color = BlackAccent
                    )
                }
                if (comment.userId == commentOwnerId) {
                    IconButton(onClick = {
                        commentId = onDeleteCommentClick()
                    }) {
                        if (comment.id == commentId) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(R.string.ask_delete_comment), style = Typography.subtitle1, color = MediumOpaquePurple)
                                Row {
                                    IconButton(onClick = {onConfirmDeleteClick()}) {
                                        Icon(
                                            modifier = Modifier.size(DeleteCommentDialogIconSize),
                                            imageVector = Icons.Default.Done,
                                            contentDescription = "Delete Comment",
                                            tint = MediumGreen
                                        )
                                    }
                                    IconButton(onClick = {commentId = onDismissDeleteClick()}) {
                                        Icon(
                                            modifier = Modifier.size(DeleteCommentDialogIconSize),
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Dismiss",
                                            tint = MediumRed
                                        )
                                    }
                                }
                            }
                        } else {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete comment",
                                tint = DarkPurple
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(Space8))
            Text(text = comment.comment, style = Typography.caption, color = DarkGray)
        }
    }
}