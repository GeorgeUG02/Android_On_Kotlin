package com.example.lesson2_homework.view.details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lesson2_homework.R
import com.example.lesson2_homework.app.App
import com.example.lesson2_homework.databinding.FragmentDetailsBinding
import com.example.lesson2_homework.model.FilmDescription
import com.example.lesson2_homework.repository.LocalRepository
import com.example.lesson2_homework.repository.LocalRepositoryImpl
import com.example.lesson2_homework.room.FilmEntity
import com.example.lesson2_homework.utils.showSnackBar
import com.example.lesson2_homework.viewmodel.*
import com.squareup.picasso.Picasso
import java.util.concurrent.CompletableFuture


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DetailsFragment : Fragment() {
    private var id_f: Int=0
    private val filmsRepository: LocalRepository = LocalRepositoryImpl(App.getFilmDao())
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var f:CompletableFuture<FilmEntity>
    private lateinit var image:String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id:Int=arguments!!.getInt(BUNDLE_EXTRA)
        f = CompletableFuture.supplyAsync { filmsRepository.getFilm(id) }
        id_f=id
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        super.onDestroy()
        val id=id_f
        val title=binding.title.text.toString()
        val date=binding.date.text.toString()
        val description=binding.description.text.toString()
        val rating=binding.rating.text.toString().toDouble()
        val genre=binding.genre.text.toString()
        val image=this.image
        val comment:String = binding.commentary.text.toString()
        CompletableFuture.runAsync{
        filmsRepository.saveEntity(FilmEntity(id,title,date,description,rating,genre,image,comment))
        }
    }

    private fun displayFilm(filmdescription: FilmDescription) {
          with(binding){
              title.text=filmdescription.title
              date.text=filmdescription.date
              description.text=filmdescription.description
              rating.text=filmdescription.rating.toString()
              genre.text=filmdescription.genre
              Picasso
                  .get()
                  .load("https://image.tmdb.org/t/p/w500"+filmdescription.image)
                  .into(poster);
          }
        image=filmdescription.image
    }
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)}
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer{
            renderData(it as AppStateFilm)
        })
        binding.mainView.visibility=View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        var i:Int=0
        while (i!=3) {
            if (f.getNow(null) != null) break
            Thread.sleep(1000)
            i++
        }
        val film=f.getNow(null)
        if (film==null)
        viewModel.getFilmFromRemoteSource(id_f)
        else{
            binding.mainView.visibility=View.VISIBLE
            binding.loadingLayout.visibility = View.GONE
            displayFilm(FilmDescription(film.title,film.date,film.image,film.rating,film.genre,film.description))
            binding.commentary.setText(film.commentary)
        }
        }
    private fun renderData(appStateFilm: AppStateFilm){
        when (appStateFilm) {
            is AppStateFilm.Success -> {
                binding.mainView.visibility=View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                displayFilm(appStateFilm.filmData)
            }
            is AppStateFilm.Loading -> {
                binding.mainView.visibility=View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateFilm.Error -> {
                binding.mainView.visibility=View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getFilmFromRemoteSource(id_f) })
            }
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
        fun newInstance(bundle: Bundle) : DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}