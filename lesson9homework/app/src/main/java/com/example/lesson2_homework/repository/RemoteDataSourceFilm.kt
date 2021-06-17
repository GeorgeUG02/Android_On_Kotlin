package com.example.lesson2_homework.repository

import com.example.lesson2_homework.BuildConfig
import com.example.lesson2_homework.model.FilmDescription
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.model.FilmsDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceFilm() {
    private val filmApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmAPI::class.java)

    fun getFilmDetails(
        id:Int,
        lang:String,
        callback: Callback<FilmDescriptionDTO>
    ) {
        filmApi.getFilm(id,BuildConfig.FILMS_API_KEY, lang).enqueue(callback)
    }
}