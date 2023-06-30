package com.example.moviecompose.savedMovieList.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.R
import com.example.moviecompose.adapters.SavedMovieAdapter
import com.example.moviecompose.movieDetails.ui.compose.ProgressBar
import com.example.moviecompose.movieList.ui.compose.MovieListScreen
import com.example.moviecompose.savedMovieList.ui.compose.SavedMovieListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedMovieListFragment : Fragment() {
    val savedMovieListViewModel: SavedMovieListViewModel by viewModels()
    private lateinit var waitDialog: Dialog

    private val movieAdapter: SavedMovieAdapter by lazy {
        SavedMovieAdapter(
            onMovieClickListener = {
                savedMovieListViewModel.onEvent(SavedMovieListEvent.OnMovieClick(it))
            },
            deleteMovieListener = {
                savedMovieListViewModel.onEvent(SavedMovieListEvent.OnDeleteMovieClick(it))
            }
        )
    }
    private val recyclerView: RecyclerView by lazy {
        RecyclerView(requireContext()).apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        Log.d("qwerty","Saved Movie OnCreate")
        setContent {
            val uiState by savedMovieListViewModel.uiState.collectAsState()
            //todo ui не обновляется при удалении со списка

            Log.d("qwerty uiState", uiState.toString())

            when (val state = uiState){
                SavedMovieListUIState.Loading -> ProgressBar()
                is SavedMovieListUIState.Data -> {

                    MovieListScreen(recyclerView = recyclerView)

                    if (state.movieList.isEmpty())
                        Text(
                            text = "No saved movies",
                            textAlign = TextAlign.Center
                        )
                    movieAdapter.submitList(state.movieList.toMutableList())

                    Log.d("qwerty recycler", movieAdapter.itemCount.toString())

                    Log.d("qwerty SavedMovieFragm", "uiState: ${state.movieList.size}")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            savedMovieListViewModel.effect.collect {
                when (it) {
                    is SavedMovieListEffect.ShowToast -> {
                        hideWaitDialog()
                        Toast.makeText(
                            context, it.text,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    SavedMovieListEffect.ShowWaitDialog -> {
                        showWaitDialog()
                    }
                    is SavedMovieListEffect.NavigateToMovieDetails -> {
                        navigateToMovieDetails(it.movieId)
                    }
                }
            }
        }
    }

    private fun navigateToMovieDetails(movieId: Int){
        findNavController().navigate(
            R.id.action_savedMovieFragment_to_movieDetailsFragment,
            Bundle().apply {
                putInt("id", movieId)
            }
        )
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
        if (this::waitDialog.isInitialized or waitDialog.isShowing) waitDialog.dismiss()
    }

}