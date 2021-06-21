package com.example.lesson2_homework.utils

import android.app.Activity
import android.content.Context
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.model.FilmDescription
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.model.FilmsDTO
import com.example.lesson2_homework.room.FilmEntity
import com.example.lesson2_homework.view.IS_ADULT_KEY

fun convertDtoToModelList(filmsDTO: FilmsDTO,activity: Activity): List<Film> {
        val a:ArrayList<Film> = ArrayList()
        for (i in filmsDTO.results)
        {
            if(!i.adult || activity.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_ADULT_KEY,true))
            a.add(Film(i.title,i.release_date,i.poster_path,i.vote_average,i.id))
        }
        return a
    }
    fun convertDtoToModelFilm(filmDTO: FilmDescriptionDTO): FilmDescription {
        return FilmDescription(filmDTO.title,filmDTO.release_date,filmDTO.poster_path,filmDTO.vote_average,filmDTO.genres[0].name,filmDTO.overview)
    }
