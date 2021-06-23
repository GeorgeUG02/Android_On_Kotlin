package com.example.lesson2_homework.repository

import com.example.lesson2_homework.BuildConfig
import com.example.lesson2_homework.model.FilmsDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceFilms {
    private val filmsApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmsAPI::class.java)

    fun getFilmsDetails(
        lang:String, callback:
        Callback<FilmsDTO>
    ) {
        filmsApi.getFilms(BuildConfig.FILMS_API_KEY, lang).enqueue(callback)
    }
}