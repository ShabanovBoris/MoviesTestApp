package com.bosha.data.datasource.remote


import com.bosha.data.dto.remote.CreditResponse
import com.bosha.data.dto.remote.JsonGenres
import com.bosha.data.dto.remote.JsonMovieDetails
import com.bosha.data.dto.remote.JsonMainResponse
import kotlinx.coroutines.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 	[TheMovieDb] API
 *
 *
 */


interface MovieNetworkApi {
    @GET("movie/now_playing")
    suspend fun fetchMovies(
        @Query("page") page: Int
    ): JsonMainResponse

    @GET("genre/movie/list?")
    suspend fun getGenres(): JsonGenres

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: String
    ): JsonMovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: String
    ): CreditResponse

    @GET("search/movie")
    suspend fun getMovieBySearch(
        @Query("query") searchQuery: String
    ): JsonMainResponse
}





