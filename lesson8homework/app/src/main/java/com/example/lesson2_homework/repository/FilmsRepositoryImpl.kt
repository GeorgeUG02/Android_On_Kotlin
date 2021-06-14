package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmsDTO

class FilmsRepositoryImpl(private val remoteDataSourceFilms:RemoteDataSourceFilms) :FilmsRepository{
        override fun getFilmsFromServer(
            lang:String,
            callback: retrofit2.Callback<FilmsDTO>
        ){
             remoteDataSourceFilms.getFilmsDetails(lang,callback)
        }
}