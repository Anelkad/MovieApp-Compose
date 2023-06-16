package com.example.moviecompose.adapters

import IMAGE_URL
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviecompose.R
import com.example.moviecompose.databinding.MovieItemBinding
import com.example.moviecompose.models.Movie

class MovieAdapter: ListAdapter<Movie, MovieAdapter.HolderMovie>(DiffCallback()){
    class HolderMovie(binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.title
        val description = binding.description
        val image = binding.imageView
        val save = binding.saveButton
        val itemView = binding.itemView
    }

    class DiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovie {
        val binding = MovieItemBinding.inflate(
            LayoutInflater
            .from(parent.context),parent,false)
        return HolderMovie(binding)
    }

    override fun onBindViewHolder(holder: HolderMovie, position: Int) {
        val movie = getItem(position)
        val id = movie.id
        val title = movie.title
        val description = movie.overview
        val vote_average = movie.voteAverage
        val release_date = movie.releaseDate
        val image = movie.posterPath
        val back_image = movie.backdropPath

        holder.title.text = title
        holder.description.text = "★ ${vote_average}\nПремьера: ${release_date}"

        Glide
            .with(holder.image.context)
            .load(IMAGE_URL+image)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_image_24)
            .into(holder.image)

        holder.itemView.tag = movie
        holder.itemView.setOnClickListener { onMovieClickListener?.let { it(id) } }

        holder.save.setOnClickListener {saveMovieListener?.let{ it(movie)}}
    }

    private var onMovieClickListener: ((Int) -> Unit)? = null
    fun setOnMovieClickListener(listener: (Int) -> Unit) {
        onMovieClickListener = listener
    }

    private var saveMovieListener: ((Movie) -> Unit)? = null
    fun setSaveMovieClickListener(listener: (Movie) -> Unit){
        saveMovieListener = listener
    }

}