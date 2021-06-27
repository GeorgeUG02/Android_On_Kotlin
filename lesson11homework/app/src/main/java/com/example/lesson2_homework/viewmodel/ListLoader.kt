package com.example.lesson2_homework.viewmodel

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.lesson2_homework.BuildConfig
import com.example.lesson2_homework.model.FilmsDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class ListLoader (private val listener: FilmsLoaderListener){
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadPopularFilms() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=${BuildConfig.FILMS_API_KEY}&language=ru-RU")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val filmsDTO: FilmsDTO =
                        Gson().fromJson(getLines(bufferedReader),
                            FilmsDTO::class.java)
                    handler.post { listener.onLoaded(filmsDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
    interface FilmsLoaderListener {
        fun onLoaded(filmsDTO: FilmsDTO)
        fun onFailed(throwable: Throwable)
    }
}