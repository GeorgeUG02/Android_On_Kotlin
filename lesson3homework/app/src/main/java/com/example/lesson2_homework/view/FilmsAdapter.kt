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

class FilmsAdapter constructor(context: Context?, private val films: List<Film>,onClickListener: onFilmClickListener) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val onClickListener:onFilmClickListener=onClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }
    interface onFilmClickListener{
        fun onFilmClick(film:Film,position: Int)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        holder.imageView.setImageResource(film.image)
        holder.nameView.text = film.title
        holder.dateView.text = film.date
        holder.ratingView.text=film.rating
        holder.itemView.setOnClickListener(View.OnClickListener { onClickListener.onFilmClick(film,position) })
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
            imageView = view.findViewById<View>(R.id.image) as ImageView
            nameView = view.findViewById<View>(R.id.name) as TextView
            dateView = view.findViewById<View>(R.id.date) as TextView
            ratingView=view.findViewById<View>(R.id.rating) as TextView
        }
    }
    init {
        inflater = LayoutInflater.from(context)
    }
}