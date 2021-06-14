package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsAPI {
    @GET("3/movie/popular")
    fun getFilms(
        @Query("api_key") token: String,
        @Query("language") lang: String
    ): Call<FilmsDTO>
}