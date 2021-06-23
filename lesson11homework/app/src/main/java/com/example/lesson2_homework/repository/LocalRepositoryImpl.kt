package com.example.lesson2_homework.repository

import com.example.lesson2_homework.room.FilmDao
import com.example.lesson2_homework.room.FilmEntity

class LocalRepositoryImpl(private val localDataSource: FilmDao):LocalRepository {
    override fun getFilm(id:Int):FilmEntity{
       return localDataSource.getFilmById(id)
    }
    override fun saveEntity(film: FilmEntity){
        localDataSource.insert(film)
    }
}