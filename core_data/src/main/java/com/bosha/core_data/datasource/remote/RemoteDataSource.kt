package com.bosha.core_data.datasource.remote

import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun fetchMovies(): Flow<List<Movie>>

    fun getDetails(id: String): Flow<MovieDetails>

    fun searchByTitle(title: String): Flow<List<Movie>>

    suspend fun rawQuery(page: Int): List<Movie>
}