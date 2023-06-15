package com.example.moviecompose.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviecompose.screens.SavedMovieListScreen
import com.example.moviecompose.viewmodels.SavedMovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedMovieFragment : Fragment() {
    val savedMovieListViewModel: SavedMovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        savedMovieListViewModel.savedMovieList.observe(viewLifecycleOwner, Observer {
            setContent {
                SavedMovieListScreen(it)
            }
        })
    }

}