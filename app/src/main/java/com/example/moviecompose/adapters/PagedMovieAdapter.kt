package com.example.moviecompose.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.movieList.ui.compose.AdListItemCard
import com.example.moviecompose.movieList.ui.compose.MovieListItemCard
import com.example.moviecompose.movieList.ui.modelUI.AdUI
import com.example.moviecompose.movieList.ui.modelUI.ListItemUI
import com.example.moviecompose.movieList.ui.modelUI.MovieUI

class PagedMovieAdapter(
    private val onMovieClickListener: ((Int) -> Unit),
    private val saveMovieListener: ((MovieUI) -> Unit)
) :
    PagingDataAdapter<ListItemUI, PagedMovieAdapter.ComposeMovieViewHolder>(
        DiffCallback()
    ) {

    inner class ComposeMovieViewHolder(
        private val composeView: ComposeView
    ): RecyclerView.ViewHolder(composeView) {

        fun bind(item: ListItemUI) {
            when(item) {
                is ListItemUI.MovieItemUI -> bindMovies(
                    item.movie,
                    saveMovieListener,
                    onMovieClickListener
                )
                is ListItemUI.AdItemUI -> bindAds(item.ad)
            }
        }

        private fun bindAds(
            ad: AdUI
        ) {
            composeView.setContent {
                AdListItemCard(
                    ad = ad
                )
            }
        }

        private fun bindMovies(
            movie: MovieUI,
            saveMovieListener: (MovieUI) -> Unit,
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

    class DiffCallback : DiffUtil.ItemCallback<ListItemUI>() {
        override fun areItemsTheSame(oldItem: ListItemUI, newItem: ListItemUI): Boolean {
            val isSameMovieItem = oldItem is ListItemUI.MovieItemUI
                    && newItem is ListItemUI.MovieItemUI
                    && oldItem.movie.id == newItem.movie.id

            val isSameAdItem = oldItem is ListItemUI.AdItemUI
                    && newItem is ListItemUI.AdItemUI
                    && oldItem.ad == newItem.ad

            return isSameMovieItem || isSameAdItem
        }

        override fun areContentsTheSame(oldItem: ListItemUI, newItem: ListItemUI) = oldItem == newItem
    }
}