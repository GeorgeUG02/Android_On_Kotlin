package com.example.lesson2_homework.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Film (var title:String,var date:String,var image:Int,var rating:String):Parcelable