package com.example.kolejka.view.util.errors


sealed class Errors{
    object EmptyField: Errors()
    object NotTULEmail: Errors()
    object InputTooShort: Errors()
    object InvalidEmail: Errors()
    object InvalidPassword: Errors()
}
