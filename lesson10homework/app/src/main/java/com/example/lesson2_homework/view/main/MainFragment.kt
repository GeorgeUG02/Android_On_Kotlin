package com.example.lesson2_homework.view.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lesson2_homework.R
import com.example.lesson2_homework.viewmodel.AppStateList
import com.example.lesson2_homework.viewmodel.MainViewModel
import com.example.lesson2_homework.databinding.MainFragmentBinding
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.utils.showSnackBar
import com.example.lesson2_homework.view.main.FilmsAdapter.onFilmClickListener
import com.example.lesson2_homework.view.details.DetailsFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private fun displayFilms(filmsData:List<Film>) {
        val recyclerView = binding.list
        val adapter: FilmsAdapter = FilmsAdapter(context,filmsData,object : onFilmClickListener {
            override fun onFilmClick(id: Int) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                            putInt(DetailsFragment.BUNDLE_EXTRA, id)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        recyclerView.setAdapter(adapter)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)}
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
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer{
            renderData(it as AppStateList)
        })
        viewModel.getFilmsFromRemoteSource(activity!!)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appStateList: AppStateList) {
        when (appStateList) {
            is AppStateList.Success -> {
                binding.mainView.visibility=View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                displayFilms(appStateList.filmsData)
            }
            is AppStateList.Loading -> {
                binding.mainView.visibility=View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateList.Error -> {
                binding.mainView.visibility=View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getFilmsFromRemoteSource(activity!!) })
            }
            }
        }
}