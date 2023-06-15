package com.example.moviecompose.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moviecompose.R
import com.example.moviecompose.screens.MovieListScreen
import com.example.moviecompose.viewmodels.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val movieListViewModel: MovieListViewModel by viewModels()
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
                    }
                )
            }
        })
    }
}
