package com.example.moviecompose.movieDetails.ui

import android.app.Dialog
import android.os.Bundle
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
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.movieDetails.ui.compose.MovieDetailsWithToolbar
import com.example.moviecompose.movieDetails.ui.compose.ProgressBar
import com.example.moviecompose.movieList.ui.MovieListEffect
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()
    val movieViewModel: MovieDetailsViewModel by viewModels()

    private lateinit var waitDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        movieViewModel.getMovieDetails(args.id)

        setContent {
            val uiState by remember {movieViewModel.uiState}

            when (uiState){
                MovieDetailsUIState.Loading -> ProgressBar()
                is MovieDetailsUIState.Data -> {
                    MovieDetailsWithToolbar(
                        movie = (uiState as MovieDetailsUIState.Data).movie,
                        onBackClick = {
                            movieViewModel.onEvent(MovieDetailsEvent.OnBackClick)
                        },
                        videos = (uiState as MovieDetailsUIState.Data).videos
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