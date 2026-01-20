package com.example.catchdesign.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchdesign.model.ResponseModel
import com.example.catchdesign.repository.MainRepository
import com.example.catchdesign.ui.state.ErrorState
import com.example.catchdesign.ui.state.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init { loadUsers() }
    fun loadUsers() = viewModelScope.launch {
        if (_uiState.value !is MainUiState.Success) {
            _uiState.value = MainUiState.Loading
        }

        try {
            val users = repository.fetchUsers()
            _uiState.value = MainUiState.Success(users = users)
        } catch (e: Exception) {
            _uiState.value = MainUiState.Error(
                ErrorState(message = e.localizedMessage ?: "Unknown Error", throwable = e)
            )
        }
    }
    fun addUser(user: ResponseModel) = viewModelScope.launch {
        repository.addUser(user)
        loadUsers()
    }
    fun deleteUser(id: Int?) = viewModelScope.launch {
        repository.deleteUser(id)
        loadUsers()
    }
}