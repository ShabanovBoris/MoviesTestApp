package com.bosha.data.remote

import com.bosha.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun fetchMovies(): Flow<List<Movie>>
}