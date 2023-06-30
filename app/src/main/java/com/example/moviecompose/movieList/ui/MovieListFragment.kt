package com.example.moviecompose.movieList.ui

import android.app.Dialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviecompose.R
import com.example.moviecompose.adapters.PagedMovieAdapter
import com.example.moviecompose.movieList.ui.compose.MovieListScreen
import com.example.moviecompose.movieList.ui.compose.ProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var waitDialog: Dialog

    private val movieAdapter: PagedMovieAdapter by lazy {
        PagedMovieAdapter(
            onMovieClickListener = {
                movieListViewModel.onEvent(
                    MovieListEvent.OnMovieClick(it),
                )
            },
            saveMovieListener = {
                movieListViewModel.onEvent(
                    MovieListEvent.OnSaveMovieClick(it)
                )
            }
        )
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
            val uiState by movieListViewModel.uiState.collectAsState()

            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = Unit) {
                coroutineScope.launch {
                    movieAdapter.submitData(uiState.pagingData)
                }
            }
            if (uiState.isLoading) ProgressBar()
            MovieListScreen(recyclerView = recyclerView)
            //todo MovieListScreen передать LoadState adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            movieListViewModel.effect.collect {
                when (it) {
                    is MovieListEffect.ShowToast -> {
                        hideWaitDialog()
                        Toast.makeText(
                            context, it.text,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    MovieListEffect.ShowWaitDialog -> {
                        showWaitDialog()
                    }
                    is MovieListEffect.NavigateToMovieDetails -> {
                        navigateToMovieDetails(it.movieId)
                    }
                }
            }
        }

        movieAdapter.addLoadStateListener{ loadState ->
            if (loadState.refresh is LoadState.NotLoading && movieAdapter.itemCount>1)
                    movieListViewModel.onEvent(MovieListEvent.StopLoading)
            }


//        lifecycleScope.launch {
//            movieListViewModel.movieListUiState.collectLatest { state ->
//                when (state) {
//                    is MovieListUIState.Data -> {
//                        movieAdapter.submitData(state.pagingData)
//                    }
//                    else -> Unit
//                }
//            }
//        }

    }

    private fun navigateToMovieDetails(movieId: Int){
            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailsFragment,
                Bundle().apply {
                    putInt("id", movieId)
                }
            )
    }

    private fun showWaitDialog() {
        if (!this::waitDialog.isInitialized) {
            waitDialog = Dialog(requireActivity())
            waitDialog.setContentView(R.layout.wait_dialog)

            waitDialog.setCancelable(false)
            waitDialog.setCanceledOnTouchOutside(false)
        }
        if (!waitDialog.isShowing) waitDialog.show()
    }

    private fun hideWaitDialog() {
        if (this::waitDialog.isInitialized and waitDialog.isShowing) waitDialog.dismiss()
    }
}
