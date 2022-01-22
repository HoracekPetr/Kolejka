package com.example.kolejka.view.ui.screens.post_detail_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.models.Comment
import com.example.kolejka.view.ui.components.comment.CommentComposable
import com.example.kolejka.models.Post
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.send_comment.SendCommentComposable

@Composable
fun PostDetailScreen(
    navController: NavController,
    viewModel: PostDetailScreenViewModel = hiltViewModel(),
    postId: String? = null
) {

    val post = viewModel.state.value.post

    LazyColumn {
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
                            painter = painterResource(id = R.drawable.petr),
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
                Spacer(modifier = Modifier.size(Space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.available) + ": ",
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = "${post?.available} / ${post?.limit}",
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Button(
                        onClick = {
                            if (post?.available ?: 1 < post?.limit ?: 1) {
                                viewModel.incrementAvailability()
                                post?.available = viewModel.availability.value
                                /*TODO("API CALL přidání člena k postu")*/
                            }
                        },
                        enabled = post?.available ?: 1 < post?.limit ?: 1,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                    {
                        Text(stringResource(R.string.join), style = MaterialTheme.typography.h3)
                    }
                }
                Spacer(modifier = Modifier.size(Space4))
                Divider(color = DarkGray)
                Spacer(modifier = Modifier.size(Space8))
            }
        }
        if (post?.username ?: "" in post?.members ?: emptyList()) {
            items(5) {
                CommentComposable(
                    comment = Comment(
                        username = "Petr Horáček",
                        comment = "Myslím, že to je pěkná kokotina dělat takovouhle aplikaci, ale proti gustu žádnej dišputát xD"
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
/*                    CommentTextField(
                            modifier = Modifier.weight(4f),
                            text = viewModel.commentText.value,
                            hint = stringResource(R.string.your_comment),
                            onTextChanged = { viewModel.setCommentText(it) }
                    )*/
                    StandardTextField(
                        modifier = Modifier
                            .weight(4f)
                            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp)),
                        text = viewModel.commentText.value,
                        hint = stringResource(R.string.your_comment),
                        onTextChanged = { viewModel.setCommentText(it) },
                        placeholderTextColor = DarkGray,
                        textStyle = MaterialTheme.typography.caption,
                        placeholderTextStyle = MaterialTheme.typography.caption,
                        textfieldColors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                    )
                    Spacer(Modifier.size(Space4))
                    SendCommentComposable(modifier = Modifier.weight(1f)) {
                        /*TODO*/
                    }
                }
            }
        }
    }
}