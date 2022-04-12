package com.example.kolejka.view.ui.screens.post_detail_screen

sealed class PostDetailEvent{
    object Comment: PostDetailEvent()
    object AddMember: PostDetailEvent()
    object DeletePost: PostDetailEvent()
    object EditPost: PostDetailEvent()
    object ConfirmPostDelete: PostDetailEvent()
    object DismissPostDelete: PostDetailEvent()
    object DeleteComment: PostDetailEvent()
    data class ConfirmCommentDelete(val commentId: String?): PostDetailEvent()
    object DismissCommentDelete: PostDetailEvent()
}
