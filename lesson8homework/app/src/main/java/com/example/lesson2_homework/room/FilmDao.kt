package com.example.lesson2_homework.room

import androidx.room.*

@Dao
interface FilmDao {
    @Query("SELECT * FROM FilmEntity")
    fun all(): List<FilmEntity>
    @Query("SELECT * FROM FilmEntity WHERE id=:id")
    fun getFilmById(id:Int): FilmEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FilmEntity)
    @Update
    fun update(entity: FilmEntity)
    @Delete
    fun delete(entity: FilmEntity)
}