package com.example.kolejka.view.util.uitext

import androidx.annotation.StringRes
import com.example.kolejka.R

sealed class UiText {
    data class StringDynamic(val text: String) : UiText()
    data class StringResource(@StringRes val stringId: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return UiText.StringResource(R.string.an_unknown_error_occured)
        }

        fun cantSendMail(): UiText {
            return UiText.StringResource(R.string.cant_send_email)
        }

        fun sentMail(): UiText {
            return UiText.StringResource(R.string.email_sent)
        }
    }
}
