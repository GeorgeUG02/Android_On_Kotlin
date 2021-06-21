package com.example.lesson2_homework.repository

import com.example.lesson2_homework.model.FilmsDTO

interface FilmsRepository {
        fun getFilmsFromServer(
            lang:String,
            callback: retrofit2.Callback<FilmsDTO>
        )
    }