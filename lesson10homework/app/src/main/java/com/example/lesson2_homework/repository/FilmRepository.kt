package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmDescriptionDTO

interface FilmRepository {
    fun getFilmFromServer(
        id:Int,
        lang:String,
        callback: retrofit2.Callback<FilmDescriptionDTO>
    )
}