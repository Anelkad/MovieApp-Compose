package com.example.moviecompose.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviecompose.screens.MovieDetailsScreen
import com.example.moviecompose.viewmodels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()
    val movieViewModel: MovieDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        movieViewModel.getMovie(args.id)
        movieViewModel.movieDetailsDetailsState.observe(viewLifecycleOwner, Observer {
            setContent {
                MovieDetailsScreen(
                    result = it,
                    onBackPress = {findNavController().popBackStack()}
                )
            }
        })
    }
}