package com.example.moviecompose.savedMovieList.data.remote

import MOVIES
import com.example.moviecompose.savedMovieList.data.modelDTO.MovieDTO
import com.example.moviecompose.savedMovieList.domain.model.Movie
import com.example.moviecompose.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SavedMovieService @Inject constructor(
    val firebase: FirebaseDatabase
){
    fun getSavedMovieList(): Flow<Resource<List<MovieDTO>>> = callbackFlow {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = snapshot.children.map {
                    it.getValue(MovieDTO::class.java)
                }
                this@callbackFlow.trySendBlocking(Resource.Success(movies as List<MovieDTO>))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Resource.Failure(error.toException()))
            }
        }

        firebase.getReference(MOVIES)
            .addValueEventListener(postListener)

        awaitClose{
            firebase.getReference(MOVIES)
                .removeEventListener(postListener)
            channel.close()
        }

    }

    suspend fun deleteMovie(movieId: Int): Resource<Int> {
        firebase.getReference(MOVIES).child(movieId.toString())
            .removeValue()
            .await()
        return Resource.Success(movieId)
    }

    suspend fun saveMovie(movie: Movie): Resource<Movie> {
        val database = firebase.getReference(MOVIES)
        database.child(movie.id.toString())
            .setValue(movie)
            .await()
        return Resource.Success(movie)
    }
}