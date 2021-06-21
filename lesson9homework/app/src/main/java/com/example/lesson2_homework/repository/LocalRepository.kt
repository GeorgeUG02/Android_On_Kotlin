package com.example.lesson2_homework.repository

import com.example.lesson2_homework.room.FilmEntity

interface LocalRepository {
    fun getFilm(id:Int):FilmEntity
    fun saveEntity(film:FilmEntity)
}