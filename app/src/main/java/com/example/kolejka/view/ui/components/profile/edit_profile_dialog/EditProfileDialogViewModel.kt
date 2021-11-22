package com.example.kolejka.view.ui.components.profile.edit_profile_dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileDialogViewModel @Inject constructor(): ViewModel(){

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    fun setUsername(username:String){
        _username.value = username
    }

    private val _r = mutableStateOf(0f)
    val r: State<Float> = _r

    private val _g = mutableStateOf(0f)
    val g: State<Float> = _g

    private val _b = mutableStateOf(0f)
    val b: State<Float> = _b

    fun setR(r: Float){
        _r.value = r
    }

    fun setG(g: Float){
        _g.value = g
    }

    fun setB(b: Float){
        _b.value = b
    }
}