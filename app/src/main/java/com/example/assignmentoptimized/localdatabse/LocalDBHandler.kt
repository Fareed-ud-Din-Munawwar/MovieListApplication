package com.example.assignmentoptimized.localdatabse

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDBHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val db = Room.databaseBuilder(context, LocalDatabase::class.java, "Database").build()
    //private val db =  Room.databaseBuilder(context, LocalDatabase::class.java, "Database").allowMainThreadQueries().build()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: Movie) {
        db.movieDao().insertMovie(*movie)
    }

    suspend fun updateMovie(movie: Movie) {
        db.movieDao().updateMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        db.movieDao().deleteMovie(movie)
    }

    suspend fun getMovieById(id: Long): Flow<Movie> {
        return db.movieDao().getMovieById(id)
    }

    suspend fun getMovieByTitle(title: String): Flow<List<Movie>> {
        return db.movieDao().getMovieByTitle(title)

    }

    fun getAllMovie(): Flow<List<Movie>> {
        return db.movieDao().getAllMovie()
    }

    fun getFavouriteMovie(): Flow<List<Movie>> {
        return db.movieDao().getFavouriteMovie()
    }

    suspend fun setFavourite(id: Long) {
        return db.movieDao().setFavourite(id)
    }

    suspend fun removeFavourite(id: Long) {
        return db.movieDao().removeFavourite(id)
    }
}