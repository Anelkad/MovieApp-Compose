package com.example.moviecompose.movieDetails.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviecompose.R
import com.example.moviecompose.movieDetails.ui.compose.MovieDetailsWithToolbar
import com.example.moviecompose.movieDetails.ui.compose.ProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()
    val movieViewModel: MovieDetailsViewModel by viewModels()

    private lateinit var waitDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        movieViewModel.onEvent(MovieDetailsEvent.LoadMovieDetails(args.id))
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        Log.d("qwerty", "oCreateView MovieDetails")
        setContent {
            val movieDetailsUIState by remember {movieViewModel.uiState}

            when (val state = movieDetailsUIState){
                MovieDetailsUIState.Loading -> ProgressBar()
                is MovieDetailsUIState.Data -> {
                    MovieDetailsWithToolbar(
                        movie = state.movie,
                        onBackClick = {
                            movieViewModel.onEvent(MovieDetailsEvent.OnBackClick)
                        },
                        videos = state.videos
                    )
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.effect.collect {
                when (it) {
                    is MovieDetailsEffect.ShowToast -> {
                        hideWaitDialog()
                        Toast.makeText(
                            context, it.text,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    MovieDetailsEffect.ShowWaitDialog -> {
                        showWaitDialog()
                    }
                    is MovieDetailsEffect.NavigateBack -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
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
        if (this::waitDialog.isInitialized and waitDialog.isShowing) waitDialog.dismiss()
    }
}