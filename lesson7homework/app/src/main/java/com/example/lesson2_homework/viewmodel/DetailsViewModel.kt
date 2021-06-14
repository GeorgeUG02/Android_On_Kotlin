package com.example.lesson2_homework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_homework.model.FilmDTO
import com.example.lesson2_homework.model.FilmDescription
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.model.FilmsDTO
import com.example.lesson2_homework.repository.FilmRepository
import com.example.lesson2_homework.repository.FilmRepositoryImpl
import com.example.lesson2_homework.repository.RemoteDataSourceFilm
import com.example.lesson2_homework.utils.convertDtoToModelFilm
import com.example.lesson2_homework.utils.convertDtoToModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class DetailsViewModel(
    private val filmLiveData: MutableLiveData<AppStateFilm> = MutableLiveData(),
    private val filmRepositoryImpl: FilmRepository = FilmRepositoryImpl(RemoteDataSourceFilm())): ViewModel() {
    fun getLiveData() = filmLiveData
    fun getFilmFromRemoteSource(id:Int){
        filmLiveData.value = AppStateFilm.Loading
        filmRepositoryImpl.getFilmFromServer(id,"ru-Ru",callback)
    }
    private val callback = object :
        Callback<FilmDescriptionDTO> {
        override fun onResponse(call: Call<FilmDescriptionDTO>, response:
        Response<FilmDescriptionDTO>
        ) {
            val serverResponse: FilmDescriptionDTO? = response.body()
            filmLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppStateFilm.Error(Throwable(SERVER_ERROR))
                }
            )
        }
        override fun onFailure(call: Call<FilmDescriptionDTO>, t: Throwable) {
            filmLiveData.postValue(AppStateFilm.Error(Throwable(t.message ?:
            REQUEST_ERROR)))
        }
        private fun checkResponse(serverResponse: FilmDescriptionDTO): AppStateFilm {
            return AppStateFilm.Success(convertDtoToModelFilm(serverResponse))
        }
    }
}