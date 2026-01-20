package com.example.catchdesign.ui.state

import com.example.catchdesign.model.ResponseModel

sealed interface MainUiState {
    object Loading : MainUiState

    data class Success(
        val users: List<ResponseModel>,
        val isRefreshing: Boolean = false
    ) : MainUiState

    data class Error(val error: ErrorState) : MainUiState
}