package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmDescriptionDTO
import retrofit2.Callback

class FilmRepositoryImpl(private val remoteDataSourceFilm: RemoteDataSourceFilm):FilmRepository {
    override fun getFilmFromServer(id:Int,lang: String, callback: Callback<FilmDescriptionDTO>) {
         remoteDataSourceFilm.getFilmDetails(id,lang,callback)
    }

}