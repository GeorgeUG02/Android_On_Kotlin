package com.example.lesson2_homework.viewmodel

import com.example.lesson2_homework.model.Film

sealed class AppStateList {
    data class Success(val filmsData: List<Film>) : AppStateList()
    data class Error(val error: Throwable) : AppStateList()
    object Loading : AppStateList()
}