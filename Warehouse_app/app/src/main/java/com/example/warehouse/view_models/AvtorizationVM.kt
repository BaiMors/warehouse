package com.example.warehouse.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AvtorizationVM:ViewModel(){

    sealed class Result {
        data class Success(val user: Any) : Result()  // Успех
        data class Error(val message: String) : Result()  // Ошибка
    }

    // Состояние для результата авторизации
    private val _authResult = MutableStateFlow<Result?>(null)
    val authResult: StateFlow<Result?> = _authResult

    private val _regResult = MutableStateFlow<Result?>(null)
    val regResult: StateFlow<Result?> = _regResult

    private val _upsertResult = MutableStateFlow<Result?>(null)
    val upsertResult: StateFlow<Result?> = _upsertResult
}