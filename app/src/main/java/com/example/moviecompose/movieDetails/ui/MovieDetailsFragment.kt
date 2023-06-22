package com.example.moviecompose.movieDetails.ui

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviecompose.R
import com.example.moviecompose.movieDetails.ui.compose.MovieDetailsWithToolbar
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.movieDetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()
    val movieViewModel: MovieDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {


        //todo viewState (loading progress bar)
        movieViewModel.getMovieDetails(args.id)
        //todo observer внутри observer
        movieViewModel.movieDetailsDetailsState.observe(viewLifecycleOwner, Observer { movieDetailsResource ->

            if (movieDetailsResource is Resource.Success) {
                    movieViewModel.movieVideoState.observe(viewLifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            setContent {
                            MovieDetailsWithToolbar(
                                movie = movieDetailsResource.getSuccessResult(),
                                onBackClick = { findNavController().popBackStack() },
                                videos = it.getSuccessResult()
                                )
                            }
                        }
                    })
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.saveMovieState.collectLatest {
                when (it){
                    is Resource.Failure -> {
                        hideWaitDialog()
                        Toast.makeText(
                            context, "Cannot save movie!",
                            Toast.LENGTH_LONG
                        ).show()
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
                    }
                    else -> Unit
                }
            }
        }
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