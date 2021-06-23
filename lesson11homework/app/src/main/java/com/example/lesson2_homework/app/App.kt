package com.example.lesson2_homework.app

import android.app.Application
import androidx.room.Room
import com.example.lesson2_homework.room.FilmDao
import com.example.lesson2_homework.room.FilmDataBase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
    companion object {
        private var appInstance: App? = null
        private var db: FilmDataBase? = null
        private const val DB_NAME ="Films.db"
        fun getFilmDao(): FilmDao {
            if (db == null) {
                synchronized(FilmDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            FilmDataBase::class.java,
                            DB_NAME)
                            .build()
                    }
                }
            }
            return db!!.filmDao()
        }
    }
}