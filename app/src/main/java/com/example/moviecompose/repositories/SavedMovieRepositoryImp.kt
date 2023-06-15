package com.example.moviecompose.repository

import MOVIES
import com.example.moviecompose.models.Movie
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

class SavedMovieRepositoryImp @Inject constructor(
    val firebase: FirebaseDatabase
): SavedMovieRepository {
    override fun getSavedMovieList(): Flow<Resource<ArrayList<Movie>>> = callbackFlow {
        val movieList = ArrayList<Movie>()

        val postListener = object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    movieList.clear()
                    for (ds in snapshot.children) {
                        val movie = ds.getValue(Movie::class.java)
                        if (movie != null) {
                            movieList.add(movie)
                        }
                    }
                    this@callbackFlow.trySendBlocking(Resource.Success(movieList))
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
        }

    }

    override suspend fun deleteMovie(movieId: Int): Resource<Int> {
         firebase.getReference(MOVIES).child(movieId.toString())
                .removeValue()
                .await()
        return Resource.Success(movieId)
    }

    override suspend fun saveMovie(movie: Movie): Resource<Movie> {
        val database = firebase.getReference(MOVIES)
        database.child(movie.id.toString())
            .setValue(movie)
            .await()
        return Resource.Success(movie)
    }
}