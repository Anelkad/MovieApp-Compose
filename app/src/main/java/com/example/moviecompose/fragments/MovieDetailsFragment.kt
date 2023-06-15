package com.example.moviecompose.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviecompose.R
import com.example.moviecompose.screens.MovieDetailsScreen
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.viewmodels.MovieDetailsViewModel
import com.example.moviecompose.viewmodels.SavedMovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()
    val movieViewModel: MovieDetailsViewModel by viewModels()
    val savedMovieListViewModel: SavedMovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        movieViewModel.getMovie(args.id)
        movieViewModel.movieDetailsDetailsState.observe(viewLifecycleOwner, Observer {
            setContent {
                MovieDetailsScreen(
                    result = it,
                    onBackPress = {findNavController().popBackStack()},
                    movieOnSaveClick = {savedMovieListViewModel.saveMovie(it)}
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