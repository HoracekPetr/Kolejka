package com.example.kolejka.view.util.errors


sealed class Errors{
    object EmptyField: Errors()
    object InputTooShort: Errors()
    object InvalidEmail: Errors()
    object InvalidPassword: Errors()
}
