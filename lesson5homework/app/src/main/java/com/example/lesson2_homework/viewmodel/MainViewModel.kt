package com.example.lesson2_homework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_homework.viewmodel.AppState
import com.example.lesson2_homework.model.Films
import java.lang.Thread.sleep


class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> =
                            MutableLiveData()) :
        ViewModel() {
    companion object{
        val LOADING = AppState.Loading
        val SUCCESS = AppState.Success(Films())
    }
    fun getLiveData() = liveDataToObserve
    fun getFilmsFromLocalSource() = getDataFromLocalSource()
    fun getFilmsFromRemoteSource() = getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = LOADING
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(SUCCESS)
        }.start()
    }
}