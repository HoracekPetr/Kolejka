package com.example.kolejka.use_cases.user

import android.content.SharedPreferences
import com.example.kolejka.data.util.Constants.USER_ID

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String{
        return sharedPreferences.getString(USER_ID, "") ?: ""
    }
}