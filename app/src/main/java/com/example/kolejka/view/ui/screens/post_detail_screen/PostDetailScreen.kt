package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.comment.CommentComposable
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.send_comment.SendCommentComposable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PostDetailScreen(
    navController: NavController,
    viewModel: PostDetailScreenViewModel = hiltViewModel(),
    postId: String? = null
) {

    val post = viewModel.state.value.post
    val requesterId = viewModel.state.value.requesterId
    val comments = viewModel.state.value.comments

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(postId ?: "") }
    ) {
        Column(modifier = Modifier.fillMaxSize())
        {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PaddingMedium)
                    ) {
                        Image(
                            modifier = Modifier
                                .clip(RoundedCornerShape(Space8))
                                .fillMaxWidth()
                                .height(PostHeight),
                            contentScale = ContentScale.FillBounds,
                            painter = rememberImagePainter(data = post?.postPictureUrl),
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
                                text = post?.title ?: "",
                                style = MaterialTheme.typography.body1,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Column(
                                modifier = Modifier.weight(2f, false),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(PostDetailProfileSize)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Fit,
                                    painter = rememberImagePainter(post?.profilePictureUrl),
                                    contentDescription = "Profile Image",
                                )
                                Text(
                                    text = post?.username ?: "",
                                    style = Typography.h5,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(Space8))
                        Divider(color = DarkGray)
                        Spacer(modifier = Modifier.size(Space8))
                        Text(
                            text = post?.description ?: "",
                            style = MaterialTheme.typography.caption,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.size(Space12))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.available) + ": ",
                                    style = MaterialTheme.typography.body1
                                )
                                Text(
                                    text = "${(post?.limit ?: 0) - (post?.members?.size ?: 0)   + 1} / ${post?.limit}",
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            if (requesterId != post?.userId) {
                                Button(
                                    onClick = {
                                        if (post?.available ?: 0 > 0) {
                                            //viewModel.addPostMember(postId = postId ?: "")
                                            viewModel.onEvent(PostDetailEvent.AddMember)
                                        }
                                    },
                                    enabled = post?.available != 0,
                                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                )
                                {
                                    Text(
                                        text = if((requesterId ?: "") in post?.members ?: emptyList()) stringResource(R.string.leave) else stringResource(R.string.join),
                                        style = MaterialTheme.typography.h3
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(Space4))
                        Divider(color = DarkGray)
                        Spacer(modifier = Modifier.size(Space8))
                    }
                }
                if (requesterId == post?.userId || requesterId ?: "" in post?.members ?: emptyList()) {
                    comments?.let { comments ->
                        items(comments) { comment ->
                            CommentComposable(
                                comment = comment,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            if(requesterId == post?.userId || requesterId ?: "" in post?.members ?: emptyList()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextField(
                        modifier = Modifier
                            .weight(4f)
                            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp)),
                        text = viewModel.commentState.value.text,
                        hint = stringResource(R.string.your_comment),
                        onTextChanged = { viewModel.setCommentText(it) },
                        placeholderTextColor = DarkGray,
                        textStyle = MaterialTheme.typography.caption,
                        placeholderTextStyle = MaterialTheme.typography.caption,
                        textfieldColors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                    )
                    Spacer(Modifier.size(Space4))
                    SendCommentComposable(modifier = Modifier.weight(1f)) {
/*                        postId?.let { postId ->
                            viewModel.createComment(postId)
                            viewModel.setCommentText("")
                        }*/
                        viewModel.onEvent(PostDetailEvent.Comment)
                        viewModel.setCommentText("")
                    }
                }
            }
        }
    }
}