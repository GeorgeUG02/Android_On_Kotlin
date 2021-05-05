package com.example.lesson2_homework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lesson2_homework.viewmodel.AppState
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.viewmodel.MainViewModel
import com.example.lesson2_homework.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it as AppState)
        })
        viewModel.getFilmsFromLocalSource()
    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val filmsData = appState.filmsData
                val films = filmsData.getFilms()
                binding.loadingLayout.visibility = View.GONE
                showList(films)

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }
    private fun showList(l: List<Film>){
        val recyclerView = binding.list
        val adapter: FilmsAdapter = FilmsAdapter(context,l)
        recyclerView.setAdapter(adapter);
    }
}