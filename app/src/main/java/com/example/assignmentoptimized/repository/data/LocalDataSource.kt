package com.example.assignmentoptimized.repository.data

import com.example.assignmentoptimized.localdatabse.LocalDBHandler
import com.example.assignmentoptimized.localdatabse.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val localDBHandler: LocalDBHandler
) {

    fun getFavouriteMovieData(): Flow<List<Movie>> {
        return localDBHandler.getFavouriteMovie()
    }

    suspend fun addFavouriteMovie(movie: Movie) {
        localDBHandler.setFavourite(movie.id)
    }

    suspend fun removeFavouriteMovie(movie: Movie) {
        localDBHandler.removeFavourite(movie.id)
    }

    suspend fun deleteMovie(movie: Movie) {
        localDBHandler.deleteMovie(movie)
    }

    fun getMovieData(): Flow<List<Movie>> {
        return localDBHandler.getAllMovie()

    }

    suspend fun setDB(list: List<Movie>?) {
        localDBHandler.insertMovie(*list?.toTypedArray() ?: emptyArray())
    }

    suspend fun searchMovieById(id: Long): Flow<Movie> {
        return localDBHandler.getMovieById(id)
    }

    suspend fun addNewMovie(customMovie: Movie) {
        localDBHandler.insertMovie(customMovie)
    }
}