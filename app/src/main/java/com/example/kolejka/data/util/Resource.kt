package com.example.kolejka.data.util

import com.example.kolejka.view.util.uitext.UiText

//SIMPLE RESOURCE - VRACÍ UNIT -> NEVRACÍ ŽÁDNÉ DATA ZPÁTKY
typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null){

    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(uiText: UiText, data: T? = null): Resource<T>(data, uiText)

}
