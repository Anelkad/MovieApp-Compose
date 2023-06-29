package com.example.moviecompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.models.Movie
import com.example.moviecompose.savedMovieList.ui.compose.SavedMovieListItemCard

class SavedMovieAdapter(
    private val onMovieClickListener: ((Int) -> Unit),
    private val deleteMovieListener: ((Int) -> Unit)
): ListAdapter<Movie, SavedMovieAdapter.HolderMovie>(DiffCallback()){

    inner class HolderMovie(private val composeView: ComposeView): RecyclerView.ViewHolder(composeView) {
        fun bind(movie: Movie) {
            composeView.setContent {
                SavedMovieListItemCard(
                    movie = movie,
                    movieOnDeleteClick = {deleteMovieListener(movie.id)},
                    movieOnClick = {onMovieClickListener(movie.id)}
                )
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovie {
        return HolderMovie(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: HolderMovie, position: Int) {
        holder.bind(getItem(position))
    }

}