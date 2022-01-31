package com.example.kolejka.view.util

sealed class PostType(val type: Int) {
    object Event : PostType(0)
    object Offer : PostType(1)
}