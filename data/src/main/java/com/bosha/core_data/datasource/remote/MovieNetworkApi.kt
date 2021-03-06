package com.bosha.core_data.datasource.remote

import com.bosha.core_data.dto.remote.CreditResponse
import com.bosha.core_data.dto.remote.JsonGenres
import com.bosha.core_data.dto.remote.JsonMainResponse
import com.bosha.core_data.dto.remote.JsonMovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieNetworkApi {
    @GET("movie/now_playing")
    suspend fun fetchMovies(
        @Query("page") page: Int
    ): JsonMainResponse

    @GET("genre/movie/list?")
    suspend fun getGenres(): JsonGenres

    @GET("movie/{movie_id}?")
    suspend fun getDetails(
        @Path("movie_id") movieId: String
    ): JsonMovieDetails

    @GET("movie/{movie_id}/credits?")
    suspend fun getCredits(
        @Path("movie_id") movieId: String
    ): CreditResponse

    @GET("search/movie")
    suspend fun getMovieBySearch(
        @Query("query") searchQuery: String
    ): JsonMainResponse
}





