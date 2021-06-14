package com.example.lesson2_homework.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDescriptionDTO(
    val title:String,
    val release_date:String,
    val vote_average:Double,
    val poster_path:String,
    val genres:Array<Genre>,
    val overview:String
):Parcelable
@Parcelize
data class Genre(
    val name:String
):Parcelable