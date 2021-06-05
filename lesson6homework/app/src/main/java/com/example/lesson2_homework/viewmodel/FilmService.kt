package com.example.lesson2_homework.viewmodel

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lesson2_homework.BuildConfig
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.view.DETAILS_FILM_DESCRIPTION
import com.example.lesson2_homework.view.DETAILS_INTENT_FILTER
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val FILM_ID="Film id"
class FilmService(name:String="FilmService"):IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        val id=intent!!.getIntExtra(FILM_ID,0)
        if (id!=0) {
            loadFilm(id)
        }
    }
   @RequiresApi(Build.VERSION_CODES.N)
   fun loadFilm(id:Int){
       try {
           val uri =
               URL("https://api.themoviedb.org/3/movie/${id}?api_key=${BuildConfig.FILMS_API_KEY}&language=ru-RU")
               lateinit var urlConnection: HttpsURLConnection
               try {
                   urlConnection = uri.openConnection() as HttpsURLConnection
                   urlConnection.requestMethod = "GET"
                   urlConnection.readTimeout = 10000
                   val bufferedReader =
                       BufferedReader(InputStreamReader(urlConnection.inputStream))
                   val filmDTO: FilmDescriptionDTO =
                       Gson().fromJson(getLines(bufferedReader),
                           FilmDescriptionDTO::class.java)
                   onResponse(filmDTO)
               } catch (e: Exception) {
                   e.printStackTrace()
               } finally {
                   urlConnection.disconnect()
               }
       } catch (e: MalformedURLException) {
           e.printStackTrace()
       }
   }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
    private fun onResponse(film:FilmDescriptionDTO){
        broadcastIntent.putExtra(DETAILS_FILM_DESCRIPTION,film)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}