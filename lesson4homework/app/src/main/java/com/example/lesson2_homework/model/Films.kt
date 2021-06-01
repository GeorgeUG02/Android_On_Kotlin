package com.example.lesson2_homework.model

import com.example.lesson2_homework.R

class Films (){
    private val films = listOf(Film("Ниже нуля","29.01.2021", R.drawable.wqghdbjmqmwx9keqb42aht6tf68,"6.4"),
        Film("Сверхъестественное","13.09.2005", R.drawable.uu3liac0cwkrvdjouznme4x0ag5,"8.2"),
        Film("Часовой","05.03.2021",R.drawable.amugn1rj9xddp6dyn9oa2uv8m,"6.0"),
        Film("Сила Грома","09.04.2021",R.drawable.duk11vqd4upda7ujrgrgx90xjox,"5.8"),
        Film("Без жалости","29.04.2021",R.drawable.rem96ib0spizbadnkbhkbv5bv,"7.2"),
        Film("Магическая битва","03.10.2020",R.drawable.c7ryxnwc5ffbre9logbtt8zp3qu,"8.5"),
        Film("Джиу-джитсу: Битва за Землю","20.11.2020",R.drawable.uq2epbdln1yl6ohrqchwadom9k,"5.2"))
    fun getFilms() = films
}