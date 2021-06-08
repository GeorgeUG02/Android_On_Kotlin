package com.example.lesson2_homework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_homework.model.FilmsDTO
import com.example.lesson2_homework.repository.FilmsRepository
import com.example.lesson2_homework.repository.FilmsRepositoryImpl
import com.example.lesson2_homework.repository.RemoteDataSourceFilms
import com.example.lesson2_homework.utils.convertDtoToModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class MainViewModel(private val listLiveData: MutableLiveData<AppStateList> = MutableLiveData(),
                    private val filmsRepositoryImpl: FilmsRepository = FilmsRepositoryImpl(RemoteDataSourceFilms())
) :
        ViewModel() {
    companion object{
        val LOADING = AppStateList.Loading
        val SUCCESS = AppStateList.Success(listOf())
    }
    fun getLiveData() = listLiveData
    fun getFilmsFromLocalSource() = getDataFromLocalSource()
    fun getFilmsFromRemoteSource() {
        listLiveData.value = AppStateList.Loading
        filmsRepositoryImpl.getFilmsFromServer("ru-Ru",callback)
    }
    private val callback = object :
        Callback<FilmsDTO> {
        override fun onResponse(call: Call<FilmsDTO>, response:
        Response<FilmsDTO>
        ) {
            val serverResponse: FilmsDTO? = response.body()
            listLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppStateList.Error(Throwable(SERVER_ERROR))
                }
            )
        }
        override fun onFailure(call: Call<FilmsDTO>, t: Throwable) {
            listLiveData.postValue(AppStateList.Error(Throwable(t.message ?:
            REQUEST_ERROR)))
        }
        private fun checkResponse(serverResponse: FilmsDTO): AppStateList {
            val results = serverResponse.results
            return AppStateList.Success(convertDtoToModelList(serverResponse))
        }
    }
    private fun getDataFromLocalSource() {
        listLiveData.value = LOADING
        Thread {
            sleep(1000)
            listLiveData.postValue(SUCCESS)
        }.start()
    }
}