package com.example.lesson2_homework.utils

import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.model.FilmDescription
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.model.FilmsDTO

    fun convertDtoToModelList(filmsDTO: FilmsDTO): List<Film> {
        var a:ArrayList<Film> = ArrayList<Film>()
        for (i in filmsDTO.results)
        {
            a.add(Film(i.title,i.release_date,i.poster_path,i.vote_average,i.id))
        }
        return a
    }
    fun convertDtoToModelFilm(filmDTO: FilmDescriptionDTO): FilmDescription {
        return FilmDescription(filmDTO.title,filmDTO.release_date,filmDTO.poster_path,filmDTO.vote_average,filmDTO.genres[0].name,filmDTO.overview)
    }