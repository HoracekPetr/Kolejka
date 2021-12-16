package com.example.kolejka.view.util.errors


sealed class AuthError{
    object EmptyField: AuthError()
    object InputTooShort: AuthError()
    object InvalidEmail: AuthError()
    object InvalidPassword: AuthError()
}
