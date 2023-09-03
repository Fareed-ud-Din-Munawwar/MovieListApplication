package com.example.assignmentoptimized.repository


import com.example.assignmentoptimized.dataobjects.MovieGson
import com.example.assignmentoptimized.localdatabse.Movie
import com.example.assignmentoptimized.repository.data.LocalDataSource
import com.example.assignmentoptimized.repository.data.RemoteDataSource
import com.example.assignmentoptimized.repository.data.Resource
import com.example.assignmentoptimized.repository.data.isSuccess
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getFavouriteMovieData(): Flow<List<Movie>> = localDataSource.getFavouriteMovieData()

    suspend fun addFavouriteMovie(movie: Movie) = localDataSource.addFavouriteMovie(movie)

    suspend fun removeFavouriteMovie(movie: Movie) = localDataSource.removeFavouriteMovie(movie)

    suspend fun deleteMovie(movie: Movie) = localDataSource.deleteMovie(movie)

    fun getMovieData(): Flow<List<Movie>> = localDataSource.getMovieData()

    suspend fun setDB(list: List<Movie>?) = localDataSource.setDB(list)

    suspend fun searchMovieById(id: Long): Flow<Movie> = localDataSource.searchMovieById(id)

    suspend fun addNewMovie(customMovie: Movie) = localDataSource.addNewMovie(customMovie)

    suspend fun fetchMovieDetails(id: Long): Resource<Movie?> {
        val response = remoteDataSource.sendDetailGetRequest(id)
        return if (response.status.isSuccess) {
            Resource.success(response.data)
        } else {
            Resource.error(response.message ?: "Unknown Network Error")
        }
    }

    suspend fun fetchMoviesList(): Resource<MovieGson?> {
        val response = remoteDataSource.sendListGetRequest()
        return if (response.status.isSuccess) {
            setDB(response.data?.results ?: emptyList())
            Resource.success(response.data)
        } else {
            Resource.error(response.message ?: "Unknown Network Error")
        }
    }

}