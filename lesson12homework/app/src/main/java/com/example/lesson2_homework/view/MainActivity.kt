package com.example.lesson2_homework.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lesson2_homework.R
import com.example.lesson2_homework.googlemaps.GoogleMapsFragment
import com.example.lesson2_homework.view.content_provider.ContentProviderFragment
import com.example.lesson2_homework.view.main.MainFragment
import com.example.lesson2_homework.viewmodel.MainBroadcastReceiver
var isAdult:Boolean=true
const val IS_ADULT_KEY="ADULT_KEY"
class MainActivity : AppCompatActivity() {
    private val receiver = MainBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        isAdult=getPreferences(Context.MODE_PRIVATE).getBoolean(IS_ADULT_KEY,true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
            registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        if (!getPreferences(Context.MODE_PRIVATE).getBoolean(IS_ADULT_KEY,false)) menu?.findItem(R.id.menu_adult)?.setChecked(true)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_adult -> {
                isAdult=!isAdult
                    with(getPreferences(Context.MODE_PRIVATE).edit()) {
                        putBoolean(IS_ADULT_KEY, isAdult)
                        apply()
                    }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
                if (!item.isChecked)
                item.setChecked(true)
                else{
                    item.setChecked(false)
                }
                true
            }
            R.id.menu_content_provider -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ContentProviderFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_google_maps -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, GoogleMapsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroy(){
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}