package com.example.lesson1_homework

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button=findViewById(R.id.button)
        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v:View?){Toast.LENGTH_SHORT
                Toast.makeText(applicationContext,"Button is clicked!",Toast.LENGTH_LONG).show()
        } })
        val cat:Cat=Cat("Vasya",5)
        Toast.makeText(this,"${cat.name}  ${cat.age}",Toast.LENGTH_LONG).show()
        val cat2=cat.copy()
        Toast.makeText(this,"${cat2.name}  ${cat2.age}",Toast.LENGTH_LONG).show()
        val b=Brush.getColor()
        Toast.makeText(this,"$b",Toast.LENGTH_LONG).show()
        for(i in 1..10) {
            print("Hello Kotlin!")
        }
        for(i in 10 downTo 1 step 2) {
            print("Hello Kotlin!")
        }
        val a= arrayOf(1,2,3,4,5)
        for (element in a){
            println(element)
        }
        for (index in 0 until a.size){
            println(index)
        }

    }
}