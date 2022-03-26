package com.example.kolejka.use_cases.user

import com.example.kolejka.data.features.user.models.ChangePasswordResult
import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.errors.Errors

class ChangePasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String?,
        newPassword: String,
        newPasswordAgain: String
    ): ChangePasswordResult {
        //return userRepository.changeUserPassword(userId = userId, newPassword = newPassword)
        val newPasswordError = validateNewPassword(newPassword)
        val newPasswordAgainError = validateNewPasswordAgain(newPassword)

        if(newPasswordError != null || newPasswordAgainError != null){
            return ChangePasswordResult(
                newPasswordError = newPasswordError,
                newPasswordAgainError = newPasswordAgainError
            )
        }

        if(newPassword != newPasswordAgain){
            return ChangePasswordResult(
                passwordNotMatching = true
            )
        }

        val result = userRepository.changeUserPassword(userId = userId, newPassword = newPassword)
        return ChangePasswordResult(changePasswordResult = result)
    }
}

private fun validateNewPassword(password: String): Errors?{
    val trimPassword = password.trim()
    if(trimPassword.isBlank()){
        return Errors.EmptyField
    }

    if(trimPassword.length < Constants.MIN_PASSWORD_LENGTH){
        return Errors.InputTooShort
    }

    if(!trimPassword.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$"))){
        return Errors.InvalidPassword
    }

    return null
}

private fun validateNewPasswordAgain(password: String): Errors?{
    val trimPassword = password.trim()
    if(trimPassword.isBlank()){
        return Errors.EmptyField
    }

    if(trimPassword.length < Constants.MIN_PASSWORD_LENGTH){
        return Errors.InputTooShort
    }

    if(!trimPassword.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$"))){
        return Errors.InvalidPassword
    }

    return null
}