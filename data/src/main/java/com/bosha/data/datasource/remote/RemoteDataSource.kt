package com.bosha.data.datasource.remote

import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun fetchMovies(): Flow<List<Movie>>

    fun getDetails(id: String): Flow<MovieDetails>

    fun searchByTitle(title: String): Flow<List<Movie>>
}