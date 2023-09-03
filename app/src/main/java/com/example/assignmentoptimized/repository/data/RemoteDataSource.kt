package com.example.assignmentoptimized.repository.data

import android.net.Uri
import com.example.assignmentoptimized.dataobjects.MovieGson
import com.example.assignmentoptimized.localdatabse.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: MovieService
) {
    companion object {
        private const val KEY_MOVIE_ID = "{movie_id}"
        private const val MOVIES_LIST_API = "popular"
        private const val MOVIE_DETAILS_API = KEY_MOVIE_ID
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val POSTER_ADDRESS: String = "http://image.tmdb.org/t/p/w500"
        private const val API_KEY = "42fe60cd7004daf05adaac5a9857a582"
        private const val LANGUAGE = "en-US"
        private const val PAGE = "1"
    }

    private val apiKeyQueryParam = Pair("api_key", API_KEY)
    private val languageQueryParam = Pair("language", LANGUAGE)
    private val pageQueryParam = Pair("page", PAGE)

    private val MOVIES_LIST_URL by lazy {
        Uri.parse(BASE_URL)
            .buildUpon()
            .appendPath(MOVIES_LIST_API)
            .appendQueryParameter(apiKeyQueryParam.first, apiKeyQueryParam.second)
            .appendQueryParameter(languageQueryParam.first, languageQueryParam.second)
            .appendQueryParameter(pageQueryParam.first, pageQueryParam.second)
            .build().toString()
    }

    private val MOVIE_DETAILS_URL by lazy {
        Uri.parse(BASE_URL)
            .buildUpon()
            .appendEncodedPath(MOVIE_DETAILS_API)
            .appendQueryParameter(apiKeyQueryParam.first, apiKeyQueryParam.second)
            .appendQueryParameter(languageQueryParam.first, languageQueryParam.second)
            .build().toString()
    }

    interface MovieService {
        @GET(MOVIES_LIST_API)
        suspend fun getList(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("page") page: String,
        ): Response<MovieGson>

        @GET(MOVIE_DETAILS_API)
        suspend fun getMovie(
            @Path("movie_id", encoded = false) id: Long,
            @Query("api_key") api_key: String,
            @Query("language") language: String,
        ): Response<Movie>
    }

    fun getAddress(url: String): String {
        return Uri.parse(POSTER_ADDRESS).buildUpon().appendEncodedPath(url).build().toString()
    }

    fun getBaseUrl(): String {
        return BASE_URL
    }

    suspend fun sendListGetRequest(): Resource<MovieGson?> {
        //val data : MovieGson
        return try {
            val response = service.getList(API_KEY, LANGUAGE, PAGE)
            Resource.success(response.body())
        } catch (e: Exception) {
            return if (e is UnknownHostException) {
                Resource.error("No Network available")

            } else {
                Resource.error(e.localizedMessage!!)
            }
        }
    }

    suspend fun sendDetailGetRequest(id: Long): Resource<Movie?> {
        //val updateURL = MOVIE_DETAILS_URL.replace(KEY_MOVIE_ID, id)
        return try {
            val response = service.getMovie(id, API_KEY, LANGUAGE)
            Resource.success(response.body())
        } catch (e: Exception) {
            Resource.error(e.localizedMessage!!)
        }
    }
}


