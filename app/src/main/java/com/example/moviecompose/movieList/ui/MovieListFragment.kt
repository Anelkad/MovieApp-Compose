package com.example.moviecompose.movieList.ui

import android.app.Dialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var waitDialog: Dialog

    private val movieAdapter: PagedMovieAdapter by lazy {
        PagedMovieAdapter(
            onMovieClickListener = {
                movieListViewModel.obtainEvent(
                    MovieListEvent.OpenMovieDetails(
                        it,
                        findNavController()
                    ),
                )
            },
            saveMovieListener = {
                movieListViewModel.obtainEvent(
                    MovieListEvent.SaveMovie(
                        it,
                        requireContext()
                    )
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
            val uiState by movieListViewModel.movieListUiState.collectAsState()

            val coroutineScope = rememberCoroutineScope()
            when (val state = uiState) {
                MovieListUIState.Loading -> {
                    ProgressBar()
                }
                is MovieListUIState.Data -> {
                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            movieAdapter.submitData(state.pagingData)
                        }
                    }
                    MovieListScreen(recyclerView = recyclerView)
                }
                is MovieListUIState.Error -> TODO()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

//            movieAdapter.addLoadStateListener{ loadState ->
//            if (loadState.refresh !is LoadState.Loading)
//                    movieListViewModel.obtainEvent(MovieListEvent.ShowMovieList)
//            }


//        viewLifecycleOwner.lifecycleScope.launch {
//            movieListViewModel.saveMovieState.collectLatest {
//                when (it){
//                    is Resource.Failure -> {
//                        hideWaitDialog()
//                        Toast.makeText(
//                            context, "Cannot save movie!",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    is Resource.Loading -> {
//                        showWaitDialog()
//                    }
//                    is Resource.Success ->{
//                        hideWaitDialog()
//                        Toast.makeText(
//                            context, "Movie \"${it.getSuccessResult().title}\" saved!",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    else -> Unit
//                }
//            }
//        }
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
        if (this::waitDialog.isInitialized or waitDialog.isShowing) waitDialog.dismiss()
    }
}
