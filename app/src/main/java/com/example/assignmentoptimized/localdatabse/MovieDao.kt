package com.example.assignmentoptimized.localdatabse

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(vararg movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :ID ORDER BY release_date DESC")
    fun getMovieById(ID: Long): Flow<Movie>

    @Query("SELECT * FROM Movie WHERE title LIKE :title ORDER BY release_date DESC")
    fun getMovieByTitle(title: String): Flow<List<Movie>>

    @Query("SELECT * FROM Movie ORDER BY release_date DESC")
    fun getAllMovie(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE favourite = 1 ORDER BY release_date DESC")
    fun getFavouriteMovie(): Flow<List<Movie>>

    @Query("UPDATE Movie SET favourite = 1 WHERE id = :id ")
    suspend fun setFavourite(id: Long)

    @Query("UPDATE Movie SET favourite = 0 WHERE id = :id")
    suspend fun removeFavourite(id: Long)

}