package com.example.lesson2_homework.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lesson2_homework.R
import com.example.lesson2_homework.databinding.FragmentDetailsBinding
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.model.FilmDescriptionDTO
import com.example.lesson2_homework.model.FilmsDTO
import com.example.lesson2_homework.viewmodel.FILM_ID
import com.example.lesson2_homework.viewmodel.FilmLoader
import com.example.lesson2_homework.viewmodel.FilmService
import com.example.lesson2_homework.viewmodel.ListLoader


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val DETAILS_FILM_DESCRIPTION="Details Film Description"
const val DETAILS_INTENT_FILTER="Details Intent Filter"
class DetailsFragment : Fragment() {
    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
           displayFilm(intent.getParcelableExtra<FilmDescriptionDTO>(DETAILS_FILM_DESCRIPTION)!!)
        }
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver,
                    IntentFilter(DETAILS_INTENT_FILTER)
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
    }
    private val onLoadListener: FilmLoader.FilmLoaderListener =
        object : FilmLoader.FilmLoaderListener {
            override fun onLoaded(filmdescriptionDTO: FilmDescriptionDTO) {
                displayFilm(filmdescriptionDTO)
            }

            override fun onFailed(throwable: Throwable) {
//Обработка ошибки
            }
        }

    private fun displayFilm(filmdescriptionDTO: FilmDescriptionDTO) {
          with(binding){
              title.text=filmdescriptionDTO.title
              date.text=filmdescriptionDTO.release_date
              description.text=filmdescriptionDTO.overview
              rating.text=filmdescriptionDTO.vote_average.toString()
              genre.text=filmdescriptionDTO.genres[0].name
          }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id:Int=arguments!!.getInt(BUNDLE_EXTRA)
        context?.let {
            it.startService(Intent(it, FilmService::class.java).apply {
                putExtra(
                    FILM_ID,
                    id
                )
            })
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        const val BUNDLE_EXTRA="film"
        fun newInstance(bundle: Bundle) :DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}