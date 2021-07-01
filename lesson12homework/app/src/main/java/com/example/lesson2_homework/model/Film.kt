package com.example.lesson2_homework.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Film (var title:String,var date:String?,var image:String,var rating:Double,var id:Int):Parcelable