package com.example.lesson2_homework.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FilmEntity::class), version = 1, exportSchema =
false)
abstract class FilmDataBase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}