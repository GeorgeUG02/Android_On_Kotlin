package com.example.lesson2_homework.model

data class FilmsDTO (
    val results:Array<FilmDTO>
)
data class FilmDTO(
    val title:String,
    val release_date:String,
    val vote_average:Double,
    val poster_path:String,
    val id:Int
)

