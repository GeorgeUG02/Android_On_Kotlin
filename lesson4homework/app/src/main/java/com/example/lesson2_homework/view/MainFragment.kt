package com.example.lesson2_homework.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lesson2_homework.R
import com.example.lesson2_homework.viewmodel.AppState
import com.example.lesson2_homework.model.Film
import com.example.lesson2_homework.viewmodel.MainViewModel
import com.example.lesson2_homework.databinding.MainFragmentBinding
import com.example.lesson2_homework.model.Films
import com.example.lesson2_homework.view.FilmsAdapter.onFilmClickListener
import com.google.android.material.snackbar.Snackbar

private fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getFilmsFromLocalSource() })
            }
            }
        }
    private fun showList(l: List<Film>){
        val recyclerView = binding.list
        val adapter: FilmsAdapter = FilmsAdapter(context,l, object : onFilmClickListener {
            override fun onFilmClick(film: Film, position: Int) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(DetailsFragment.BUNDLE_EXTRA, film)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
        recyclerView.setAdapter(adapter);
    }
}