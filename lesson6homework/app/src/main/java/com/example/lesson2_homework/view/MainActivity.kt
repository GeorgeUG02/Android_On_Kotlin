package com.example.lesson2_homework.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson2_homework.R
import com.example.lesson2_homework.viewmodel.MainBroadcastReceiver

class MainActivity : AppCompatActivity() {
    private val receiver = MainBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
            registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }
    override fun onDestroy(){
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}