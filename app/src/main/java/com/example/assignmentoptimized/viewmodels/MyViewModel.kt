package com.example.assignmentoptimized.viewmodels

import androidx.lifecycle.*
import com.example.assignmentoptimized.localdatabse.Movie
import com.example.assignmentoptimized.repository.MovieRepository
import com.example.assignmentoptimized.repository.data.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    var favList = listOf<Movie>()
    var movieList = listOf<Movie>()

    private var _movie = MutableLiveData<Movie>()
    val movieObserver: LiveData<Movie> = _movie

    val movieListObserver: LiveData<List<Movie>> =
        repository.getMovieData().distinctUntilChanged().asLiveData()

    val movieFavListObserver: LiveData<List<Movie>> =
        repository.getFavouriteMovieData().distinctUntilChanged().asLiveData()

    private var _searchRes = MutableLiveData<Movie>()
    val searchResObserver: LiveData<Movie> = _searchRes

    private var _toastRes = MutableLiveData<String?>()
    val toastResObserver: LiveData<String?> = _toastRes


    fun getMovieListAPI() {
        viewModelScope.launch {
            val result = repository.fetchMoviesList()
            if (result.status.isSuccess)
                _toastRes.value = ""
            else
                _toastRes.value = result.message
        }
    }

    fun searchMovieById(movieDetails: Long) {
        viewModelScope.launch {
            repository.searchMovieById(movieDetails).distinctUntilChanged().collect {
                _searchRes.value = it
            }
        }
    }

    fun setResponse(response: List<Movie>?) {
        viewModelScope.launch {
            repository.setDB(response)
        }

    }

    fun removeFavouriteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.removeFavouriteMovie(movie)
        }

    }

    fun addFavouriteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.addFavouriteMovie(movie)
        }

    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }

    }

    fun fetchMovieDetails(movieDetails: Long) {
        viewModelScope.launch {
            val response = repository.fetchMovieDetails(movieDetails)
            if (response.status.isSuccess) {
                response.data?.let {
                    _movie.postValue(it)
                    _toastRes.value = ""
                }
            } else {
                _toastRes.value = response.message
            }
        }
    }

    fun addNewMovie(movie: Movie) {
        viewModelScope.launch {
            repository.addNewMovie(movie)
        }

    }
}