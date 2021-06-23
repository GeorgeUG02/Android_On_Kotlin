package com.example.lesson2_homework.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntity(
        @PrimaryKey
        val id: Int,
        val title:String,
        val date:String,
        val description:String,
        val rating:Double,
        val genre:String,
        val image:String,
        val commentary:String
)