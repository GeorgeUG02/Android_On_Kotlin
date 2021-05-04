package com.example.lesson2_homework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_homework.viewmodel.AppState
import com.example.lesson2_homework.model.Films
import java.lang.Thread.sleep


class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> =
                            MutableLiveData()) :
        ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getFilmsFromLocalSource() = getDataFromLocalSource()
    fun getFilmsFromRemoteSource()=getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(Films()))
        }.start()
    }
}