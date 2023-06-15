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
import com.example.moviecompose.R
import com.example.moviecompose.screens.MovieListScreen
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.viewmodels.MovieListViewModel
import com.example.moviecompose.viewmodels.SavedMovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val movieListViewModel: MovieListViewModel by viewModels()
    val savedMovieListViewModel: SavedMovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        movieListViewModel.movieListState.observe(viewLifecycleOwner, Observer {
            setContent {
                MovieListScreen(
                    result = it,
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
                    }
                )
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

    private lateinit var waitDialog: Dialog
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
