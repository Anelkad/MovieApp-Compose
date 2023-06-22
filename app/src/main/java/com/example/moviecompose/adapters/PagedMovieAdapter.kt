package com.example.moviecompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.models.Ad
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.models.Movie
import com.example.moviecompose.movieList.ui.compose.AdListItemCard
import com.example.moviecompose.movieList.ui.compose.MovieListItemCard

class PagedMovieAdapter(
    private val onMovieClickListener: ((Int) -> Unit),
    private val saveMovieListener: ((Movie) -> Unit)
) :
    PagingDataAdapter<ListItem, PagedMovieAdapter.ComposeMovieViewHolder>(
        DiffCallback()
    ) {

    inner class ComposeMovieViewHolder(
        private val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {

        fun bind(item: ListItem) {
            when(item) {
                is ListItem.MovieItem -> bindMovies(
                    item.movie,
                    saveMovieListener,
                    onMovieClickListener
                )
                is ListItem.AdItem -> bindAds(item.ad)
            }
        }

        private fun bindAds(
            ad: Ad
        ) {
            composeView.setContent {
                AdListItemCard(
                    ad = ad
                )
            }
        }

        private fun bindMovies(
            movie: Movie,
            saveMovieListener: (Movie) -> Unit,
            onMovieClickListener: (Int) -> Unit
        ) {
            composeView.setContent {
                MovieListItemCard(
                    movie = movie,
                    movieOnSaveClick = {saveMovieListener(movie)},
                    movieOnClick = {onMovieClickListener(movie.id)}
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeMovieViewHolder {
        return ComposeMovieViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            val isSameMovieItem = oldItem is ListItem.MovieItem
                    && newItem is ListItem.MovieItem
                    && oldItem.movie.id == newItem.movie.id

            val isSameAdItem = oldItem is ListItem.AdItem
                    && newItem is ListItem.AdItem
                    && oldItem.ad == newItem.ad

            return isSameMovieItem || isSameAdItem
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem == newItem
    }
}