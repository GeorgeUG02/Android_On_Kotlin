package com.example.lesson2_homework.viewmodel

import com.example.lesson2_homework.model.FilmDescription

sealed class AppStateFilm {
    data class Success(val filmData: FilmDescription) : AppStateFilm()
    data class Error(val error: Throwable) : AppStateFilm()
    object Loading : AppStateFilm()
}