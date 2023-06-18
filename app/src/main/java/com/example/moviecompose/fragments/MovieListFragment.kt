package com.example.moviecompose.fragments

import android.app.Dialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviecompose.R
import com.example.moviecompose.adapters.MovieAdapter
import com.example.moviecompose.adapters.MovieComposeAdapter
import com.example.moviecompose.screens.MovieListScreen
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.viewmodels.MovieListViewModel
import com.example.moviecompose.viewmodels.SavedMovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val movieListViewModel: MovieListViewModel by viewModels()
    val savedMovieListViewModel: SavedMovieListViewModel by viewModels()
    private lateinit var waitDialog: Dialog

    private val movieAdapter: MovieComposeAdapter by lazy {
        MovieComposeAdapter()
    }
    private val recyclerView: RecyclerView by lazy {
        RecyclerView(requireContext()).apply {
            adapter = movieAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
                //MovieComposeTheme {
                    MovieListScreen(
                        //todo navController передать в констуктор
                        movieOnClick = {
                            val bundle = Bundle().apply {
                                putInt("id", it)
                            }
                            findNavController().navigate(
                                R.id.action_movieListFragment_to_movieDetailsFragment,
                                bundle
                            )
                        },
                        movieOnSaveClick = {
                            savedMovieListViewModel.saveMovie(it)
                        },
                        recyclerView = recyclerView
                    )
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo observe flow with paginated data and adapter.submitData
        //todo paging adapter
        movieListViewModel.movieListState.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success){
                movieAdapter.submitList(it.getSuccessResult().results.toMutableList())
            }
        })

        savedMovieListViewModel.saveMovieState.observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Failure -> {
                    hideWaitDialog()
                    Toast.makeText(
                        context, "Cannot save movie!",
                        Toast.LENGTH_LONG
                    ).show()
                    savedMovieListViewModel.clearSaveMovieState()
                }
                is Resource.Loading -> {
                    showWaitDialog()
                }
                is Resource.Success ->{
                    hideWaitDialog()
                    Toast.makeText(
                        context, "Movie \"${it.getSuccessResult().title}\" saved!",
                        Toast.LENGTH_LONG
                    ).show()
                    savedMovieListViewModel.clearSaveMovieState()
                }
                else -> Unit
            }
        })
    }

    private fun showWaitDialog(){
        if (!this::waitDialog.isInitialized) {
            waitDialog = Dialog(requireActivity())
            waitDialog.setContentView(R.layout.wait_dialog)

            waitDialog.setCancelable(false)
            waitDialog.setCanceledOnTouchOutside(false)
        }
        if (!waitDialog.isShowing) waitDialog.show()
    }

    private fun hideWaitDialog(){
        if (this::waitDialog.isInitialized or waitDialog.isShowing) waitDialog.dismiss()
    }
}
