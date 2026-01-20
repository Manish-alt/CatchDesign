package com.example.catchdesign.ui.state

data class ErrorState(
    val message: String,
    val throwable: Throwable? = null,
    val errorCode: Int? = null
)