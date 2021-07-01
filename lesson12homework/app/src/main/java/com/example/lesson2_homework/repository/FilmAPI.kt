package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmDescriptionDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmAPI {
    @GET("3/movie/{id}")
    fun getFilm(
        @Path("id") id:Int,
        @Query("api_key") token: String,
        @Query("language") lang: String
    ): Call<FilmDescriptionDTO>
}