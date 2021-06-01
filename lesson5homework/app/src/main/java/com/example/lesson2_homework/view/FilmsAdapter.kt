package com.example.lesson2_homework.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson2_homework.R
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.model.FilmDTO

class FilmsAdapter constructor(context: Context?, private val films: Array<FilmDTO>,onClickListener:onFilmClickListener) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val onClickListener: onFilmClickListener=onClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }
    interface onFilmClickListener{
        fun onFilmClick(id:Int)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        with(holder) {
           // imageView.setImageResource(film.image)
            nameView.text = film.title
            dateView.text = film.release_date
            ratingView.text = film.vote_average.toString()
            itemView.setOnClickListener(View.OnClickListener {
                onClickListener.onFilmClick(film.id)
            })
          /*  itemView.setOnClickListener(View.OnClickListener {
                onClickListener.onFilmClick(
                    film,
                    position
                )
            })
           */

        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val nameView: TextView
        val dateView: TextView
        val ratingView:TextView
        init {
            with(view) {
                imageView = findViewById<View>(R.id.image) as ImageView
                nameView = findViewById<View>(R.id.name) as TextView
                dateView = findViewById<View>(R.id.date) as TextView
                ratingView = findViewById<View>(R.id.rating) as TextView
            }
        }
    }
    init {
        inflater = LayoutInflater.from(context)
    }
}