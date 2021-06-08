package com.example.lesson2_homework.viewmodel

import com.example.lesson2_homework.model.Films

sealed class AppState {
    data class Success(val filmsData: Films) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}