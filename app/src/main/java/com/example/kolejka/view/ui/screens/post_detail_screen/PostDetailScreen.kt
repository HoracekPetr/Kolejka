package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditAttributes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.comment.CommentComposable
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.post_detail.DeleteCommentDialog
import com.example.kolejka.view.ui.components.post_detail.DeletePostDialog
import com.example.kolejka.view.ui.components.send_comment.SendCommentComposable
import com.example.kolejka.view.util.PostType
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.asString
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun PostDetailScreen(
    navController: NavController,
    viewModel: PostDetailScreenViewModel = hiltViewModel(),
    postId: String? = null
) {
    val post = viewModel.state.value.post
    val requesterId = viewModel.state.value.requesterId
    val comments = viewModel.state.value.comments
    val showDeletePostDialog = viewModel.state.value.showDeletePostDialog
    val showDeleteCommentDialog = viewModel.state.value.showDeleteCommentDialog

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val localContext = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext)
                    )
                }

                else -> {}
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(postId ?: "") }
    ) {
        Scaffold(scaffoldState = scaffoldState) {
            Box(Modifier.fillMaxSize()) {

                if (viewModel.state.value.isPostLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = DarkPurple
                    )
                } else {
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
                                    Spacer(modifier = Modifier.size(Space24))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        //TITLE

                                        Text(
                                            modifier = Modifier.weight(4f),
                                            text = post?.title ?: "",
                                            style = if (post?.title?.length ?: 0 < 15) MaterialTheme.typography.body1 else TextStyle(
                                                fontFamily = roboto_mono,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 18.sp
                                            ),
                                            color = MaterialTheme.colors.onSurface,
                                            overflow = TextOverflow.Clip,
                                            maxLines = 1
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f, false),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceEvenly
                                        ) {

                                            Image(
                                                modifier = Modifier
                                                    .size(PostDetailProfileSize)
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        if (post?.userId == requesterId) {
                                                            navController.popBackStack()
                                                            navController.navigate(Screen.ProfileScreen.route)
                                                        } else {
                                                            navController.popBackStack()
                                                            navController.navigate(Screen.OtherUserScreen.route + "?userId=${post?.userId}")
                                                        }
                                                    },
                                                contentScale = ContentScale.Fit,
                                                painter = rememberImagePainter(post?.profilePictureUrl),
                                                contentDescription = "Profile Image",
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.size(Space16))
                                    //DESCRIPTION

                                    Text(
                                        text = post?.description ?: "",
                                        style = MaterialTheme.typography.subtitle1,
                                        color = MaterialTheme.colors.onSurface
                                    )

                                    Spacer(modifier = Modifier.size(Space12))
                                    Divider(color = MaterialTheme.colors.onSurface)
                                    Spacer(modifier = Modifier.size(Space12))


                                    //DATE

                                    if (post?.type == PostType.Event.type) {
                                        Row {
                                            Text(
                                                text = stringResource(id = R.string.date) + ": ",
                                                style = MaterialTheme.typography.caption,
                                                color = MaterialTheme.colors.secondaryVariant
                                            )
                                            Text(
                                                text = post?.date ?: "",
                                                style = MaterialTheme.typography.caption,
                                                color = MaterialTheme.colors.onSurface
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.size(Space12))

                                    //LOCATION

                                    Row {
                                        Text(
                                            text = stringResource(id = R.string.location) + ": ",
                                            style = MaterialTheme.typography.caption,
                                            color = MaterialTheme.colors.secondaryVariant
                                        )
                                        Text(
                                            text = post?.location ?: "",
                                            style = MaterialTheme.typography.caption,
                                            color = MaterialTheme.colors.onSurface
                                        )
                                    }

                                    Spacer(modifier = Modifier.size(Space8))
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
                                                style = MaterialTheme.typography.h3,
                                                color = DarkPurple
                                            )
                                            Text(
                                                text = "${post?.available} / ${post?.limit}",
                                                style = MaterialTheme.typography.h3,
                                                color = MaterialTheme.colors.secondaryVariant
                                            )
                                        }
                                        if (requesterId != post?.userId) {
                                            Button(
                                                onClick = {
                                                    if (post?.available ?: 0 > -1) {
                                                        viewModel.onEvent(PostDetailEvent.AddMember)
                                                    }
                                                },
                                                //enabled = post?.available != 0,
                                                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                                                contentPadding = PaddingValues(Space4)
                                            )
                                            {
                                                if (viewModel.state.value.isLoading) {
                                                    CircularProgressIndicator(color = LightBackgroundWhite)
                                                } else {
                                                    Text(
                                                        text = if ((requesterId
                                                                ?: "") in post?.members ?: emptyList()
                                                        ) stringResource(R.string.leave) else stringResource(
                                                            R.string.join
                                                        ),
                                                        style = MaterialTheme.typography.subtitle1
                                                    )
                                                }
                                            }
                                        } else {
                                            Row {

                                                IconButton(onClick = {
                                                    viewModel.onEvent(
                                                        PostDetailEvent.EditPost
                                                    )
                                                }) {
                                                    Icon(
                                                        modifier = Modifier.size(30.dp),
                                                        imageVector = Icons.Default.Edit,
                                                        contentDescription = "Edit post",
                                                        tint = MaterialTheme.colors.primary
                                                    )
                                                }

                                                IconButton(onClick = {
                                                    viewModel.onEvent(
                                                        PostDetailEvent.DeletePost
                                                    )
                                                }) {
                                                    Icon(
                                                        modifier = Modifier.size(30.dp),
                                                        tint = MaterialTheme.colors.primary,
                                                        imageVector = Icons.Default.DeleteOutline,
                                                        contentDescription = "Delete post"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(Space4))
                                    Divider(color = DarkGray)
                                    Spacer(modifier = Modifier.size(Space2))
                                }
                                if (showDeletePostDialog) {
                                    DeletePostDialog(onDismissRequestClick = {
                                        viewModel.onEvent(
                                            PostDetailEvent.DismissPostDelete
                                        )
                                    }) {
                                        viewModel.onEvent(PostDetailEvent.ConfirmPostDelete)
                                        navController.popBackStack()
                                        navController.navigate(Screen.PostScreen.route) {
                                            popUpTo(Screen.PostScreen.route) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            }
                            if (requesterId == post?.userId || requesterId ?: "" in post?.members ?: emptyList()) {
                                comments?.let { comments ->
                                    itemsIndexed(comments) { index, comment ->
                                        CommentComposable(
                                            comment = comment,
                                            modifier = Modifier.fillMaxWidth(),
                                            commentOwnerId = requesterId ?: "",
                                            deleteComment = false,
                                            onDeleteCommentClick = { comments[index].id },
                                            onConfirmDeleteClick = {
                                                viewModel.onEvent(
                                                    PostDetailEvent.ConfirmCommentDelete(
                                                        comment.id
                                                    )
                                                )
                                            },
                                            onDismissDeleteClick = { "" },
                                            onNavigateProfile = {
                                                if (comment.userId == requesterId) {
                                                    navController.navigate(Screen.ProfileScreen.route)
                                                } else {
                                                    navController.navigate(Screen.OtherUserScreen.route + "?userId=${comment.userId}")
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if (requesterId == post?.userId || requesterId ?: "" in post?.members ?: emptyList()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = PaddingMedium, vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StandardTextField(
                                    modifier = Modifier
                                        .weight(4f)
                                        .border(
                                            1.dp,
                                            Color.DarkGray,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    text = viewModel.commentState.value.text,
                                    hint = stringResource(R.string.your_comment),
                                    onTextChanged = { viewModel.setCommentText(it) },
                                    placeholderTextColor = DarkGray,
                                    textStyle = MaterialTheme.typography.caption,
                                    placeholderTextStyle = MaterialTheme.typography.caption,
                                    textfieldColors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.White,
                                        textColor = MaterialTheme.colors.onSurface
                                    )
                                )
                                Spacer(Modifier.size(Space4))
                                SendCommentComposable(
                                    modifier = Modifier.weight(1f),
                                    sending = viewModel.state.value.isLoading
                                ) {
                                    viewModel.onEvent(PostDetailEvent.Comment)
                                    viewModel.setCommentText("")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

